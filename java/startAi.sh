#!/bin/sh
JAR_PRE="ai-v"
RUN_PID=$(ps aux | grep $JAR_PRE | grep -v grep | head -n1 | awk '{print $2}')
JAR_HOME="/home/user/ai_intelligent/java"
NEW_JAR_NAME=$(ls -t | grep $JAR_PRE | head -n1 | awk '{print $0}')
JAR_DIR=$JAR_HOME/$NEW_JAR_NAME
if [ -f "ai.pid" ]; then
  PID=$(cat ai.pid)
  echo "pid in ai.pid:($PID) and run pid:($RUN_PID)"
  if [ -z $PID ]; then
    PID=$RUN_PID
  fi
  kill -9 $PID
  echo "kill pid:"$PID
fi
echo "starting server:"$NEW_JAR_NAME
cd $JAR_HOME
nohup java -jar $JAR_DIR --spring.profiles.active=beijing -Duser.timezone=GMT+08 >/dev/null &
sleep 3
ps -ef | grep $JAR_PRE | grep -v grep | awk '{print $2}' >ai.pid
echo "success……"
exit 0
