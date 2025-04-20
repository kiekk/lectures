create table account
(
    age      integer not null,
    id       bigint  not null,
    password varchar(255),
    roles    varchar(255),
    username varchar(255),
    primary key (id)
)