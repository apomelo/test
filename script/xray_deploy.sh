#!/bin/bash
###############################################################################
# Xray 一键部署脚本
# 适用系统: Ubuntu 24.04 LTS+
# 用法:
#   bash xray_deploy.sh -d <域名> -u <UUID> [-e <email>] \
#       [-p <xray端口>] [-T <trojan密码>] [-s <步骤>] [-r] [-h]
#
# 参数说明:
#   -d  域名 (必填)
#   -u  UUID (必填, 可通过 xray uuid 生成)
#   -e  邮箱 (可选, 默认 user@email.com)
#   -p  xray 监听端口 (可选, 默认 443)
#   -T  Trojan 密码 (可选, 不指定则随机生成)
#   -W  VLESS-WS 路径 (可选, 默认 /index)
#   -s  只运行指定步骤, 逗号分隔 (可选, 如 -s 4,5,6)
#   -r  重置进度, 从头开始安装
#   -h  显示帮助信息
#
# 步骤编号:
#   1 - 安装 Nginx           5 - 配置 Xray          9 - 配置 geo 自动更新
#   2 - 配置 Nginx (HTTP)    6 - 配置 Xray 服务     10 - 创建 Netflix 检测脚本
#   3 - 申请 SSL 证书        7 - 配置 Nginx (HTTPS)
#   4 - 安装 Xray            8 - 开启 BBR 加速
#
# 协议说明:
#   VLESS + TCP + TLS + xtls-rprx-vision (主协议)
#   Trojan + TCP + TLS (回落协议)
#   VLESS + WS + TLS (WebSocket 备用协议)
#
# 断点续装:
#   脚本会在 /root/.xray_deploy_progress 记录已完成的步骤
#   如果某步骤失败, 重新运行相同命令即可从失败处继续
#   如需从头开始, 加 -r 参数清除进度记录
#
# 示例:
#   bash xray_deploy.sh -d example.com -u "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -e "user@email.com"
#   bash xray_deploy.sh -d example.com -u "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx" -e "user@email.com" -p 8443
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
XRAY_PORT=443
TROJAN_PASSWORD=""
WS_PATH="/index"
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

# 保存配置 (用于续装时恢复随机生成的值)
save_config() {
    cat > "$CONFIG_FILE" <<CFGEOF
DOMAIN="$DOMAIN"
UUID="$UUID"
EMAIL="$EMAIL"
XRAY_PORT="$XRAY_PORT"
TROJAN_PASSWORD="$TROJAN_PASSWORD"
WS_PATH="$WS_PATH"
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
    echo "Xray 一键部署脚本"
    echo ""
    echo "用法: bash $0 -d <域名> -u <UUID> [选项]"
    echo ""
    echo "必填参数:"
    echo "  -d  域名"
    echo "  -u  UUID (可通过 xray uuid 生成)"
    echo ""
    echo "可选参数:"
    echo "  -e  邮箱 (默认: user@email.com)"
    echo "  -p  xray 监听端口 (默认: 443)"
    echo "  -T  Trojan 密码 (不指定则随机生成)"
    echo "  -W  VLESS-WS 路径 (默认: /index)"
    echo "  -s  只运行指定步骤, 逗号分隔 (如: -s 4,5,6)"
    echo "       步骤: 1-Nginx安装 2-Nginx配置 3-SSL证书 4-Xray安装"
    echo "             5-Xray配置 6-Xray服务 7-Nginx HTTPS 8-BBR 9-Geo 10-Netflix检测脚本"
    echo "  -r  重置进度, 从头开始安装 (清除之前的安装进度记录)"
    echo "  -h  显示帮助信息"
    echo ""
    echo "示例:"
    echo "  bash $0 -d example.com -u \"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx\" -e \"user@email.com\""
    echo "  bash $0 -d example.com -u \"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx\" -e \"user@email.com\" -p 8443"
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

while getopts ":d:u:e:p:T:W:s:rh" opt; do
    case "$opt" in
        d) DOMAIN="$OPTARG" ;;
        u) UUID="$OPTARG" ;;
        e) EMAIL="$OPTARG" ;;
        p) XRAY_PORT="$OPTARG" ;;
        T) TROJAN_PASSWORD="$OPTARG" ;;
        W) WS_PATH="$OPTARG" ;;
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

# ======================== 参数校验 ========================

if [[ -z "$DOMAIN" ]]; then
    print_error "域名不能为空! 请使用 -d 指定域名"
    show_help
fi

if [[ -z "$UUID" ]]; then
    print_error "UUID 不能为空! 请使用 -u 指定 UUID"
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
echo -e "  域名:           ${YELLOW}$DOMAIN${RESET}"
echo -e "  UUID:           ${YELLOW}$UUID${RESET}"
echo -e "  邮箱:           ${YELLOW}$EMAIL${RESET}"
echo -e "  Xray 端口:      ${YELLOW}$XRAY_PORT${RESET}"
echo -e "  Trojan 密码:    ${YELLOW}${TROJAN_PASSWORD:-<随机生成>}${RESET}"
echo -e "  WS 路径:        ${YELLOW}$WS_PATH${RESET}"
echo -e "  运行步骤:       ${YELLOW}${RUN_STEPS:-全部}${RESET}"
echo -e "${GREEN}===========================================${RESET}"
echo ""

read -p "确认以上配置并开始部署? (y/n): " confirm
if [[ "$confirm" != "y" && "$confirm" != "Y" ]]; then
    print_info "已取消部署"
    exit 0
fi

# ======================== 检查环境 ========================

check_root
check_os

# ======================== 1. 安装 nginx ========================

if ! should_run_step 1; then
    print_info "[跳过] 1/10 Nginx (未选择此步骤)"
elif step_done "install_nginx"; then
    print_info "[跳过] 1/10 Nginx 已安装"
else
    print_step "1/10 安装 Nginx"

    # 获取 Ubuntu 版本号 (如 20.04, 22.04, 24.04)
    UBUNTU_VER=$(lsb_release -rs 2>/dev/null || grep VERSION_ID /etc/os-release | cut -d'"' -f2)
    UBUNTU_VER_MAJOR=$(echo "$UBUNTU_VER" | cut -d'.' -f1)

    if [[ "$UBUNTU_VER_MAJOR" -lt 24 ]]; then
        # Ubuntu 24.04 以下版本通过 PPA 安装最新稳定版 nginx
        print_info "Ubuntu $UBUNTU_VER: 通过 PPA 安装 Nginx"
        apt install -y software-properties-common
        add-apt-repository -y ppa:nginx/stable
    else
        # Ubuntu 24.04+ 默认源已包含较新版本 nginx, 无需 PPA
        print_info "Ubuntu $UBUNTU_VER: 使用默认源安装 Nginx"
    fi

    apt update -y
    apt install -y nginx

    mark_step_done "install_nginx"
fi

# ======================== 2. 配置 nginx (初始) ========================

if ! should_run_step 2; then
    print_info "[跳过] 2/10 Nginx 配置 (未选择此步骤)"
elif step_done "config_nginx_init"; then
    print_info "[跳过] 2/10 Nginx 初始配置已完成"
else
    print_step "2/10 配置 Nginx (初始 HTTP)"

    # 创建网站目录
    mkdir -p /home/www-data/$DOMAIN
    mkdir -p /home/www-data/xray_cert
    mkdir -p /home/www-data/xray_log
    mkdir -p /home/www-data/404

    # 创建默认首页
    cat > /home/www-data/$DOMAIN/index.html <<EOF
<!DOCTYPE html>
<html>
<head><title>Welcome</title></head>
<body><h1>Welcome to $DOMAIN</h1></body>
</html>
EOF

    # 删除默认配置
    rm -f /etc/nginx/sites-available/default
    rm -f /etc/nginx/sites-enabled/default

    # 创建初始 nginx 配置 (仅 HTTP, 用于 acme 证书签发)
    cat > /etc/nginx/sites-available/xray <<NGINXEOF
server {
    listen 80 default_server;
    listen [::]:80 default_server;

    server_name $DOMAIN;

    root /home/www-data/$DOMAIN;
    index index.html index.htm index.nginx-debian.html;
}
NGINXEOF

    ln -sf /etc/nginx/sites-available/xray /etc/nginx/sites-enabled/xray
    chown -R www-data:www-data /home/www-data/

    # 测试 nginx 配置
    nginx -t
    service nginx restart

    mark_step_done "config_nginx_init"
fi

# ======================== 3. 安装 acme.sh 并申请证书 ========================

if ! should_run_step 3; then
    print_info "[跳过] 3/10 SSL 证书 (未选择此步骤)"
elif step_done "install_acme_cert"; then
    print_info "[跳过] 3/10 SSL 证书已申请部署"
else
    print_step "3/10 安装 acme.sh 并申请 SSL 证书"

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

    # 测试签发
    print_info "执行测试签发..."
    /root/.acme.sh/acme.sh --issue --test -d $DOMAIN -w /home/www-data/$DOMAIN --keylength ec-256

    # 正式签发
    print_info "执行正式签发..."
    /root/.acme.sh/acme.sh --issue -d $DOMAIN -w /home/www-data/$DOMAIN --keylength ec-256 --force

    # 部署证书
    print_info "部署证书..."
    /root/.acme.sh/acme.sh --install-cert -d $DOMAIN --ecc \
        --fullchain-file /home/www-data/xray_cert/xray.crt \
        --key-file /home/www-data/xray_cert/xray.key

    chmod +r /home/www-data/xray_cert/*

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

# ======================== 4. 安装 xray ========================

if ! should_run_step 4; then
    print_info "[跳过] 4/10 Xray 安装 (未选择此步骤)"
elif step_done "install_xray"; then
    print_info "[跳过] 4/10 Xray 已安装"
else
    print_step "4/10 安装 Xray"

    cd /root/
    wget -O install-release.sh https://github.com/XTLS/Xray-install/raw/main/install-release.sh
    bash install-release.sh
    rm -f install-release.sh

    mark_step_done "install_xray"
fi

# ======================== 5. 配置 xray ========================

if ! should_run_step 5; then
    print_info "[跳过] 5/10 Xray 配置 (未选择此步骤)"
elif step_done "config_xray"; then
    print_info "[跳过] 5/10 Xray 已配置"
else
    print_step "5/10 配置 Xray"

    # TROJAN_PASSWORD 为空时随机生成
    if [[ -z "$TROJAN_PASSWORD" ]]; then
        TROJAN_PASSWORD=$(head -c 16 /dev/urandom | xxd -p)
    fi

    # 配置 sniffing (流量嗅探)
    SNIFFING_BLOCK='
      "sniffing": {
        "enabled": true,
        "destOverride": [
          "http",
          "tls"
        ]
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

# 生成 xray 配置
cat > /usr/local/etc/xray/config.json <<XRAYEOF
{
  "log": {
    "loglevel": "warning",
    "access": "/home/www-data/xray_log/access.log",
    "error": "/home/www-data/xray_log/error.log"
  },
  "inbounds": [
    {${SNIFFING_BLOCK}
      "port": $XRAY_PORT,
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
        "decryption": "none",
        "fallbacks": [
          {
            "dest": 1310,
            "xver": 1
          },
          {
            "path": "$WS_PATH",
            "dest": 1234,
            "xver": 1
          },
          {
            "dest": 8080
          }
        ]
      },
      "streamSettings": {
        "network": "tcp",
        "security": "tls",
        "tlsSettings": {
          "alpn": ["h2", "http/1.1"],
          "minVersion": "1.2",
          "maxVersion": "1.3",
          "certificates": [
            {
              "certificateFile": "/home/www-data/xray_cert/xray.crt",
              "keyFile": "/home/www-data/xray_cert/xray.key"
            }
          ]
        }
      }
    },
    {
      "port": 1310,
      "listen": "127.0.0.1",
      "protocol": "trojan",
      "settings": {
        "clients": [
          {
            "password": "$TROJAN_PASSWORD",
            "level": 0,
            "email": "$EMAIL"
          }
        ],
        "fallbacks": [
          {
            "dest": 8080
          }
        ]
      },
      "streamSettings": {
        "network": "tcp",
        "security": "none",
        "tcpSettings": {
          "acceptProxyProtocol": true
        }
      }
    },
    {
      "port": 1234,
      "listen": "127.0.0.1",
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
        "network": "ws",
        "security": "none",
        "wsSettings": {
          "acceptProxyProtocol": true,
          "path": "$WS_PATH"
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
    print_info "  主协议: VLESS + TCP + TLS + Vision"
    print_info "  Trojan: Trojan + TCP + TLS (密码: $TROJAN_PASSWORD)"
    print_info "  VLESS-WS: VLESS + WebSocket + TLS (PATH: $WS_PATH)"
    print_info "  流量嗅探: 已启用 (HTTP/TLS)"

    # 保存配置 (含随机生成的值), 以便续装时恢复
    save_config
    mark_step_done "config_xray"
fi

# ======================== 6. 配置 xray 以 www-data 用户启动 ========================

if ! should_run_step 6; then
    print_info "[跳过] 6/10 Xray 服务 (未选择此步骤)"
elif step_done "config_xray_service"; then
    print_info "[跳过] 6/10 Xray 服务已配置"
else
    print_step "6/10 配置 Xray 服务"

    # 修改 xray 服务配置, 使用 www-data 用户启动
    if [[ -f /etc/systemd/system/xray.service ]]; then
        sed -i 's/User=nobody/User=www-data/g' /etc/systemd/system/xray.service
    fi
    if [[ -f /etc/systemd/system/xray@.service ]]; then
        sed -i 's/User=nobody/User=www-data/g' /etc/systemd/system/xray@.service
    fi

    systemctl daemon-reload
    service xray restart

    mark_step_done "config_xray_service"
fi

# ======================== 7. 重新配置 nginx: HTTPS 跳转 ========================

if ! should_run_step 7; then
    print_info "[跳过] 7/10 Nginx HTTPS (未选择此步骤)"
elif step_done "config_nginx_https"; then
    print_info "[跳过] 7/10 Nginx HTTPS 跳转已配置"
else
    print_step "7/10 配置 Nginx (HTTPS 跳转 + 回落)"

    # 创建默认 404 页面
    cat > /home/www-data/404/404.html <<EOF404
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>404 Not Found</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; padding-top: 100px; background: #f5f5f5; }
        h1 { font-size: 80px; color: #333; margin-bottom: 0; }
        p { font-size: 20px; color: #666; }
    </style>
</head>
<body>
    <h1>404</h1>
    <p>Page Not Found</p>
</body>
</html>
EOF404

    # 生成 HSTS 配置 (仅在端口为 443 时添加)
    HSTS_CONFIG=""
    if [[ "$XRAY_PORT" == "443" ]]; then
        HSTS_CONFIG='add_header Strict-Transport-Security "max-age=31536000" always;'
    fi

    # 更新 nginx 配置
    cat > /etc/nginx/sites-available/xray <<NGINXEOF2
server {
    listen 80 default_server;
    listen [::]:80 default_server;

    server_name $DOMAIN;

    return 301 https://\$http_host:${XRAY_PORT}\$request_uri;
}

server {
    listen 127.0.0.1:8080;
    listen [::1]:8080;

    root /home/www-data/$DOMAIN;

    index index.html index.htm index.nginx-debian.html;

    location / {
        try_files \$uri \$uri/ =404;
    }

    error_page  404 403 500 502 503 504  /404.html;
    location = /404.html {
        root   /home/www-data/404/;
    }

    location /404/ {
        root   /home/www-data/;
    }

    $HSTS_CONFIG
}
NGINXEOF2

    chown -R www-data:www-data /home/www-data/

    nginx -t
    service nginx restart

    mark_step_done "config_nginx_https"
fi

# ======================== 8. 开启 BBR 加速 ========================

if ! should_run_step 8; then
    print_info "[跳过] 8/10 BBR (未选择此步骤)"
elif step_done "enable_bbr"; then
    print_info "[跳过] 8/10 BBR 已配置"
else
    print_step "8/10 开启 BBR 加速"

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

# ======================== 9. 自动更新 geo 文件 ========================

if ! should_run_step 9; then
    print_info "[跳过] 9/10 Geo 更新 (未选择此步骤)"
elif step_done "config_geo_update"; then
    print_info "[跳过] 9/10 Geo 自动更新已配置"
else
    print_step "9/10 配置 geo 文件自动更新"

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

# ======================== 10. 创建 Netflix 检测脚本 ========================

if ! should_run_step 10; then
    print_info "[跳过] 10/10 Netflix 检测脚本 (未选择此步骤)"
elif step_done "create_netflix_check"; then
    print_info "[跳过] 10/10 Netflix 检测脚本已创建"
else
    print_step "10/10 创建 Netflix 检测脚本"

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

# 全部完成, 清理进度文件 (仅在运行全部步骤时清理)
if [[ -z "$RUN_STEPS" ]] && [[ -f "$PROGRESS_FILE" ]]; then
    rm -f "$PROGRESS_FILE" "$CONFIG_FILE"
    print_info "安装进度文件已清理"
elif [[ -n "$RUN_STEPS" ]]; then
    print_info "仅运行了指定步骤 ($RUN_STEPS), 进度文件已保留"
fi

echo ""
echo -e "${GREEN}============================================================${RESET}"
echo -e "${GREEN}              Xray 一键部署完成!${RESET}"
echo -e "${GREEN}============================================================${RESET}"
echo ""
echo -e "  ${BLUE}域名:${RESET}              $DOMAIN"
echo -e "  ${BLUE}Xray 端口:${RESET}         $XRAY_PORT"
echo -e "  ${BLUE}协议:${RESET}              VLESS + TCP + TLS (xtls-rprx-vision)"
echo -e "  ${BLUE}Trojan:${RESET}            Trojan + TCP + TLS"
echo -e "  ${BLUE}Trojan 密码:${RESET}       $TROJAN_PASSWORD"
echo -e "  ${BLUE}VLESS-WS:${RESET}          VLESS + WebSocket + TLS (PATH: $WS_PATH)"
echo -e "  ${BLUE}UUID:${RESET}              $UUID"
echo -e "  ${BLUE}邮箱:${RESET}              $EMAIL"
echo ""
echo -e "  ${YELLOW}Crontab 定时任务:${RESET}"
echo -e "    - 证书续期: 每月1日 01:00"
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
echo -e "    查看 Nginx 状态: ${GREEN}systemctl status nginx${RESET}"
echo -e "    重启 Nginx:      ${GREEN}service nginx restart${RESET}"
echo -e "    查看 crontab:    ${GREEN}crontab -l${RESET}"
echo ""
echo -e "${GREEN}============================================================${RESET}"
echo ""
print_warn "建议: 重启服务器以确保 BBR 加速生效: reboot"
