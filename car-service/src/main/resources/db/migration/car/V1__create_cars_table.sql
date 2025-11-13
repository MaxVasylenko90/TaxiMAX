CREATE TABLE cars
(
    id         UUID PRIMARY KEY,
    car_number varchar(10) NOT NULL,
    brand      varchar(20) NOT NULL,
    model      varchar(20) NOT NULL,
    color      varchar(10) NOT NULL,
    available  BOOLEAN,
    driver_id  UUID,
    rent_price NUMERIC(10, 2)
        CONSTRAINT check_price_nonnegative CHECK (rent_price >= 0)
);

CREATE TABLE car_rental_history
(
    id        UUID PRIMARY KEY,
    car_id    UUID    NOT NULL,
    driver_id UUID    NOT NULL,
    status    VARCHAR NOT NULL,
    date      TIMESTAMP
);

CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

INSERT INTO cars (id, car_number, brand, model, color, available, rent_price, driver_id)
VALUES ('a45e7d8f-624f-4521-9f8f-868bd5b4f000', 'FY1234ZX', 'BMW', 'X6', 'BLACK', false, 2000.00,
        'a45e7d8f-624f-4521-9f8f-868bd5b4f300'),
       ('a45e7d8f-624f-4521-9f8f-868bd5b4f001', 'AS3544RF', 'Honda', 'Accord', 'RED', true, 3000.00, null),
       ('a45e7d8f-624f-4521-9f8f-868bd5b4f002', 'FX9876AA', 'Hyundai', 'Tucson', 'WHITE', true, 4000.00, null);