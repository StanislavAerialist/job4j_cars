create table auto_post
(
    id           serial primary key,
    description  varchar                         not null,
    created      timestamp                       not null,
    auto_user_id int references auto_user (id)   not null,
    sold         boolean                         not null,
    price        int                             not null,
    car_id       int  unique REFERENCES car(id)  not null,
    file_id      int REFERENCES file (id)
);