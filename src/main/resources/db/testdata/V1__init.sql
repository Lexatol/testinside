-- ТАБЛИЦЫ ДЛЯ ТЕСТОВ

CREATE TABLE users(
        id                  bigserial primary key,
        name                varchar(255) not null, -- имя пользователя
        password            varchar(255) not null, -- пароль
        email               varchar(255) unique, -- почта должна быть уникальной
        created_at          timestamp default current_timestamp, -- дата создания
        updated_at          timestamp default current_timestamp -- дата изменения
);

insert into users (name, password, email) values
('Petya', '$2a$12$dtrkYXJhILb2.9776faS3u6mDcZ8yqzrtROtxuTGngEWQr8hdeL2.', 'petya@gmail.com'), --100
('Vasya', '$2a$12$dtrkYXJhILb2.9776faS3u6mDcZ8yqzrtROtxuTGngEWQr8hdeL2.', 'vasya@gmail.com'); --100

create table roles(
        id                  bigserial primary key,
        title               varchar(100) not null -- название роли
);

insert into roles (title) values
('ROLE_ADMIN'), ('ROLE_USER');

create table users_roles(
        user_id             bigint not null references users(id), -- ссылка по foreign key
        role_id             bigint not null references roles(id),-- ссылка по foreign key
        primary key (user_id, role_id) -- уникальный ключ состоит из двух полей
);

insert into users_roles(user_id, role_id) values
(1, 1), (1, 2), (2, 1);

create table messages(
        id                  bigserial primary key,
        user_id             bigint references users(id),
        description         varchar(1000),
        created_at          timestamp default current_timestamp -- дата создания
);

insert into messages (user_id, description, created_at) values
(1, 'message 1', '2020-08-02 15:02:42'),
(1, 'message 2', '2021-08-02 15:12:42'),
(1, 'message 3', '2021-08-02 15:22:42'),
(1, 'message 4', '2022-08-02 15:32:42'),
(1, 'message 5', '2022-08-02 15:42:42'),
(2, 'message 1', '2020-10-02 11:02:42'),
(2, 'message 2', '2021-10-02 11:12:42'),
(2, 'message 3', '2021-10-02 11:22:42'),
(2, 'message 4', '2022-10-02 11:32:42'),
(2, 'message 5', '2022-10-02 11:42:42');
