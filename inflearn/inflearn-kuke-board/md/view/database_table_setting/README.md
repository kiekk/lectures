### 조회수 DB, Table 설정

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
create datagbase article_view;
# Database 사용
use article_view;
```

4. Table 생성
```shell
# article_view 테이블 생성
mysql> create table article_view_count (
    -> article_id bigint not null primary key,
    -> view_count bigint not null
    -> );

```
- script는 `service/view/src/main/resources/db/rdb-schema.sql`에 위치