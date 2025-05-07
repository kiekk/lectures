# Outbox 테이블
create table outbox
(
    outbox_id bigint not null primary key,
    shard_key bigint not null,
    event_type varchar(100) not null,
    payload varchar(5000) not null,
    created_at datetime not null
);

# 인덱스
create index idx_shard_key_created_at on outbox(shard_key asc, created_at asc);
