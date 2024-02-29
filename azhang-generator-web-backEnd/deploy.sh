#!/bin/bash
## set -e

## 第一步：删除可能启动的老 generator-web-backend 容器
echo "开始删除 generator-web-backend 容器"
docker stop generator-web-backend || true
docker rm generator-web-backend || true
echo "完成删除 generator-web-backend 容器"

## 第二步：启动新的 generator-web-backend 容器 \
echo "开始启动 generator-web-backend 容器"
docker run -d \
--name generator-web-backend \
-e "SPRING_PROFILES_ACTIVE=prod" \
-v /docker_projects/generator-web-backEnd/log:/root/logs/ \
-v /docker_projects/generator-web-backEnd/.temp:/root/.temp \
-p 28080:28080 \
generator-web-backend

echo "正在启动 generator-web-backend 容器中，需要等待 30 秒左右"