ALTER TABLE car ADD COLUMN brand_id int not null unique REFERENCES brand(id);
ALTER TABLE car ADD COLUMN body_id int not null unique REFERENCES body(id);
ALTER TABLE car ADD COLUMN category_id int not null unique REFERENCES category(id);
ALTER TABLE car ADD COLUMN transmission_id int not null unique REFERENCES transmission(id);