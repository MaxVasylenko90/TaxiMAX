CREATE TABLE cars(
    id UUID PRIMARY KEY,
    car_number varchar(10) NOT NULL,
    brand varchar(20) NOT NULL,
    model varchar(20) NOT NULL,
    color varchar(10) NOT NULL,
    available BOOLEAN,
    driver_id UUID
);

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO cars (id, car_number, brand, model, color,available)
VALUES (gen_random_uuid(),'FY1234ZX','BMW','X6','BLACK',TRUE);

INSERT INTO cars (id, car_number, brand, model, color,available)
VALUES (gen_random_uuid(),'AS3544RF','Honda','Accord','RED', TRUE);

INSERT INTO cars (id, car_number, brand, model, color,available)
VALUES (gen_random_uuid(),'FX9876AA','Hyundai','Tucson','WHITE', TRUE);