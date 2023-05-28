create table car
(
    id        serial primary key,
    name      varchar not null,
    engine_id int not null                references engine(id),
    owner_id int not null                  references owner(id),
    brand_id int not null                  references brand(id),
    body_id int not null                    references body(id),
    category_id int not null            references category(id),
    transmission_id int not null    references transmission(id)
);