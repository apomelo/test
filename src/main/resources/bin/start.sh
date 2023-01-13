#!/bin/bash

echo "$(date "+%Y-%m-%d %H:%M:%S") get into start.sh"

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
if [ ! -d "$LOG_DIR" ]; then
    echo "$LOG_DIR is not exist"
    mkdir "$LOG_DIR"
    echo "create dir: $LOG_DIR"
fi
# 一或多个jar包时用下面方式运行
MAIN_CLASS=`sed '/main.class/!d;s/.*=//' conf/application.properties | tr -d '\r'`
# 只有一个jar包时可以用下面方式运行，但不推荐
# JAR_OPTION='-jar test.jar'

PID=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep |awk '{print $2}'`
if [ -n "$PID" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PID"
    exit 1
fi

LIB_DIR=${DEPLOY_DIR}/lib
LIB_JARS=`ls ${LIB_DIR}|grep .jar|awk '{print "'${LIB_DIR}'/"$0}'|tr "\n" ":"`


JAVA_OPTS="-server -Dfile.encoding=utf-8 -Duser.timezone=GMT+08 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:${LOG_DIR}/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:ErrorFile=${LOG_DIR}/java_error_%p.log -XX:HeapDumpPath=${LOG_DIR}/java_error.hprof "

JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
    # JDK 1.3.x or earlier
#    JAVA_DEBUG_OPTS="-Xnoagent -Djava.compiler=NONE -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000 "
    # JDK 1.4.x
#    JAVA_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
    # JDK 5 - 8
    JAVA_DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 "
    # JDK 9 or later
#    JAVA_DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000 "
fi

JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
    JAVA_JMX_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=5010 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false "
fi

JAVA_MEM_OPTS="-Xmx256m -Xms256m -Xmn128m -Xss256k -XX:SurvivorRatio=8 "

export LD_LIBRARY_PATH=${DEPLOY_DIR}/lib
echo -e "Starting the $SERVER_NAME ...\c"
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$LIB_JARS $MAIN_CLASS 1>$LOG_DIR/stdout  2>&1 &

COUNT=0
NUM=0
while [[ $COUNT -lt 1 && $NUM < 10 ]]; do    
    echo -e ".\c"
    sleep 1 
    COUNT=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep  | awk '{print $2}' | wc -l`
	let "NUM++"
done

if [ $COUNT -lt 1 ]; then
	echo "start $MAIN_CLASS failed!!!"
	exit 1
fi 

echo "OK!"
PID=`ps auxf | grep java | grep "$CONF_DIR" | grep -v grep  | awk '{print $2}'`
echo "PID: $PID"
