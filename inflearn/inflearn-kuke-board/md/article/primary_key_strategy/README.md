### Primary Key 생성 전략
- Primary Key 생성 전략에는 다음과 같이 4가지 방법을 고려해볼 수 있다.

1. DB Auto-Increment
- 분산 데이터베이스 환경에서 PK가 중복될 수 있기 때문에, 식별자의 유일성이 보장되지 않는다.
  - 여러 샤드에서 동일한 PK를 가지는 상황
- 클라이언트 측에 노출하면 보안 문제
  - 데이터의 개수 또는 특정 시점의 식별자 예측 가능
- 간단하기 때문에 다음 상황에서는 유리할 수 있다.
  - 보안적인 문제를 크게 고려하지 않는 상황
  - 단일 DB를 사용하거나 애플리케이션에서 PK의 중복을 직접 구분하는 상황
- Auto-Increment를 사용하면서 보안 문제도 고려한다면
  - PK는 DB내에서 식별자로만 사용하고 애플리케이션의 식별자를 위해 별도 유니크 인덱스를 사용할 수도 있다.
    - PK = id(DB auto_increment)
    - unique index = article_id (UUID 등...)
    - client는 article_id만 식별자로서 노출 및 사용
    - 다만 unique index를 사용한다면 PK를 사용하는 것보다 조회 성능이 떨어질 수 있다.

2. 유니크 문자열 or 숫자
- UUID 또는 난수를 생성하여 PK를 지정할 수 있다.
  - 정렬 데이터가 아니라 랜덤 데이터를 삽입하는 것
  - 대신 키 생성 방식이 간단하다.
- 하지만 랜덤 데이터로 인해 성능 정하가 발생할 수 있다.
  - Clustered Index는 정렬된 상태를 유지한다.
  - 데이터 삽입이 필요한 인덱스 페이지가 가득찼다면, B+ tree 재구성 및 페이지 분할로 인해 디스크 I/O가 증가한다.
  - PK를 이용한 범위 조회가 필요하다면, 디스크 랜덤 I/O가 발생하기 때문에, 순차 I/O보다 성능이 저하된다.

3. 유니크 정렬 문자열
- 분산 환경에 대한 PK 중복 문제, 보안 문제, 랜덤 데이터에 의한 성능 문제가 해결된다.
- UUID v7, ULID 등의 알고리즘을 사용한다.
  - 일반적으로 알려진 알고리즘은 128비트를 사용한다.
- 데이터 크기에 따라 공간 및 성능 효율이 달라진다.
  - PK가 크면 클수록 데이터는 더 많은 공간을 차지, 비교 연산에 의한 정렬/조회에 더 많은 비용이 소모된다.

4. 유니크 정렬 숫자
- 분산 환경에 대한 PK 중복 문제, 보안 문제, 랜덤 데이터에 의한 성능 문제가 해결된다.
- Snowflake, TSID 등의 알고리즘을 사용한다.
  - 64비트를 사용한다. (BIGINT)
  - 정렬을 위해 타임스탬프를 나타내는 비트 수의 제한으로
    - 키 생성을 위한 시간적인 한계가 있을 수 있다.
    - 문자열 알고리즘에서도 동일한 문제가 있으나 비트 수가 많을수록 제한이 덜할 수 있다.
- `유니크 정렬 문자열`보다 적은 공간을 사용한다. (128비트 > 64비트)


## 📂 추가로 학습할만한 자료
- [Non-sequential vs Sequential PK - Page Split (MySQL) - 김혜주 | F-Lab 미니 컨퍼런스](https://www.youtube.com/watch?v=_uc_DgEJEeQ)
- UUID vs ULID
  - [UUID vs ULID](https://velog.io/@injoon2019/UUID-vs-ULID)
  - [ULID](https://junuuu.tistory.com/823)
  - [ULID Creator](https://github.com/f4b6a3/ulid-creator#monotonic-ulid)
- [TSID](https://www.foxhound.systems/blog/time-sorted-unique-identifiers/)
- [TSID Creator](https://github.com/f4b6a3/ulid-creator#monotonic-ulid)