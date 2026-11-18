#!/bin/bash
###############################################################################
# Xray 一键部署脚本 (REALITY + XHTTP 双线路)
# 适用系统: Ubuntu 24.04 LTS+ / Debian
#
# 线路架构:
#                     ┌── VLESS + REALITY + Vision  →  直连 VPS (端口 443)
#   客户端 ───────────┤
#                     └── VLESS + XHTTP + TLS  →  Cloudflare  →  VPS (端口 8443)
#
# 用法:
#   bash xray_deploy.sh -d <域名> -u <UUID> [-e <email>] \
#       [-R <reality端口>] [-x <xhttp端口>] [-D <reality借用站点>] \
#       [-W <xhttp路径>] [-c] [-K] [-y] [-s <步骤>] [-r] [-h]
#
# 参数说明:
#   -d  域名 (必填, 用于 XHTTP+TLS 走 Cloudflare 那条线路及证书签发)
#   -u  UUID (必填, 两条线路共用, 可通过 xray uuid 生成)
#   -e  邮箱 (可选, 默认 user@email.com)
#   -R  REALITY 监听端口 (可选, 默认 443, 直连)
#   -x  XHTTP 监听端口 (可选, 默认 8443, 走 CF; CF 橙云支持 8443)
#   -D  REALITY 借用的目标站点 host:port (可选, 默认 www.bing.com:443)
#   -W  XHTTP 路径 (可选, 默认 /index)
#   -c  复用已有 config 文件参数重新安装 (从 /root/.xray_deploy_config 读取)
#   -L  日志保留天数 (可选, 默认 30)
#   -y  非交互模式, 自动确认所有提示
#   -s  只运行指定步骤, 逗号分隔 (可选, 如 -s 3,4)
#   -r  重置进度, 从头开始安装
#   -h  显示帮助信息
#
# 步骤编号:
#   1 - 申请 SSL 证书          6 - 配置 geo 自动更新
#   2 - 安装 Xray              7 - 配置日志轮转
#   3 - 生成密钥+配置 Xray     8 - 配置 UFW 防火墙
#   4 - 配置 Xray 服务        9 - 创建 Netflix 检测脚本
#   5 - 开启 BBR 加速
#
# 断点续装:
#   脚本会在 /root/.xray_deploy_progress 记录已完成的步骤
#   如果某步骤失败, 重新运行相同命令即可从失败处继续
#   如需从头开始, 加 -r 参数清除进度记录
#
# 示例:
#   bash xray_deploy.sh -d example.com -u "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -e "user@email.com"
#   bash xray_deploy.sh -c   # 复用上次保存的配置重新安装
###############################################################################

set -e

# ======================== 颜色定义 ========================
RED="\033[31m"
GREEN="\033[32m"
YELLOW="\033[33m"
BLUE="\033[34m"
RESET="\033[0m"

# ======================== 默认值 ========================
DOMAIN=""
UUID=""
EMAIL="user@email.com"
REALITY_PORT=443
XHTTP_PORT=8443
REALITY_DEST="www.ti.com:443"
XHTTP_PATH="/index"
REALITY_PRIVATE_KEY=""
REALITY_PUBLIC_KEY=""
REALITY_SHORT_ID=""
LOG_KEEP_DAYS=30
REUSE_CONFIG=false
AUTO_YES=false
RUN_STEPS=""
RESET_PROGRESS=false

# ======================== 进度追踪 ========================
PROGRESS_FILE="/root/.xray_deploy_progress"
CONFIG_FILE="/root/.xray_deploy_config"

# ======================== 函数定义 ========================

print_info() {
    echo -e "${GREEN}[INFO]${RESET} $1"
}

print_warn() {
    echo -e "${YELLOW}[WARN]${RESET} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${RESET} $1"
}

print_step() {
    echo ""
    echo -e "${BLUE}========================================${RESET}"
    echo -e "${BLUE} $1${RESET}"
    echo -e "${BLUE}========================================${RESET}"
}

# 检查某步骤是否已完成
step_done() {
    local step_name="$1"
    if [[ -f "$PROGRESS_FILE" ]] && grep -q "^${step_name}$" "$PROGRESS_FILE"; then
        return 0
    fi
    return 1
}

# 标记某步骤已完成
mark_step_done() {
    local step_name="$1"
    echo "$step_name" >> "$PROGRESS_FILE"
    print_info "步骤 [$step_name] 已完成并记录"
}

# 检查是否应该运行某步骤 (基于 -s 参数)
should_run_step() {
    local step_num="$1"
    # 如果未指定 -s, 则运行所有步骤
    if [[ -z "$RUN_STEPS" ]]; then
        return 0
    fi
    # 检查步骤号是否在指定列表中
    if echo ",$RUN_STEPS," | grep -q ",$step_num,"; then
        return 0
    fi
    return 1
}

# 步骤号 -> 进度记录名称
step_name_of() {
    case "$1" in
        1) echo "install_acme_cert" ;;
        2) echo "install_xray" ;;
        3) echo "config_xray" ;;
        4) echo "config_xray_service" ;;
        5) echo "enable_bbr" ;;
        6) echo "config_geo_update" ;;
        7) echo "config_log_rotate" ;;
        8) echo "config_ufw" ;;
        9) echo "create_netflix_check" ;;
        *) echo "" ;;
    esac
}

# 保存配置 (用于续装时恢复随机生成的值)
save_config() {
    cat > "$CONFIG_FILE" <<CFGEOF
DOMAIN="$DOMAIN"
UUID="$UUID"
EMAIL="$EMAIL"
REALITY_PORT="$REALITY_PORT"
XHTTP_PORT="$XHTTP_PORT"
REALITY_DEST="$REALITY_DEST"
XHTTP_PATH="$XHTTP_PATH"
REALITY_PRIVATE_KEY="$REALITY_PRIVATE_KEY"
REALITY_PUBLIC_KEY="$REALITY_PUBLIC_KEY"
REALITY_SHORT_ID="$REALITY_SHORT_ID"
LOG_KEEP_DAYS="$LOG_KEEP_DAYS"
CFGEOF
    print_info "配置已保存到 $CONFIG_FILE"
}

# 加载上次保存的配置
load_config() {
    if [[ -f "$CONFIG_FILE" ]]; then
        source "$CONFIG_FILE"
        print_info "已从 $CONFIG_FILE 加载上次配置"
        return 0
    fi
    return 1
}

show_help() {
    echo "Xray 一键部署脚本 (REALITY + XHTTP 双线路)"
    echo ""
    echo "用法: bash $0 -d <域名> -u <UUID> [选项]"
    echo "      bash $0 -c   # 复用已有配置重新安装"
    echo ""
    echo "必填参数 (使用 -c 复用配置时可省略):"
    echo "  -d  域名 (XHTTP 走 Cloudflare 那条线路使用)"
    echo "  -u  UUID (两条线路共用, 可通过 xray uuid 生成)"
    echo ""
    echo "可选参数:"
    echo "  -e  邮箱 (默认: user@email.com)"
    echo "  -R  REALITY 监听端口 (默认: 443, 直连)"
    echo "  -x  XHTTP 监听端口 (默认: 8443, 走 CF)"
    echo "  -D  REALITY 借用站点 host:port (默认: www.bing.com:443)"
    echo "  -W  XHTTP 路径 (默认: /index)"
    echo "  -c  复用已有 config 文件参数重新安装"
    echo "  -L  日志保留天数 (默认: 30)"
    echo "  -y  非交互模式, 自动确认所有提示"
    echo "  -s  只运行指定步骤, 逗号分隔 (如: -s 3,4)"
    echo "       步骤: 1-SSL证书 2-安装Xray 3-密钥+配置Xray 4-Xray服务 5-BBR"
    echo "             6-Geo 7-日志轮转 8-UFW防火墙 9-Netflix检测脚本"
    echo "  -r  重置进度, 从头开始安装 (清除之前的安装进度记录)"
    echo "  -h  显示帮助信息"
    echo ""
    echo "示例:"
    echo "  bash $0 -d example.com -u \"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx\" -e \"user@email.com\""
    echo "  bash $0 -c"
    exit 0
}

check_root() {
    if [[ $EUID -ne 0 ]]; then
        print_error "请使用 root 用户运行此脚本!"
        exit 1
    fi
}

check_os() {
    if [[ ! -f /etc/os-release ]]; then
        print_error "无法检测操作系统!"
        exit 1
    fi
    source /etc/os-release
    if [[ "$ID" != "ubuntu" && "$ID" != "debian" ]]; then
        print_warn "此脚本针对 Ubuntu/Debian 设计, 当前系统: $ID, 可能存在兼容性问题"
    fi
    print_info "当前系统: $PRETTY_NAME"
}

# ======================== 解析参数 ========================

while getopts ":d:u:e:R:x:D:W:L:cys:rh" opt; do
    case "$opt" in
        d) DOMAIN="$OPTARG" ;;
        u) UUID="$OPTARG" ;;
        e) EMAIL="$OPTARG" ;;
        R) REALITY_PORT="$OPTARG" ;;
        x) XHTTP_PORT="$OPTARG" ;;
        D) REALITY_DEST="$OPTARG" ;;
        W) XHTTP_PATH="$OPTARG" ;;
        L) LOG_KEEP_DAYS="$OPTARG" ;;
        c) REUSE_CONFIG=true ;;
        y) AUTO_YES=true ;;
        s) RUN_STEPS="$OPTARG" ;;
        r) RESET_PROGRESS=true ;;
        h) show_help ;;
        :)
            print_error "选项 -$OPTARG 需要参数"
            exit 1
            ;;
        \?)
            print_error "无效选项: -$OPTARG"
            show_help
            ;;
    esac
done

# ======================== 复用已有配置 ========================

# -c: 从已有 config 文件读取参数重新安装
if [[ "$REUSE_CONFIG" == true ]]; then
    if load_config; then
        print_info "已从 $CONFIG_FILE 复用全部参数 (包含 REALITY 密钥与 shortId)"
    else
        print_error "未找到配置文件 $CONFIG_FILE, 无法复用, 请改用 -d -u 手动指定"
        exit 1
    fi
fi

# ======================== 参数校验 ========================

if [[ -z "$DOMAIN" ]]; then
    print_error "域名不能为空! 请使用 -d 指定域名 (或 -c 复用配置)"
    show_help
fi

if [[ -z "$UUID" ]]; then
    print_error "UUID 不能为空! 请使用 -u 指定 UUID (或 -c 复用配置)"
    show_help
fi

# ======================== 重置/续装检测 ========================

if [[ "$RESET_PROGRESS" == true ]]; then
    rm -f "$PROGRESS_FILE" "$CONFIG_FILE"
    print_info "已清除安装进度记录, 将从头开始安装"
fi

if [[ -f "$PROGRESS_FILE" ]]; then
    echo ""
    echo -e "${YELLOW}检测到上次未完成的安装进度:${RESET}"
    echo -e "  已完成步骤:"
    while IFS= read -r line; do
        echo -e "    ${GREEN}✓${RESET} $line"
    done < "$PROGRESS_FILE"
    echo ""
    # 加载上次保存的配置 (恢复随机生成的 PATH 和密码)
    if load_config; then
        print_info "已恢复上次的随机 PATH 和密码配置"
    fi
    print_info "将从上次中断处继续安装 (如需重新开始, 请使用 -r 参数)"
    echo ""
fi

# ======================== 显示配置 ========================

echo ""
echo -e "${GREEN}================ 部署配置 ================${RESET}"
echo -e "  域名:            ${YELLOW}$DOMAIN${RESET}"
echo -e "  UUID (共用):     ${YELLOW}$UUID${RESET}"
echo -e "  邮箱:            ${YELLOW}$EMAIL${RESET}"
echo -e "  REALITY 端口:    ${YELLOW}$REALITY_PORT${RESET}  (直连)"
echo -e "  XHTTP   端口:    ${YELLOW}$XHTTP_PORT${RESET}  (走 Cloudflare)"
echo -e "  REALITY 借用站:  ${YELLOW}$REALITY_DEST${RESET}"
echo -e "  XHTTP   路径:    ${YELLOW}$XHTTP_PATH${RESET}"
echo -e "  日志保留天数:    ${YELLOW}$LOG_KEEP_DAYS${RESET}"
echo -e "  运行步骤:        ${YELLOW}${RUN_STEPS:-全部}${RESET}"
echo -e "${GREEN}===========================================${RESET}"
echo ""

if [[ "$AUTO_YES" != true ]]; then
    read -p "确认以上配置并开始部署? (y/n): " confirm
    if [[ "$confirm" != "y" && "$confirm" != "Y" ]]; then
        print_info "已取消部署"
        exit 0
    fi
fi

# ======================== 检查环境 ========================

check_root
check_os

# ======================== 1. 安装 acme.sh 并申请证书 ========================

if ! should_run_step 1; then
    print_info "[跳过] 1/9 SSL 证书 (未选择此步骤)"
elif step_done "install_acme_cert"; then
    print_info "[跳过] 1/9 SSL 证书已申请部署"
else
    print_step "1/9 安装 acme.sh 并申请 SSL 证书 (standalone 模式)"

    # 证书/日志目录 (Xray 需要, 与 nginx 无关)
    mkdir -p /home/www-data/xray_cert
    mkdir -p /home/www-data/xray_log

    # 安装 acme.sh
    cd /root/
    if [[ ! -d /root/.acme.sh ]]; then
        wget -O - https://get.acme.sh | sh
        . .bashrc
    else
        print_info "acme.sh 已安装, 跳过安装步骤"
    fi

    # 确保 acme.sh 可用
    export PATH="$PATH:/root/.acme.sh"

    # 设置自动更新和默认 CA
    /root/.acme.sh/acme.sh --upgrade --auto-upgrade
    /root/.acme.sh/acme.sh --set-default-ca --server letsencrypt

    print_warn "standalone 模式将占用 80 端口签发, 请确保 80 端口空闲且可从公网访问"
    print_warn "若域名已开启 Cloudflare 橙云, 建议签发时先临时切为灰云(DNS only), 签发后再开橙云"

    # 检查是否已有有效正式证书, 域名未变则跳过, 避免触发 Let's Encrypt 频率限制 (同域名每周 5 张)
    CERT_FILE=/home/www-data/xray_cert/xray.crt
    NEED_ISSUE=true
    if [[ -f "$CERT_FILE" ]]; then
        if openssl x509 -in "$CERT_FILE" -noout -issuer 2>/dev/null | grep -qi "staging"; then
            print_warn "检测到测试(staging)证书, 将重新签发正式证书"
        elif ! openssl x509 -in "$CERT_FILE" -noout -checkend $((30*24*3600)) >/dev/null 2>&1; then
            print_warn "证书剩余有效期不足 30 天, 将重新签发"
        else
            NEED_ISSUE=false
            print_info "检测到有效正式证书 (剩余 >30 天), 域名未变, 跳过签发与部署"
        fi
    fi

    if [[ "$NEED_ISSUE" == true ]]; then
        # 正式签发 (显式指定生产 CA; 容忍 acme.sh 的"跳过"退出码 2, 避免 set -e 误终止)
        print_info "执行签发..."
        set +e
        /root/.acme.sh/acme.sh --issue -d $DOMAIN --standalone --keylength ec-256 --server letsencrypt --force
        _acme_rc=$?
        set -e
        if [[ $_acme_rc -ne 0 && $_acme_rc -ne 2 ]]; then
            print_error "证书签发失败 (exit $_acme_rc)"
            print_error "请确认: 80 端口可从公网访问; 域名解析指向本机; 若已开 CF 橙云请先切灰云(DNS only)"
            exit 1
        fi

        # 部署证书
        print_info "部署证书..."
        /root/.acme.sh/acme.sh --install-cert -d $DOMAIN --ecc \
            --fullchain-file /home/www-data/xray_cert/xray.crt \
            --key-file /home/www-data/xray_cert/xray.key

        chmod +r /home/www-data/xray_cert/*
    fi

    # 创建定时部署脚本
    cat > /home/www-data/xray_cert/xray-cert-renew.sh <<CERTEOF
#!/bin/bash

/root/.acme.sh/acme.sh --install-cert -d $DOMAIN --ecc --fullchain-file /home/www-data/xray_cert/xray.crt --key-file /home/www-data/xray_cert/xray.key
echo "Xray Certificates Renewed"

chmod +r /home/www-data/xray_cert/*
echo "Read Permission Granted for Private Key"

sudo systemctl restart xray
echo "Xray Restarted"

chown -R www-data:www-data /home/www-data/
chmod +x /home/www-data/xray_cert/xray-cert-renew.sh
CERTEOF

    chmod +x /home/www-data/xray_cert/xray-cert-renew.sh

    # 添加证书续期 crontab
    (crontab -l 2>/dev/null | grep -v "xray-cert-renew"; echo "0 1 1 * *   bash /home/www-data/xray_cert/xray-cert-renew.sh") | crontab -

    mark_step_done "install_acme_cert"
fi

# ======================== 2. 安装 xray ========================

if ! should_run_step 2; then
    print_info "[跳过] 2/9 Xray 安装 (未选择此步骤)"
elif step_done "install_xray"; then
    print_info "[跳过] 2/9 Xray 已安装"
else
    print_step "2/9 安装 Xray"

    cd /root/
    wget -O install-release.sh https://github.com/XTLS/Xray-install/raw/main/install-release.sh
    bash install-release.sh
    rm -f install-release.sh

    mark_step_done "install_xray"
fi

# ======================== 3. 生成 REALITY 密钥并配置 xray ========================

if ! should_run_step 3; then
    print_info "[跳过] 3/9 Xray 配置 (未选择此步骤)"
elif step_done "config_xray"; then
    print_info "[跳过] 3/9 Xray 已配置"
else
    print_step "3/9 生成 REALITY 密钥 + 配置 Xray"

    # 复用已有 REALITY 密钥对, 否则用 xray x25519 生成
    if [[ -n "$REALITY_PRIVATE_KEY" && -n "$REALITY_PUBLIC_KEY" ]]; then
        print_info "复用已有 REALITY 密钥对"
    else
        print_info "生成 REALITY 密钥对..."
        _rk=$(xray x25519)
        REALITY_PRIVATE_KEY=$(echo "$_rk" | grep -iE "private" | awk -F'[: ]+' '{print $NF}')
        REALITY_PUBLIC_KEY=$(echo "$_rk" | grep -iE "public|password" | awk -F'[: ]+' '{print $NF}')
    fi

    # 复用已有 shortId, 否则随机生成 (8 字节 = 16 位十六进制)
    if [[ -n "$REALITY_SHORT_ID" ]]; then
        print_info "复用已有 shortId"
    else
        REALITY_SHORT_ID=$(head -c 8 /dev/urandom | xxd -p)
    fi

    # 从 dest 提取 SNI (去掉 :port)
    REALITY_SNI="${REALITY_DEST%%:*}"

    # 配置 sniffing (流量嗅探; routeOnly 避免改写目标地址, 对纯转发/流媒体更稳)
    SNIFFING_BLOCK='
      "sniffing": {
        "enabled": true,
        "destOverride": [
          "http",
          "tls"
        ],
        "routeOnly": true
      },'
        OUTBOUNDS_EXTRA=',
    {
      "tag": "netflix_proxy",
      "protocol": "socks",
      "settings": {
        "servers": [
          {
            "address": "127.0.0.1",
            "port": 40004
          }
        ]
      }
    }'
        ROUTING_BLOCK=',
  "routing": {
    "rules": [
      {
        "type": "field",
        "outboundTag": "direct",
        "network": "udp,tcp"
      }
    ]
  }'

# 生成 xray 配置: 线路A REALITY(直连) + 线路B XHTTP(过 Cloudflare)
cat > /usr/local/etc/xray/config.json <<XRAYEOF
{
  "log": {
    "loglevel": "info",
    "access": "/home/www-data/xray_log/access.log",
    "error": "/home/www-data/xray_log/error.log"
  },
  "inbounds": [
    {${SNIFFING_BLOCK}
      "listen": "0.0.0.0",
      "port": $REALITY_PORT,
      "protocol": "vless",
      "settings": {
        "clients": [
          {
            "id": "$UUID",
            "flow": "xtls-rprx-vision",
            "level": 0,
            "email": "$EMAIL"
          }
        ],
        "decryption": "none"
      },
      "streamSettings": {
        "network": "tcp",
        "security": "reality",
        "realitySettings": {
          "show": false,
          "minClientVer": "1.0.0",
          "dest": "$REALITY_DEST",
          "xver": 0,
          "serverNames": ["$REALITY_SNI"],
          "privateKey": "$REALITY_PRIVATE_KEY",
          "shortIds": ["$REALITY_SHORT_ID"]
        }
      }
    },
    {${SNIFFING_BLOCK}
      "listen": "0.0.0.0",
      "port": $XHTTP_PORT,
      "protocol": "vless",
      "settings": {
        "clients": [
          {
            "id": "$UUID",
            "level": 0,
            "email": "$EMAIL"
          }
        ],
        "decryption": "none"
      },
      "streamSettings": {
        "network": "xhttp",
        "security": "tls",
        "tlsSettings": {
          "alpn": ["h3", "h2"],
          "minVersion": "1.2",
          "maxVersion": "1.3",
          "certificates": [
            {
              "certificateFile": "/home/www-data/xray_cert/xray.crt",
              "keyFile": "/home/www-data/xray_cert/xray.key"
            }
          ]
        },
        "xhttpSettings": {
          "host": "$DOMAIN",
          "path": "$XHTTP_PATH",
          "mode": "auto"
        }
      }
    }
  ],
  "outbounds": [
    {
      "tag": "direct",
      "protocol": "freedom"
    }${OUTBOUNDS_EXTRA}
  ]${ROUTING_BLOCK}
}
XRAYEOF

    print_info "Xray 配置完成"
    print_info "  线路A(直连): VLESS + REALITY + Vision  (端口 $REALITY_PORT, SNI $REALITY_SNI)"
    print_info "  线路B(CF):   VLESS + XHTTP + TLS       (端口 $XHTTP_PORT, PATH $XHTTP_PATH)"
    print_info "  流量嗅探: 已启用 (HTTP/TLS)"

    # 保存配置 (含 REALITY 密钥/shortId), 以便 -c 复用或续装恢复
    save_config
    mark_step_done "config_xray"
fi

# ======================== 4. 配置 xray 以 www-data 用户启动 ========================

if ! should_run_step 4; then
    print_info "[跳过] 4/9 Xray 服务 (未选择此步骤)"
elif step_done "config_xray_service"; then
    print_info "[跳过] 4/9 Xray 服务已配置"
else
    print_step "4/9 配置 Xray 服务"

    # 通过 systemd drop-in 统一使用 www-data 用户启动，不直接改动上游单元文件
    for UNIT_DIR in \
        /etc/systemd/system/xray.service.d \
        /etc/systemd/system/xray@.service.d
    do
        install -d -m 0755 "$UNIT_DIR"
        printf '[Service]\nUser=www-data\nGroup=www-data\n' > "$UNIT_DIR/run-as-www-data.conf"
    done

    # 修改 Xray 日志目录和配置目录所有者
    for DIR in /home/www-data /usr/local/etc/xray; do
        if [[ -d "$DIR" ]]; then
            chown -R www-data:www-data "$DIR"
        fi
    done

    systemctl daemon-reload
    service xray restart

    mark_step_done "config_xray_service"
fi

# ======================== 5. 开启 BBR 加速 ========================

if ! should_run_step 5; then
    print_info "[跳过] 5/9 BBR (未选择此步骤)"
elif step_done "enable_bbr"; then
    print_info "[跳过] 5/9 BBR 已配置"
else
    print_step "5/9 开启 BBR 加速"

    # 检查是否已开启 BBR
    if sysctl net.ipv4.tcp_congestion_control 2>/dev/null | grep -q bbr; then
        print_info "BBR 已开启, 跳过配置"
    else
        # 添加 BBR 配置
        if ! grep -q "net.core.default_qdisc=fq" /etc/sysctl.conf; then
            echo "net.core.default_qdisc=fq" >> /etc/sysctl.conf
        fi
        if ! grep -q "net.ipv4.tcp_congestion_control=bbr" /etc/sysctl.conf; then
            echo "net.ipv4.tcp_congestion_control=bbr" >> /etc/sysctl.conf
        fi
        sysctl -p
        print_info "BBR 配置已添加"
    fi

    mark_step_done "enable_bbr"
fi

# ======================== 6. 自动更新 geo 文件 ========================

if ! should_run_step 6; then
    print_info "[跳过] 6/9 Geo 更新 (未选择此步骤)"
elif step_done "config_geo_update"; then
    print_info "[跳过] 6/9 Geo 自动更新已配置"
else
    print_step "6/9 配置 geo 文件自动更新"

    mkdir -p /root/xray

    cat > /root/xray/updateGeo.sh <<'GEOEOF'
#!/bin/bash

BASE_DIR=$(cd $(dirname $0); pwd)
GEO_DIR=$BASE_DIR/geo
TARGET_DIR="/usr/local/share/xray"
CHECK_STR="100%"

echo "BASE_PID: $$"
echo "BASE_DIR: $BASE_DIR"

if [ ! -d "$GEO_DIR" ]; then
    mkdir $GEO_DIR
fi

status=$(wget -O $GEO_DIR/geoip.dat https://github.com/Loyalsoldier/v2ray-rules-dat/releases/latest/download/geoip.dat 2>&1)
if [[ ! $status =~ $CHECK_STR ]]; then
    echo ">>>>>>>>>>>>>>>>>>>>>>>>>> geoip.dat download fail!!!"
    exit -1
fi

status=$(wget -O $GEO_DIR/geosite.dat https://github.com/Loyalsoldier/v2ray-rules-dat/releases/latest/download/geosite.dat 2>&1)
if [[ ! $status =~ $CHECK_STR ]]; then
    echo ">>>>>>>>>>>>>>>>>>>>>>>>>> geosite.dat download fail!!!"
    exit -1
fi

echo ">>>>>>>>>>>>>>>>>>>>>>>>>> all download success!!!"
cp -f $GEO_DIR/* $TARGET_DIR
GEOEOF

    chmod +x /root/xray/updateGeo.sh

    # 添加 geo 更新 crontab
    (crontab -l 2>/dev/null | grep -v "updateGeo"; echo "0 3 * * *   bash /root/xray/updateGeo.sh >> /root/xray/updateGeo.log 2>&1") | crontab -

    mark_step_done "config_geo_update"
fi

# ======================== 7. 配置日志轮转 ========================
# 每天 00:00 将 access.log/error.log 快照为 access-YYYYMMDD.log, 并清理过期日志。
# 采用 copytruncate 方式 (cp + 清空原文件), 无需重启 Xray 即可持续写入。

if ! should_run_step 7; then
    print_info "[跳过] 7/9 日志轮转 (未选择此步骤)"
elif step_done "config_log_rotate"; then
    print_info "[跳过] 7/9 日志轮转已配置"
else
    print_step "7/9 配置日志轮转 (每天 00:00, 保留 ${LOG_KEEP_DAYS} 天)"

    mkdir -p /root/xray
    mkdir -p /home/www-data/xray_log

    cat > /root/xray/rotateLog.sh <<ROTEOF
#!/bin/bash
LOG_DIR="/home/www-data/xray_log"
KEEP_DAYS=${LOG_KEEP_DAYS}
DATE=\$(date +%Y%m%d)

for name in access error; do
    SRC="\$LOG_DIR/\${name}.log"
    [ -f "\$SRC" ] || continue
    cp -f "\$SRC" "\$LOG_DIR/\${name}-\${DATE}.log"
    : > "\$SRC"
done

# 清理超过保留天数的历史日志 (access-YYYYMMDD.log / error-YYYYMMDD.log)
find "\$LOG_DIR" -maxdepth 1 -type f -name '*-[0-9]*.log' -mtime +\${KEEP_DAYS} -delete
ROTEOF

    chmod +x /root/xray/rotateLog.sh

    # 添加每天 0 点的日志轮转 crontab
    (crontab -l 2>/dev/null | grep -v "rotateLog"; echo "0 0 * * *   bash /root/xray/rotateLog.sh >> /root/xray/rotateLog.log 2>&1") | crontab -

    print_info "日志轮转脚本: /root/xray/rotateLog.sh (每天 00:00, 保留 ${LOG_KEEP_DAYS} 天)"
    mark_step_done "config_log_rotate"
fi

# ======================== 8. 配置 UFW 防火墙 ========================
# 若安装并启用了 ufw, 检查所需端口是否放行, 未放行则提示并在确认后放行 (只加不删)。

if ! should_run_step 8; then
    print_info "[跳过] 8/9 UFW 防火墙 (未选择此步骤)"
elif step_done "config_ufw"; then
    print_info "[跳过] 8/9 UFW 防火墙已处理"
else
    print_step "8/9 检查 UFW 防火墙端口"

    if command -v ufw &>/dev/null; then
        if ufw status 2>/dev/null | grep -qi "Status: active"; then
            _need_ports=("$REALITY_PORT" "$XHTTP_PORT" "80")
            _missing=()
            for _p in "${_need_ports[@]}"; do
                if ! ufw status | grep -qw "$_p"; then
                    _missing+=("$_p")
                fi
            done

            if [ ${#_missing[@]} -gt 0 ]; then
                print_warn "UFW 已启用, 以下端口未放行: ${_missing[*]}"
                echo "  将执行以下命令放行 (TCP):"
                for _p in "${_missing[@]}"; do
                    echo "    ufw allow ${_p}/tcp"
                done

                _do_open=true
                if [[ "$AUTO_YES" != true ]]; then
                    read -p "是否放行以上端口? (y/n): " _ans
                    [[ "$_ans" == "y" || "$_ans" == "Y" ]] || _do_open=false
                fi

                if [[ "$_do_open" == true ]]; then
                    for _p in "${_missing[@]}"; do
                        ufw allow "${_p}/tcp"
                    done
                    ufw reload
                    print_info "已放行端口: ${_missing[*]}"
                else
                    print_warn "已跳过放行, 请手动开放: ${_missing[*]}"
                fi
            else
                print_info "UFW 已启用, 所需端口 (${_need_ports[*]}) 均已放行"
            fi
        else
            print_info "UFW 已安装但未启用 (不拦截端口), 无需放行"
        fi
    else
        print_info "未检测到 ufw, 跳过防火墙检查"
    fi

    mark_step_done "config_ufw"
fi

# ======================== 9. 创建 Netflix 检测脚本 ========================

if ! should_run_step 9; then
    print_info "[跳过] 9/9 Netflix 检测脚本 (未选择此步骤)"
elif step_done "create_netflix_check"; then
    print_info "[跳过] 9/9 Netflix 检测脚本已创建"
else
    print_step "9/9 创建 Netflix 检测脚本"

    mkdir -p /root/netflix

    cat > /root/netflix/my_check.sh <<'NFCHECKEOF'
#!/bin/bash
shopt -s expand_aliases
Font_Black="\033[30m"
Font_Red="\033[31m"
Font_Green="\033[32m"
Font_Yellow="\033[33m"
Font_Blue="\033[34m"
Font_Purple="\033[35m"
Font_SkyBlue="\033[36m"
Font_White="\033[37m"
Font_Suffix="\033[0m"

networkType=""
useNIC=""
usePROXY=""
UA_Browser="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.87 Safari/537.36"

while getopts ":I:M:P:X:" optname; do
    case "$optname" in
    "I")
        iface="$OPTARG"
        useNIC="--interface $iface"
        ;;
    "M")
        if [[ "$OPTARG" == "4" ]]; then
            networkType=4
        elif [[ "$OPTARG" == "6" ]]; then
            networkType=6
        fi
        ;;
    "P")
        proxy="$OPTARG"
        usePROXY="-x $proxy"
        ;;
    "X")
        proxy="$OPTARG"
        usePROXY="--socks5 $proxy"
        ;;
    ":")
        echo "Unknown error while processing options"
        exit 1
        ;;
    esac
done

function echo_Result() {
    for((i=0;i<${#array[@]};i++))
    do
        echo "$result" | grep "${array[i]}"
        sleep 0.03
    done;
}

function MediaUnlockTest_Netflix() {
    local result1=$(curl $useNIC $usePROXY -${1} --user-agent "${UA_Browser}" -fsL --write-out %{http_code} --output /dev/null --max-time 10 "https://www.netflix.com/title/81280792" 2>&1)
    echo "result1: $result1"
    if [[ "$result1" == "404" ]]; then
        echo -n -e "\r Netflix:\t\t\t\t${Font_Yellow}Originals Only${Font_Suffix}\n"
        return
    elif [[ "$result1" == "403" ]]; then
        echo -n -e "\r Netflix:\t\t\t\t${Font_Red}No${Font_Suffix}\n"
        return
    elif [[ "$result1" == "200" ]]; then
        local region=$(curl $useNIC $usePROXY -${1} --user-agent "${UA_Browser}" -fs --max-time 10 --write-out %{redirect_url} --output /dev/null "https://www.netflix.com/title/80018499" 2>&1 | cut -d '/' -f4 | cut -d '-' -f1 | tr [:lower:] [:upper:])
        if [[ ! -n "$region" ]]; then
            region="US"
        fi
        echo -n -e "\r Netflix:\t\t\t\t${Font_Green}Yes (Region: ${region})${Font_Suffix}\n"
        return
    elif [[ "$result1" == "000" ]]; then
        echo -n -e "\r Netflix:\t\t\t\t${Font_Red}Failed (Network Connection)${Font_Suffix}\n"
        return
    fi
}

function Global_UnlockTest() {
    echo ""
    echo "============[ Multination ]============"
    local result=$(
    MediaUnlockTest_Netflix ${1}
    )
    wait
    local array=("Netflix:")
    echo_Result ${result} ${array}
    echo "======================================="
}

echo "============[ prams ]============"
echo "networkType: $networkType"
echo "useNIC: $useNIC"
echo "usePROXY: $usePROXY"
echo "================================="

if  [ "" != "$networkType" ] ;then
    Global_UnlockTest $networkType
else
    Global_UnlockTest 4
    Global_UnlockTest 6
fi
NFCHECKEOF

    chmod +x /root/netflix/my_check.sh

    print_info "Netflix 检测脚本已创建: /root/netflix/my_check.sh"
    print_info "使用示例:"
    echo "  检测本机:         bash /root/netflix/my_check.sh -M 4"
    echo "  检测 socks5 代理: bash /root/netflix/my_check.sh -M 4 -X 127.0.0.1:40004"

    mark_step_done "create_netflix_check"
fi

# ======================== 重启 Xray 服务 ========================

# 检查 xray 是否已安装
if command -v xray &> /dev/null && [[ -f /etc/systemd/system/xray.service ]]; then
    print_step "重启 Xray 服务"
    systemctl daemon-reload
    service xray restart
    print_info "Xray 服务已重启"
else
    print_warn "Xray 尚未安装，跳过重启"
fi

# ======================== 部署完成 ========================

# 全部完成, 清理进度文件 (保留 CONFIG_FILE 以便 -c 复用/查阅密钥)
if [[ -z "$RUN_STEPS" ]] && [[ -f "$PROGRESS_FILE" ]]; then
    rm -f "$PROGRESS_FILE"
    print_info "安装进度文件已清理 (配置文件 $CONFIG_FILE 已保留)"
elif [[ -n "$RUN_STEPS" ]]; then
    # -s 指定的步骤全部完成后清理进度文件, 否则保留以便续装
    all_specified_done=true
    IFS=',' read -ra _run_step_list <<< "$RUN_STEPS"
    for _s in "${_run_step_list[@]}"; do
        _name=$(step_name_of "$_s")
        if [[ -z "$_name" ]] || ! step_done "$_name"; then
            all_specified_done=false
            break
        fi
    done

    if [[ "$all_specified_done" == true ]]; then
        rm -f "$PROGRESS_FILE"
        print_info "指定步骤 ($RUN_STEPS) 已全部完成, 进度文件已清理 (配置文件 $CONFIG_FILE 已保留)"
    else
        print_info "指定步骤 ($RUN_STEPS) 尚未全部完成, 进度文件已保留以便续装"
    fi
fi

echo ""
echo -e "${GREEN}============================================================${RESET}"
echo -e "${GREEN}              Xray 一键部署完成!${RESET}"
echo -e "${GREEN}============================================================${RESET}"
echo ""

# 获取服务器公网 IP (REALITY 直连使用)
SERVER_IP=$(curl -s --max-time 5 https://api.ipify.org 2>/dev/null || hostname -I | awk '{print $1}')
REALITY_SNI="${REALITY_DEST%%:*}"

# 若变量为空(如本次跳过了配置步骤), 从现有 xray 配置恢复 pbk/sid
XRAY_CFG="/usr/local/etc/xray/config.json"
if [[ -z "$REALITY_SHORT_ID" && -f "$XRAY_CFG" ]]; then
    REALITY_SHORT_ID=$(grep -oP '"shortIds":\s*\[\s*"\K[^"]*' "$XRAY_CFG" | head -n1)
fi
if [[ -z "$REALITY_PUBLIC_KEY" && -f "$XRAY_CFG" ]] && command -v xray &>/dev/null; then
    _priv=$(grep -oP '"privateKey":\s*"\K[^"]+' "$XRAY_CFG")
    if [[ -n "$_priv" ]]; then
        REALITY_PUBLIC_KEY=$(xray x25519 -i "$_priv" 2>/dev/null | grep -iE "public|password" | awk -F'[: ]+' '{print $NF}')
    fi
fi

echo -e "  ${BLUE}域名:${RESET}              $DOMAIN"
echo -e "  ${BLUE}服务器 IP:${RESET}         $SERVER_IP"
echo -e "  ${BLUE}UUID (共用):${RESET}       $UUID"
echo -e "  ${BLUE}邮箱:${RESET}              $EMAIL"
echo ""
echo -e "  ${YELLOW}=== 线路A: VLESS + REALITY + Vision (直连 VPS) ===${RESET}"
echo -e "    地址(address):   ${GREEN}$SERVER_IP${RESET}"
echo -e "    端口(port):      ${GREEN}$REALITY_PORT${RESET}"
echo -e "    UUID:            ${GREEN}$UUID${RESET}"
echo -e "    流控(flow):      ${GREEN}xtls-rprx-vision${RESET}"
echo -e "    传输(network):   ${GREEN}tcp${RESET}"
echo -e "    安全(security):  ${GREEN}reality${RESET}"
echo -e "    SNI:             ${GREEN}$REALITY_SNI${RESET}"
echo -e "    指纹(fp):        ${GREEN}chrome${RESET}"
echo -e "    公钥(pbk):       ${GREEN}$REALITY_PUBLIC_KEY${RESET}"
echo -e "    shortId(sid):    ${GREEN}$REALITY_SHORT_ID${RESET}"
echo ""
echo -e "  ${YELLOW}=== 线路B: VLESS + XHTTP + TLS (经 Cloudflare) ===${RESET}"
echo -e "    地址(address):   ${GREEN}$DOMAIN${RESET}  (Cloudflare 橙云)"
echo -e "    端口(port):      ${GREEN}$XHTTP_PORT${RESET}  (客户端连 CF 的 $XHTTP_PORT 端口)"
echo -e "    UUID:            ${GREEN}$UUID${RESET}"
echo -e "    传输(network):   ${GREEN}xhttp${RESET}"
echo -e "    路径(path):      ${GREEN}$XHTTP_PATH${RESET}"
echo -e "    Host:            ${GREEN}$DOMAIN${RESET}"
echo -e "    安全(security):  ${GREEN}tls${RESET}"
echo -e "    SNI:             ${GREEN}$DOMAIN${RESET}"
echo ""
echo -e "  ${YELLOW}Cloudflare 配置提醒:${RESET}"
echo -e "    1. 将 $DOMAIN 的 A 记录指向 $SERVER_IP, 开启橙云(Proxied)"
echo -e "    2. SSL/TLS 模式设为 Full (Strict)"
echo -e "    3. 客户端线路B 连接 ${GREEN}$DOMAIN:$XHTTP_PORT${RESET} (CF 支持的 HTTPS 端口)"
echo ""
echo -e "  ${YELLOW}防火墙需放行端口:${RESET} ${GREEN}$REALITY_PORT (REALITY), $XHTTP_PORT (XHTTP), 80 (证书签发)${RESET}"
echo ""
echo -e "  ${YELLOW}UFW 防火墙常用命令:${RESET}"
echo -e "    查看状态:  ${GREEN}ufw status verbose${RESET}"
echo -e "    开放端口:  ${GREEN}ufw allow <端口>/tcp${RESET}"
echo -e "    关闭端口:  ${GREEN}ufw delete allow <端口>/tcp${RESET}"
echo ""
echo -e "  ${YELLOW}Crontab 定时任务:${RESET}"
echo -e "    - 证书续期: 每月1日 01:00 (acme.sh standalone 自动续期)"
echo -e "    - Geo更新:  每天 03:00"
echo ""
echo -e "  ${YELLOW}检测命令:${RESET}"
echo -e "    检测本机 IP4 地址:         ${GREEN}bash /root/netflix/my_check.sh -M 4${RESET}"
echo -e "    检测本机 IP6 地址:         ${GREEN}bash /root/netflix/my_check.sh -M 6${RESET}"
echo -e "    检测 socks5 代理: ${GREEN}bash /root/netflix/my_check.sh -M 4 -X 127.0.0.1:40004${RESET}"
echo ""
echo -e "  ${YELLOW}常用命令:${RESET}"
echo -e "    查看 Xray 状态:  ${GREEN}systemctl status xray${RESET}"
echo -e "    重启 Xray:       ${GREEN}service xray restart${RESET}"
echo -e "    复用配置重装:    ${GREEN}bash $0 -c${RESET}"
echo -e "    查看 crontab:    ${GREEN}crontab -l${RESET}"
echo ""
echo -e "${GREEN}============================================================${RESET}"
echo ""
print_warn "建议: 重启服务器以确保 BBR 加速生效: reboot"
