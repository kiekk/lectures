# spring-boot-jar-test
Spring Boot 에서 JSP 사용시 jar 패키징이 가능한지 테스트

```
결론부터 말하자면 Spring Boot 에서 JSP 사용시 jar 패키징이 불가능하다.
정확히 말하자면 Spring Boot 1.4.3 버전부터 불가능하다. 
```

```
반대로 말하면 Spring Boot 1.4.2 버전까지는 JSP도 jar 패키징이 가능했다는 소리인데,
그보다 먼저 Spring Boot 에서 JSP 를 사용하는 방법에 대해 알아보자
```

```
Spring Boot 에서 JSP 를 사용하려면 
1. 먼저 jstl, jasper 라이브러리를 별도로 추가해줘야 한다.
2. jsp 파일들을 src/main/webapp/WEB-INF/.. 경로에 저장해야 한다.
3. viewresolver 의 prefix, suffix 설정을 추가해야 한다.

여기서 2번 경로는 IDE 에서 프로젝트를 실행하면 정상적으로 JSP 파일을 읽어온다.
하지만 jar 패키징시에는 위 경로로는 사용할 수 없다.
```

```
jar로 프로젝트를 실행하여 JSP 파일을 읽어오기 위해서는 
jsp 파일의 경로를 수정해야 한다.

src/main/resources/META-INF/resources/WEB-INF/.. 경로에 저장해야 한다.

Spring Boot 는 기본적으로 jar 패키징시 webapp 폴더가 무시된다.
따라서 webapp 하위에 있는 jsp 파일이 jar 에 포함되지 않아 Spring Boot 가 이를 인식할 수 없었던 것인데,
resources/META-INF 하위 경로에 jsp 를 저장하게 되면 jsp 파일이 jar 에 포함되어 
jar 로 프로젝트를 실행해도 이제는 jsp 파일을 읽어 올 수 있게 된다.

하지만 반대로 이 경로는 IDE 에서 실행할 때는 정상적으로 JSP 파일을 읽어 올 수 없다.
따라서 IDE를 위한 경로 src/main/webapp/WEB-INF/.. 와
jar 패키징을 위한 경로 src/main/resources/META-INF/resource/WEB-INF/.. 
경로 모두 jsp 파일을 추가해줘야 한다.
```

```
하지만 이 방법 역시 앞서 설명했듯이 Spring Boot 1.4.2 버전까지만 가능했던 얘기이고,
1.4.3 버전 부터는 jsp 사용시 jar 패키징이 불가능하다.

이와 관련해서 링크와 캡쳐들을 남겨놓겠다.
```

![img_1.png](img_1.png)
![img_2.png](img_2.png)
![img_3.png](img_3.png)

### 관련 링크
https://github.com/spring-projects/spring-boot/issues/13420
https://stackoverflow.com/questions/44689773/why-spring-boot-1-5-3-jar-does-not-recognise-jsp-files-in-src-main-resources-met
https://github.com/spring-projects/spring-boot/commit/9d9acc92e328159063b916083a2988b563869e04
