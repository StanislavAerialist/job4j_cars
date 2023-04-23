create table owner
(
    id                               serial primary key,
    name  varchar                              not null,
    auto_user_id int references auto_user (id) not null
);