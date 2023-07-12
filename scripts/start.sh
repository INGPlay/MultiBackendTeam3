#!/bin/bash

ROOT_PATH="/home/ubuntu/MultiBackendTeam3"
JAR="$ROOT_PATH/application.jar"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

NOW=$(date +%c)

echo "[$NOW] $JAR 복사" >> $START_LOG
cp $ROOT_PATH/build/libs/springboot-1.0.0-SNAPSHOT.war $JAR

echo "[$NOW] > $JAR 실행" >> $START_LOG
nohup java -jar $JAR --spring.profiles.active=server > $APP_LOG 2> $ERROR_LOG &

SERVICE_PID=$(pgrep -f $JAR)
echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> $START_LOG