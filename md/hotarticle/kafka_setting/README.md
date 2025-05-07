### kafka 개발 환경 세팅

1. docker-compose.yml 작성
```yaml
  kuke-board-kafka:
    image: apache/kafka:3.8.0
    container_name: kuke-board-kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: broker,controller
      KAFKA_CONTROLLER_QUORUM_VOTERS: 1@localhost:9093
      KAFKA_CONTROLLER_LISTENER_NAMES: CONTROLLER

      # 👇 반드시 CONTROLLER 리스너를 포함해야 함
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,CONTROLLER:PLAINTEXT

      KAFKA_LOG_DIRS: /tmp/kraft-combined-logs
```

2. docker container 접속
```shell
$ docker exec -it kuke-board-kafka bash
```

3. kafka topic 생성
```shell
$ cd /opt/kafka/bin/

$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic soono-board-article --replication-factor 1 --partitions 3
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic soono-board-comment --replication-factor 1 --partitions 3
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic soono-board-like --replication-factor 1 --partitions 3
$ ./kafka-topics.sh --bootstrap-server localhost:9092 --create --topic soono-board-view --replication-factor 1 --partitions 3
```

4. kafka topic 확인
```shell
$ cd /opt/kafka/bin/

$ ./kafka-topics.sh --list --bootstrap-server localhost:9092
```
