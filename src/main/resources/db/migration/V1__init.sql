-- Создаем таблицу с пользователями
CREATE TABLE users(
        id                  bigserial primary key,
        name                varchar(255) not null, -- имя пользователя
        password            varchar(255) not null, -- пароль
        email               varchar(255) unique, -- почта должна быть уникальной
        created_at          timestamp default current_timestamp, -- дата создания
        updated_at          timestamp default current_timestamp -- дата изменения
);

-- Наполняем таблицу пользователей, пароль взят один для всех для упрощения и зашифрован через bcCrypt
insert into users (name, password, email) values
('Petya', '$2a$12$dtrkYXJhILb2.9776faS3u6mDcZ8yqzrtROtxuTGngEWQr8hdeL2.', 'petya@gmail.com'), --100
('Vasya', '$2a$12$dtrkYXJhILb2.9776faS3u6mDcZ8yqzrtROtxuTGngEWQr8hdeL2.', 'vasya@gmail.com'); --100

-- создаем таблицу ролей пользователя
create table roles(
        id                  bigserial primary key,
        title               varchar(100) not null -- название роли
);

--Наполняем таблицу ролей пользователя двумя ролями
insert into roles (title) values
('ROLE_ADMIN'), ('ROLE_USER');

-- создаем связующую таблицу ролей и пользователей
create table users_roles(
        user_id             bigint not null references users(id), -- ссылка по foreign key
        role_id             bigint not null references roles(id),-- ссылка по foreign key
        primary key (user_id, role_id) -- уникальный ключ состоит из двух полей
);

--наполняем связующую таблицу ролей и пользователей
insert into users_roles(user_id, role_id) values
(1, 1), (1, 2), (2, 1);

create table messages(
        id                  bigserial primary key,
        user_id             bigint references users(id),
        description         varchar(1000),
        created_at          timestamp default current_timestamp -- дата создания
);

insert into messages (user_id, description, created_at) values
(1, 'message 1', '2022-08-02 15:02:42'),
(1, 'message 2', '2022-08-02 15:02:42'),
(1, 'message 3', '2022-08-02 15:02:42'),
(1, 'message 4', '2022-08-02 15:02:42'),
(1, 'message 5', '2022-08-02 15:02:42'),
(1, 'message 6', '2022-08-02 15:02:42'),
(1, 'message 7', '2022-08-02 15:02:42'),
(1, 'message 8', '2022-08-02 15:02:42'),
(1, 'message 9', '2022-08-02 15:02:42'),
(1, 'message 10', '2022-08-02 15:02:42'),
(1, 'message 11', '2022-08-02 15:02:42'),
(1, 'message 12', '2022-08-02 15:02:42'),
(1, 'message 13', '2022-08-02 15:02:42'),
(1, 'message 14', '2022-08-02 15:02:42'),
(1, 'message 15', '2022-08-02 15:02:42'),
(2, 'message 1', '2022-08-02 15:02:42'),
(2, 'message 2', '2022-08-02 15:02:42'),
(2, 'message 3', '2022-08-02 15:02:42'),
(2, 'message 4', '2022-08-02 15:02:42'),
(2, 'message 5', '2022-08-02 15:02:42'),
(2, 'message 6', '2022-08-02 15:02:42'),
(2, 'message 7', '2022-08-02 15:02:42'),
(2, 'message 8', '2022-08-02 15:02:42'),
(2, 'message 9', '2022-08-02 15:02:42'),
(2, 'message 10', '2022-08-02 15:02:42'),
(2, 'message 11', '2022-08-02 15:02:42'),
(2, 'message 12', '2022-08-02 15:02:42'),
(2, 'message 13', '2022-08-02 15:02:42'),
(2, 'message 14', '2022-08-02 15:02:42'),
(2, 'message 15', '2022-08-02 15:02:42');