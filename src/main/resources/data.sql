-- 테스트 계정
insert into user_account
(user_id, user_password, role_types, nickname, email, memo, created_at, created_by, modified_at, modified_by)
values ('soono', 'asdf1234', 'ADMIN', 'soono', 'soono@mail.com', 'I am soono.', now(), 'soono', now(), 'soono'),
       ('mark', '{asdf1234', 'MANAGER', 'Mark', 'mark@mail.com', 'I am Mark.', now(), 'soono', now(), 'soono'),
       ('susan', 'asdf1234', 'MANAGER,DEVELOPER', 'Susan', 'Susan@mail.com', 'I am Susan.', now(), 'soono', now(),
        'soono'),
       ('jim', 'asdf1234', 'USER', 'Jim', 'jim@mail.com', 'I am Jim.', now(), 'soono', now(), 'soono')
;