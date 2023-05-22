create table car
(
    id        serial primary key,
    name      varchar not null,
    engine_id int not null references engine(id),
    owner_id int not null unique REFERENCES owner(id),
    brand_id int not null unique REFERENCES brand(id),
    body_id int not null unique REFERENCES body(id),
    category_id int not null unique REFERENCES category(id),
    transmission_id int not null unique REFERENCES transmission(id)
);