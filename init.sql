create table if not exists "users"
(
    id       uuid DEFAULT gen_random_uuid() primary key,
    username varchar(60)  not null,
    password varchar(60)  not null,
    email    varchar(60)  not null,
    roles    varchar(100) not null
);

create table if not exists "refresh_tokens"
(
    id         uuid DEFAULT gen_random_uuid() primary key,
    user_id    uuid      not null,
    token      uuid      not null,
    expiryDate timestamp not null,
    foreign key (user_id) references users (id)
);

insert into users (username, password, email, roles)
values ('filip1', 'pwd', 'filip1@gmail.com', 'user;admin');

insert into users (username, password, email, roles)
values ('john1', 'pwd', 'john@gmail.com', 'user');