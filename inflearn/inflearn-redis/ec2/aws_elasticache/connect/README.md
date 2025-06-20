## Spring Boot에 ElastiCache 연결하기

✅ application.yml 파일 수정하기
```yaml
---
spring:
  config:
    activate:
      on-profile: elasticache
  data:
    redis:
      host: ${elasticacheAddress}
      port: 6379
```

- jar 실행 시 `-DelasticacheAddress` 값을 주입해야 한다.

✅ 기존 서버 종료시키기
- Docker 환경이 아니라 jar를 직접 실행시킬 것이므로 이전에 사용중이던 Docker 컨테이너를 종료시킨다.

```shell
$ docker compose down # 이전 실습에서 실행시켰던 컨테이너 종료시키기
$ docker ps # 종료됐는 지 확인
```

✅ Spring Boot 프로젝트 실행시키기

```shell
$ ./gradlew clean build -x test 
$ cd build/libs

$ java -jar -Dspring.profiles.active="prod, elasticache" \
-DrdsAddress={RDS 경로} \
-DelasticacheAddress={ElastiCache 경로} \
{생성된 JAR 파일명}
```
