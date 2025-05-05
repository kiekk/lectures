### 비관적 락 (Pessimistic Lock)

- 비관적 락은 데이터 접근 시에 항상 충돌이 발생할 가능성이 있다고 가정한다.
- 데이터를 보호하기 위해 항상 락을 걸어 다른 트랜잭션의 접근을 방지한다.
  - 다른 트랜잭션은 락이 해제되기까지 대기
  - 락을 오래 점유하고 있으면, 성능 저하 또는 deadlock 등으로 인한 장애 문제가 발생

### 구현 방법

- [case 1]
```sql
transaction start;

insert into article_like values({article_like_id}, {article_id}, {user_id}, {created_at});

update article_like_count set like_count = like_count + 1 where article_id = {article_id};

commit;
```
- update 문을 수행하는 시점에 락을 점유하기 때문에 락 점유 시간이 상대적으로 짧다.

- [case 2]
```sql
transaction start;

insert into article_like values({article_like_id}, {article_id}, {user_id}, {created_at});

select * from article_like_count where article_id = {article_id} for update;

update article_like_count set like_count = {updated_like_count} where article_id = {article_id};

commit;
```

- 데이터 조회 시점부터 락을 점유하기 때문에 락 점유 시간이 상대적으로 길다.
- 데이터를 조회한 뒤 중간 과정을 수행해야 하기 때문에, 락 해제가 지연될 수 있다.


