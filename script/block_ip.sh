#!/bin/bash

DEFAULT_DURATION=15
DURATION="$DEFAULT_DURATION"
DOCKER_CONTAINER=""

usage() {
    echo "用法:"
    echo "  $0 [选项] <IP/域名> [IP/域名 ...] [时间秒]"
    echo
    echo "选项:"
    echo "  -d <容器名>    屏蔽指定 Docker 容器的 TCP 连接"
    echo "  -h              显示帮助"
    echo
    echo "示例:"
    echo "  sudo $0 1.2.3.4"
    echo "  sudo $0 1.2.3.4 5.6.7.8 30"
    echo "  sudo $0 test.test.com 30"
    echo "  sudo $0 -d test 1.2.3.4"
    echo "  sudo $0 -d test 1.2.3.4 5.6.7.8 30"
    echo "  sudo $0 -d test test.test.com 30"
}

# -----------------------------
# 解析参数
# -----------------------------

ARGS=()

while [[ $# -gt 0 ]]; do
    case "$1" in
        -d)
            if [ -z "$2" ]; then
                echo "错误：-d 后面必须指定 Docker 容器名"
                usage
                exit 1
            fi

            DOCKER_CONTAINER="$2"
            shift 2
            ;;

        -h|--help)
            usage
            exit 0
            ;;

        -*)
            echo "错误：未知参数 $1"
            usage
            exit 1
            ;;

        *)
            ARGS+=("$1")
            shift
            ;;
    esac
done

# -----------------------------
# 检查参数
# -----------------------------

if [ "${#ARGS[@]}" -eq 0 ]; then
    echo "错误：没有输入 IP 或域名"
    usage
    exit 1
fi

# 最后一个参数是纯数字时，作为时间
LAST_INDEX=$((${#ARGS[@]} - 1))

if [[ "${ARGS[$LAST_INDEX]}" =~ ^[0-9]+$ ]]; then
    DURATION="${ARGS[$LAST_INDEX]}"
    unset 'ARGS[$LAST_INDEX]'
fi

if [ "$DURATION" -le 0 ]; then
    echo "错误：时间必须大于 0 秒"
    exit 1
fi

# -----------------------------
# 检查 root
# -----------------------------

if [ "$EUID" -ne 0 ]; then
    echo "错误：请使用 sudo 运行"
    exit 1
fi

# -----------------------------
# Docker 模式
# -----------------------------

CONTAINER_IP=""

if [ -n "$DOCKER_CONTAINER" ]; then

    # 检查 Docker 容器是否存在
    if ! docker inspect "$DOCKER_CONTAINER" >/dev/null 2>&1; then
        echo "错误：Docker 容器不存在: $DOCKER_CONTAINER"
        exit 1
    fi

    # 检查容器是否运行
    RUNNING=$(docker inspect -f '{{.State.Running}}' "$DOCKER_CONTAINER")

    if [ "$RUNNING" != "true" ]; then
        echo "错误：Docker 容器没有运行: $DOCKER_CONTAINER"
        exit 1
    fi

    # 获取容器 IP
    CONTAINER_IP=$(docker inspect -f \
        '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' \
        "$DOCKER_CONTAINER")

    if [ -z "$CONTAINER_IP" ]; then
        echo "错误：无法获取 Docker 容器 IP: $DOCKER_CONTAINER"
        exit 1
    fi
fi

# -----------------------------
# 解析 IP / 域名
# -----------------------------

IPS=()

for TARGET in "${ARGS[@]}"; do

    # IPv4
    if [[ "$TARGET" =~ ^([0-9]{1,3}\.){3}[0-9]{1,3}$ ]]; then
        IPS+=("$TARGET")
        continue
    fi

    echo "正在解析: $TARGET"

    RESOLVED_IPS=$(getent ahostsv4 "$TARGET" | awk '{print $1}' | sort -u)

    if [ -z "$RESOLVED_IPS" ]; then
        echo "警告：无法解析 $TARGET，跳过"
        continue
    fi

    while read -r IP; do
        [ -n "$IP" ] && IPS+=("$IP")
    done <<< "$RESOLVED_IPS"

done

# 去重
IPS=($(printf "%s\n" "${IPS[@]}" | sort -u))

if [ "${#IPS[@]}" -eq 0 ]; then
    echo "错误：没有找到有效 IP"
    exit 1
fi

# -----------------------------
# 显示信息
# -----------------------------

echo
echo "========================================"

if [ -n "$DOCKER_CONTAINER" ]; then
    echo "模式: Docker 容器"
    echo "容器: $DOCKER_CONTAINER"
    echo "容器 IP: $CONTAINER_IP"
else
    echo "模式: 宿主机"
fi

echo
echo "要屏蔽的 IP:"
printf '  %s\n' "${IPS[@]}"

echo
echo "屏蔽时间: ${DURATION} 秒"
echo "========================================"
echo

# -----------------------------
# 清理规则
# -----------------------------

cleanup() {

    echo
    echo "正在恢复网络..."

    if [ -n "$DOCKER_CONTAINER" ]; then

        for IP in "${IPS[@]}"; do
            iptables -D DOCKER-USER \
                -s "$CONTAINER_IP" \
                -d "$IP" \
                -p tcp \
                -j DROP 2>/dev/null
        done

    else

        for IP in "${IPS[@]}"; do
            iptables -D OUTPUT \
                -d "$IP" \
                -p tcp \
                -j DROP 2>/dev/null
        done

    fi

    echo "网络已恢复"
}

trap cleanup EXIT INT TERM

# -----------------------------
# 添加规则
# -----------------------------

if [ -n "$DOCKER_CONTAINER" ]; then

    for IP in "${IPS[@]}"; do

        echo "屏蔽 Docker [$DOCKER_CONTAINER] → $IP"

        iptables -I DOCKER-USER \
            -s "$CONTAINER_IP" \
            -d "$IP" \
            -p tcp \
            -j DROP

    done

else

    for IP in "${IPS[@]}"; do

        echo "屏蔽本机 → $IP"

        iptables -I OUTPUT \
            -d "$IP" \
            -p tcp \
            -j DROP

    done

fi

echo
echo "开始屏蔽 ${DURATION} 秒..."

sleep "$DURATION"

echo
echo "屏蔽时间结束"
