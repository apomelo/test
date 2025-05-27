#!/bin/bash

echo "$(date "+%Y-%m-%d %H:%M:%S") get into deploy.sh"

. /etc/profile

if [ $# -ne 2 ];then
    echo -e "Must input the parameters:
        1: deploy dir
        2: run mode (default or debug)"
    exit -1
fi

echo "pwd: "`pwd`
cd `dirname $0`
echo "pwd: "`pwd`

DEPLOY_DIR=$1
RUN_MODE=$2

# 如果部署目录不存在创建部署目录
if [ ! -d "$DEPLOY_DIR" ]; then
    echo "$DEPLOY_DIR is not exist"
    mkdir -p "$DEPLOY_DIR"
    echo "create dir: $DEPLOY_DIR"
fi

# 如果lib目录存在则尝试停止程序
if [ -d "$DEPLOY_DIR/lib" ]; then
    echo "$DEPLOY_DIR/lib is exist, try to stop the application"
    bash "$DEPLOY_DIR/bin/stop.sh"
    # 备份程序
    tar -czvf "/data/bak/game/game_`date +%Y%m%d_%H%M%S`_bak.tar.gz" "$DEPLOY_DIR"
fi

cd ..
# 复制程序到部署目录
cp -rf ./* $DEPLOY_DIR

# 启动程序
if [ "$RUN_MODE" = "debug" ]; then
    bash $DEPLOY_DIR/bin/start.sh debug
else
    bash $DEPLOY_DIR/bin/start.sh
fi