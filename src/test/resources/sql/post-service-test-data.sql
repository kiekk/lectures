insert into users (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values (1, 'shyoon991@gmail.com', 'soono', 'Seoul', 'aaaa-aaa-aaa-aaaaa', 'ACTIVE', null),
       (2, 'shyoon992@gmail.com', 'soono2', 'Seoul', 'aaaa-aaa-aaa-aaaaab', 'PENDING', null);

insert into posts (`id`, `content`, `created_at`, `modified_at`, `user_id`)
values (1, 'helloworld', 1678358673958, 1678358673958, 1);
