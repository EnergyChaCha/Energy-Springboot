#!/bin/bash

# .env 파일의 경로를 환경 변수로 설정
# export ENV_FILE_PATH="./../../src/main/resources/.env"
export ENV_FILE_PATH="./.env"

# Docker Compose가 실행 중인지 확인합니다.
if docker compose ps &> /dev/null; then
    # Docker Compose로 생성된 컨테이너들을 중지하고 삭제합니다.
    docker compose down
    echo "Docker Compose로 생성된 컨테이너들이 중지되고 삭제되었습니다."
else
    echo "현재 Docker Compose로 생성된 컨테이너들이 없습니다."
fi


# 도커 네트워크가 존재하는지 확인합니다.
if ! docker network inspect energy-network &> /dev/null; then
    # 도커 네트워크를 생성합니다.
    docker network create energy-network
    echo "energy-network 생성"
else
    echo "energy-network는 이미 존재합니다."
fi

# Docker Compose 실행
docker compose -f docker-compose.yml --env-file $ENV_FILE_PATH up -d


echo "스크립트 끝"