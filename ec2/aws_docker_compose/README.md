## AWS EC2에서 Docker Compose를 활용해 Redis, Spring Boot 띄워보기

### ✅ Dockerfile 작성
```dockerfile
FROM openjdk:21-jdk

COPY build/libs/*SNAPSHOT.jar app.jar

# 운영 환경에서 ${RDS_ADDRESS}에 RDS 주소로 치환해야 함.
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod-docker", "-DrdsAddress=${RDS_ADDRESS}", "/app.jar"]
```
- Dockerfile 작성 시 경로는 프로젝트 root에 위치해야 합니다.
- AWS Docker 환경용 프로필을 새로 생성합니다. `prod-docker`

### ✅ docker-compose.yml 작성
```yaml
services:
  api-server:
    build:
      context: .
      dockerfile: ./Dockerfile-prod
    ports:
      - 8080:8080
    depends_on:
      cache-server:
        condition: service_healthy
  cache-server:
    image: redis
    ports:
      - 6379:6379
    healthcheck:
      test: [ "CMD", "redis-cli", "ping" ]
      interval: 5s
      retries: 10
volumes:
  mysql-data:
```
- docker-compose.yml 작성 시 경로는 프로젝트 root에 위치해야 합니다.

### ✅ application.yml 추가
```yaml
---
# prod-docker 환경
spring:
  config:
    activate:
      on-profile: prod-docker
  datasource:
    url: jdbc:mysql://${rdsAddress}:3306/mydb
    username: admin
    password: password
  data:
    redis:
      host: cache-server
      port: 6379
```
- docker 프로필 추가

### ✅ EC2 Docker 설치

```shell
$ sudo apt-get update && \
	sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common && \
	curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - && \
	sudo apt-key fingerprint 0EBFCD88 && \
	sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" && \
	sudo apt-get update && \
	sudo apt-get install -y docker-ce && \
	sudo usermod -aG docker ubuntu && \
	newgrp docker && \
	sudo curl -L "https://github.com/docker/compose/releases/download/2.27.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose && \
	sudo chmod +x /usr/local/bin/docker-compose && \
	sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
	
$ docker -v # Docker 버전 확인
$ docker compose version # Docker Compose 버전 확인
```

### ✅ EC2에서 실행중인 Spring Boot, Redis 종료

```shell
# Redis 중지
$ sudo systemctl stop redis
$ sudo systemctl status redis # 잘 종료됐는 지 확인

# Spring Boot 종료
$ sudo lsof -i:8080 # 8080번 포트 실행되고 있는 프로세스 확인
$ kill {Spring Boot의 PID} # 프로세스 종료
$ sudo lsof -i:8080 # 잘 종료됐는 지 확인
```

### ✅ Docker 컨테이너로 실행

```shell
$ ./gradlew clean build -x test
$ docker compose -f docker-compose-prod.yml up --build -d 

$ docker ps # 잘 띄워졌는 지 확인
$ docker compose logs -f # 실시간 로그 확인하기
```
