#!/bin/bash

echo "$(date "+%Y-%m-%d %H:%M:%S") get into stop.sh"

. /etc/profile

echo "PATH="$PATH
echo "JAVA_HOME="$JAVA_HOME

cd `dirname $0`
BIN_DIR=`pwd`
echo "pwd: "`pwd`
cd ..
DEPLOY_DIR=`pwd`
echo "pwd: "`pwd`
CONF_DIR=${DEPLOY_DIR}/conf

SERVER_NAME=`sed '/application.name/!d;s/.*=//' conf/application.properties | tr -d '\r'`
LOG_DIR=`sed '/<property name="LOG_HOME">/!d;s/.*">//;s/<.*//' conf/log4j2.xml | tr -d '\r'`
if [ ! -n "$LOG_DIR" ]; then
    LOG_DIR=${DEPLOY_DIR}/log
fi

PID=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep  |awk '{print $2}'`
if [ -z "${PID}" ]; then
    echo "ERROR: The $SERVER_NAME does not started!"
    exit 1
fi

if [ "$1" != "skip" ]; then
    sh ${BIN_DIR}/dump.sh
fi

echo -e "Stopping the $SERVER_NAME ...\c"
kill ${PID} >> ${LOG_DIR}/stdout 2>&1

COUNT=1
while [ $COUNT -gt 0 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep  | awk '{print $2}' | wc -l`
done

echo "OK!"
echo "PID: ${PID}"
