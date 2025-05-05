### 좋아요 DB, Table 설정

### Database 생성

1. MySQL 컨테이너로 접속
```shell
$ docker exec -it kuke-board-mysql bash
```

2. MySQL 접속
```shell
bash-5.1# mysql -u root -p
Enter passwerd:
```
- MySQL 컨테이너 실행 시 설정한 password 입력

3. Database 생성
```shell
# Database 생성 
create datagbase article_like;
# Database 사용
use article_like;
```

4. Table 생성
```shell
# article 테이블 생성
mysql> create table article_like (
    -> article_like_id bigint not null primary key,
    -> article_id bigint not null,
    -> user_id bigint not null,
    -> created_at datetime not null
    -> );
```

```shell
# 유니크 인덱스 생성
mysql> create unique index idx_article_id_user_id on article_like(article_id asc, user_id asc);
```
- script는 `service/like/src/main/resources/db/rdb-schema.sql`에 위치