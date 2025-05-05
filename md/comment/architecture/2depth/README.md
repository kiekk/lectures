### 댓글 최대 2 depth


### 테이블 설계

```shell
mysql> create table comment (
    -> comment_id bigint not null primary key,
    -> content varchar(3000) not null,
    -> article_id bigint not null,
    -> parent_comment_id bigint not null,
    -> writer_id bigint not null,    
    -> deleted bool not null,    
    -> created_at datetime not null
    -> );
```

- 계층 관게를 알아야하므로, 상위 댓글의 참조 키가 필요하다
- 각 댓글은 상위 댓글 ID(parent_comment_id)를 가진다.
- 이 때 Primary Key는 Snowflake ID를 사용한다.
- 각 게시글에 작성된 댓글 목록을 조회하는 것을 가정하여 article_id를 기준으로 샤딩한다.
    - 각 게시글에 작성된 댓글을 단일 샤드에서 조회하기 위함

#### Index 설정

- 게시글 -> 상위 댓글 -> 댓글로 조회하기 때문에 인덱스는 다음과 같이 생성한다.

```shell
mysql> create index idx_article_id_parent_comment_id_comment_id on comment (
    -> article_id asc,
    -> parent_comment_id asc,
    -> comment_id asc
    -> );
```

#### 쿼리 조건에 따른 Index 설정 적용 여부

- Index를 위와 같이 설정했을 때 다음과 같은 쿼리 조건일 경우 인덱스 적용/미적용으로 인해 쿼리 성능이 차이가 날 수 있다.

- [case1]
```shell
explain analyze
select comment.comment_id,
       comment.parent_comment_id,
       comment.content,
       comment.article_id,
       comment.writer_id,
       comment.content,
       comment.is_deleted,
       comment.created_at
from comment
where article_id = 1
 -- Tuple Comparison 조건
  and (parent_comment_id, comment_id) > (142539921307124354, 142539921307124350)
order by parent_comment_id, comment_id
limit 30;

--
-> Limit: 30 row(s)  (cost=542979 rows=30) (actual time=8620..8620 rows=30 loops=1)
    -> Filter: ((`comment`.comment_id,`comment`.parent_comment_id) > (142539921307124354,142539921307124350))  (cost=542979 rows=4.01e+6) (actual time=8620..8620 rows=30 loops=1)
        -> Index lookup on comment using idx_article_id_parent_comment_id_comment_id (article_id=1)  (cost=542979 rows=4.01e+6) (actual time=1.83..8251 rows=8e+6 loops=1)
```

- Tuple Comparison 조건을 사용할 경우 Index Full Scan이 발생하여 쿼리 성능 저하
    - (a, b) > (x, Y)

- [case2]
```shell
explain analyze
select comment.comment_id,
       comment.parent_comment_id,
       comment.content,
       comment.article_id,
       comment.writer_id,
       comment.content,
       comment.is_deleted,
       comment.created_at
from comment
where article_id = 1
-- Tuple Comparison를 명시적 조건으로 분리
  and (
    parent_comment_id > 142539921307124354
        or
    (parent_comment_id = 142539921307124354 and comment_id > 142539921307124350)
    )
order by parent_comment_id, comment_id
limit 30;

--
-> Limit: 30 row(s)  (cost=416 rows=30) (actual time=0.252..0.727 rows=30 loops=1)
    -> Index range scan on comment using idx_article_id_parent_comment_id_comment_id over (article_id = 1 AND parent_comment_id = 142539921307124354 AND 142539921307124350 < comment_id) OR (article_id = 1 AND 142539921307124354 < parent_comment_id), with index condition: ((`comment`.article_id = 1) and ((`comment`.parent_comment_id > 142539921307124354) or ((`comment`.parent_comment_id = 142539921307124354) and (`comment`.comment_id > 142539921307124350))))  (cost=416 rows=358) (actual time=0.232..0.705 rows=30 loops=1)
```

- Tuple Comparison 조건을 명시적 조건으로 분리할 경우 Index Range Scan이 발생하여 쿼리 성능 향상
    - (a > x) or (a = x and b > y)

- 참고 링크
    - [link](https://www.inflearn.com/community/questions/1505514/%EB%8C%93%EA%B8%80-%EB%AA%A9%EB%A1%9D-%EC%A1%B0%ED%9A%8C-%ED%8A%9C%ED%94%8C-%EB%B9%84%EA%B5%90-%EC%8B%9C-%EC%BF%BC%EB%A6%AC-%EC%84%B1%EB%8A%A5-%EC%A0%80%ED%95%98)
    - [link](https://bugs.mysql.com/bug.php?id=111952)