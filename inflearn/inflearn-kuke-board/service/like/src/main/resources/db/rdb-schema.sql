# 좋아요 테이블 생성
create table article_like
(
    article_like_id bigint   not null primary key,
    article_id      bigint   not null,
    user_id         bigint   not null,
    created_at      datetime not null
);

# 유니크 인덱스 생성
create unique index idx_article_id_user_id on article_like (article_id asc, user_id asc);

# 좋아요 수 테이블 생성
create table article_like_count (
    article_id bigint not null primary key,
    like_count bigint not null,
    version bigint not null
);