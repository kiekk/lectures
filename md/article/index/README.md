### Index

- 인덱스는 데이터를 빠르게 찾기 위한 방법이다.
- 하지만 인덱스 관리를 위해 부가적인 쓰기 작업과 인덱스 저장 공간이 필요하다.
- 다양한 데이터 특성과 쿼리를 지원하는 다양한 자료구조
  - B+ tree (Balanced Tree)
  - Hash
  - LSM tree
  - R tree
  - Bitmap

- Relational Database에서는 주로 B+ tree를 사용
  - 데이터가 정렬된 상태로 저장된다.
  - 검색, 삽입, 삭제 연산을 로그 시간에 수행 가능
  - 트리 구조에서 leaf node 간 연결되기 때문에 범위 검색에 효율적

- 인덱스를 추가하면 쓰기 시점에 B+ tree 구조의 정렬된 상태의 데이터가 생성된다.
- 이미 인덱스로 지정된 컬럼에 대해 정렬된 상태를 가지고 있기 때문에
  - 조회 시점에 전체 데이터를 정렬하고 필터링할 필요가 없다.
  - 따라서, 조회 쿼리를 빠르게 수행할 수 있다.

- 게시글 인덱스 설정
```sql
create index idx_board_id_article_id on article(board_id asc, article_id desc);
```

- board_id 오름차순 정렬, 그리고 article_id 내림차순 정렬로 인덱스 생성
- 인덱스는 순서가 중요한데 우리는 게시판별로 조회할 것이기 때문에 게시판별로 생성 시간 순으로 정렬된 상태의 인덱스를 생성한다.

- created_at 컬럼을 인덱스로 설정할 경우 분산 환경에서 충돌이날 가능성이 있기 때문에 snowflake id인 article_id를 인덱스로 사용한다.
  - snowflake id를 사용하더라도 충돌 확률이 0은 아니지만, created_at 보다는 충돌 가능성이 현저히 작다.
  - 오름차순이기 때문에 생성 시간에 의해 이미 정렬된 상태를 가지고 있다.

#### 인덱스 동작 확인
- 인덱스가 아닌 created_at 컬럼으로 정렬시 소요 시간
```sql
select * from article
where board_id  = 1
order by created_at desc
limit 30 offset 90;

-- 약 20s 362ms 소요
```

- 인덱스인 article_id 컬럼으로 정렬시 소요 시간
```sql
select * from article
where board_id = 1
order by article_id desc
limit 30 offset 90;

-- 약 285ms 소요
```

#### 인덱스인 article_id로 정렬해도 offset에 따른 소요 시간
```sql

select * from article
where board_id = 1
order by article_id desc
limit 30 offset 1499970;

-- 약 2s 소요
```

- article_id로 정렬해도 offset이 클 경우 소요 시간이 길어질 수 있다.
- 이유는 아까 생성한 (board_id, article_id) 인덱스가 Secondary Index로 동작하는데
  - Secondary Index는 데이터에 접근하기 위한 포인터를 찾은 뒤
  - Clustered Index에서 데이터를 찾기 때문에 인덱스를 총 2번 조회해야 한다.
- 따라서 쿼리를 다음과 같이 수정하면 Clustered Index를 사용하기 때문에 조회 시간이 다시 빨라지는 것을 확인할 수 있다.

```sql
select *
from (select article_id
      from article
      where board_id = 1
      order by article_id desc
      limit 30 offset 1499970) t
         left join article on t.article_id = article.article_id;

-- 약 400ms 소요
```

### MySQL에서 InnoDB는 테이블마다 Clusted Index를 자동 생성한다.
- Primary Key를 기준으로 정렬된 Clustered Index를 생성한다.
- Clustered Index는 leaf node의 값으로 행 데이터를 가진다.
- article 테이블에는 Primary Key인 article_id를 기준으로 하는 Clustered Index가 생성되어 있고, 이에 대한 데이터를 가진다.
- Primary Key를 이용한 조회는, 자동으로 생성된 Clustered Index로 수행되는 것이다.

- 아까 생성한 (board_id, article_id) 인덱스는 Secondary Index로 Non-Clustered Index라고도 한다.
- Secondary Index는 Clustered Index와 달리 leaf node에 실제 데이터가 아닌 데이터에 접근하기 위한 포인터를 가지고 있다.
- 따라서 Secondary Index를 사용하여 데이터를 조회할 경우
  - Secondary Index를 통해 포인터를 찾고
  - Clustered Index를 통해 실제 데이터를 찾는 과정을 거치게 된다.

- 따라서 위와 같은 쿼리로 작성하게 된다.
  - 먼저 Secondary Index를 통해 article_id를 빠르게 찾고
  - 그 다음 Clustered Index를 통해 실제 데이터를 찾는 것이다.
- 이와 같이 인덱스의 데이터만으로 조회를 수행할 수 있는 인덱스를 Covering Index라고 한다.
- 즉, 위 쿼리에서 우리가 만든 (board_id, article_id) 인덱스는 Secondary Index이면서 동시에 Covering Index로 동작했다고 할 수 있다.

#### 하지만 offset이 커질수록 쿼리가 느려지는 문제는 동일하다.
- 이 경우 zero offset 방식인 무한 스크롤 방식이 답이 될 수 있다.