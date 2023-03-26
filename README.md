# study-spring-boot-dev-tools

### Auto Restart
```
코드를 변경하고 재시작하지 않고 build를 할 경우
spring boot 에서 이를 감지하고 자동 재시작을 수행합니다.
이때 자동 재시작은 우리가 직접 재시작하는 것 보다 빠릅니다.
INFO 13388 --- [   File Watcher] rtingClassPathChangeChangedEventListener : Restarting due to 1 class path change (0 additions, 0 deletions, 1 modification)

build를 했지만 소스 코드의 변경이 없을 경우에는 자동 재시작을 하지 않습니다.
```