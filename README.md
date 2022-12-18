# study-lucy-xss-filter

```
참고 자료

youtube : https://www.youtube.com/watch?v=FBTT8JeHcys&t=1s
github : https://github.com/naver/lucy-xss-filter
공식 문서 : http://naver.github.io/lucy-xss-filter/kr/
```

```
XSS Preventer 방식

XssPreventer 객체 사용
특별한 설정 없이 XSS 공격을 방어하고자 할 경우 사용.

HTML 로 인식할 만한 요소를 전부 치환한다.
이 때 apache-common-lang의 StringEscapeUtils 를 내부적으로 사용하고 있다.
(<, >, "", ''...)

String clean = XssPreventer.escape(dirty);

와 같이 호출한다.

장점:
특별한 설정 없이도 XSS 공격을 방어할 수 있다.

단점:
HTML 문자열은 허용하되 특정 패턴을 검사하는 방법을 사용할 수 없다.
```

```
Xss Filter 방식 - 1. lucy-xss-filter

xml 방식을 사용
lucy-xss-superset-sax.xml & lucy-xss-sax.xml

DOM parser 보다 SAX parser 가 성능이 좋기 때문에 SAX parser 방식을 권장한다고 함.

lucy-xss-default.xml: lucy xss filter 에서 기본으로 제공하는 XSS 설정 
lucy-xss-superset-sax.xml: Global XSS 설정
lucy-xss-sax.xml: 세부 XSS 설정

우선 순위
lucy-xss-sax.xml > lucy-xss-superset-sax.xml > lucy-xss-default.xml
상속도 가능하여 각 설정 파일의 설정 정보들을 상속 받을 수 있다.

LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");
String clean = filter.doFilter(String dirty);

와 같이 사용한다.

장점:
xml 만으로 XSS 설정이 가능하다.
서비스 전반적인 XSS 설정과 세부적인 XSS 설정이 가능하다.

단점:
개발자가 추가로 설정을 해야 하기 때문에 라이브러리에 대한 지식이 있어야 한다.
```

```
lucy-xss-superset-sax.xml 의 경우 특별한 설정이 없을 경우 
lucy-xss-filter 에서 제공하는 기본 설정 내용을 적용한다.
```