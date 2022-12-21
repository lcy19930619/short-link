#!/bin/bash

APP_NAME='short-link'
# 等待应用启动的时间
APP_START_TIMEOUT=80
# 应用端口
APP_PORT=10000
# 应用健康检查URL
HEALTH_CHECK_URL='http://127.0.0.1:'${APP_PORT}/getId

# jar包的名字
JAR_NAME='short-link-1.0.0.20221221.jar'
# 探针
JAVA_AGENT=' -javaagent:/usr/local/agent/skywalking-agent.jar -Dskywalking.agent.service_name='${APP_NAME}''
# 启动参数
JAVA_OPTION=' -server -Djava.security.egd=file:/dev/./urandom -Xmx512m -Xms128m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=384m -Dserver.port='${APP_PORT}


health_check() {
    exptime=0
    echo "checking ${HEALTH_CHECK_URL}"
    while true
        do
            # shellcheck disable=SC2006
            # shellcheck disable=SC1083
            status_code=`/usr/bin/curl -L -o /dev/null --connect-timeout 5 -s -w %{http_code}  ${HEALTH_CHECK_URL}`
            if [ "$?" != "0" ]; then
               echo -n -e "\rapplication not started"
            else
                echo "code is $status_code"
                if [ "$status_code" == "200" ];then
                    break
                fi
            fi
            sleep 1
            ((exptime++))

            echo -e "\rWait app to pass health check: $exptime..."

            if [ "$exptime" -gt ${APP_START_TIMEOUT} ]; then
                echo 'app start failed'
               exit 1
            fi
        done
    echo "check ${HEALTH_CHECK_URL} success"
}
start_application() {
    echo "starting java process"
    nohup java ${JAVA_AGENT}  ${JAVA_OPTION} -jar ${JAR_NAME} > ./logs/start.log 2>&1 &
    echo "started java process"
}

stop_application() {
   # shellcheck disable=SC2006
   checkjavapid=`ps -ef | grep java | grep ${APP_NAME} | grep -v grep |grep -v gateway|grep ${APP_PORT}| awk '{print$2}'`

   if [[ ! $checkjavapid ]];then
      echo -e "\r 没有发现需要停止的进程"
      return
   fi

   kill $checkjavapid
   echo '开始停止进程 kill '$checkjavapid

   times=60
   for e in $(seq 60)
   do
        sleep 1
        COSTTIME=$(($times - $e ))
        checkjavapid=`ps -ef | grep java | grep ${APP_NAME} | grep -v grep |grep -v gateway |grep ${APP_PORT}| awk '{print$2}'`
        echo -e  "\r  等待 java 进程停止， 等待时间 `expr $COSTTIME` 秒."
        if [[ ! $checkjavapid ]];then
           return;
        fi
   done
   kill -9 $checkjavapid
   echo 'execute kill -9 '$checkjavapid
   echo ""
}

#source /etc/profile
mkdir -p ./logs
#echo '刷新环境变量'

echo '准备停止进程'
stop_application
echo '停止进程完毕'

echo '开始启动进程'
start_application
echo '启动进程完毕'

echo '开始启动进程心跳检查'
health_check
echo '心跳检查完毕'

echo '程序启动结束'
