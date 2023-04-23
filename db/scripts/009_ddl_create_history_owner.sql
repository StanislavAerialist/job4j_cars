create table history_owner
(
    id                             serial primary key,
    car_id     int references car (id)       not null,
    owner_id   int references owner (id)    not null,
    startAt    timestamp                     not null,
    endAt      timestamp                     not null
);