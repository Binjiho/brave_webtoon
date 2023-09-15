#!/bin/bash
echo ">> deploy Dir 이동"
cd /home/ec2-user/deploy/

EXIST_BLUE=$(docker ps | grep blue)

if [ -z "$EXIST_BLUE" ]; then
        echo ">>[Up] Blue Up"
        docker image tag jiyu1948/brave_webtoon:backend blue:backend
        docker rmi jiyu1948/brave_webtoon:backend
        docker image tag jiyu1948/brave_webtoon:frontend blue:frontend
        docker rmi jiyu1948/brave_webtoon:frontend
        docker-compose -f docker-compose.blue.yaml -p blue up -d
        BEFORE_COMPOSE_COLOR="green"
        AFTER_COMPOSE_COLOR="blue"
        CONTAINER_PORT=9001
else
        echo ">>[Up] Green Up"
        docker image tag jiyu1948/brave_webtoon:backend green:backend
        docker rmi jiyu1948/brave_webtoon:backend
        docker image tag jiyu1948/brave_webtoon:frontend green:frontend
        docker rmi jiyu1948/brave_webtoon:frontend
        docker-compose -f docker-compose.green.yaml -p green up -d
        BEFORE_COMPOSE_COLOR="blue"
        AFTER_COMPOSE_COLOR="green"
        CONTAINER_PORT=9004
fi
sleep 10

echo ">>BEFORE_COMPOSE_COLOR = ${BEFORE_COMPOSE_COLOR}"
echo ">>AFTER_COMPOSE_COLOR = ${AFTER_COMPOSE_COLOR}"

EXIST_AFTER=$(docker ps | grep ${AFTER_COMPOSE_COLOR})

if [ -n "$EXIST_AFTER" ]; then
        echo ">> nginx conf fix AND nginx reload"
        echo "set \$service_url http://127.0.0.1:${CONTAINER_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
        sudo service nginx reload

        echo ">> 이전 컨테이너 종료"
        docker-compose -f docker-compose.${BEFORE_COMPOSE_COLOR}.yaml -p ${BEFORE_COMPOSE_COLOR} down
        echo ">> Remove previous container"
        docker container prune -f
        echo ">> Remove previous image"
        docker image prune -a -f
fi