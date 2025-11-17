CREATE TABLE driver
(
    id                    UUID PRIMARY KEY,
    name                  VARCHAR(50)        NOT NULL,
    surname               VARCHAR(50)        NOT NULL,
    email                 VARCHAR(50) UNIQUE NOT NULL,
    phone                 VARCHAR(9)         NOT NULL,
    average_rating        NUMERIC(3, 2)      NOT NULL,
    CONSTRAINT check_rating_range CHECK (average_rating BETWEEN 0 AND 5),
    amount                NUMERIC(10, 2)     NOT NULL,
    CONSTRAINT check_amount_nonnegative CHECK (amount >= 0),
    driver_licence_number VARCHAR(20)        NOT NULL,
    status                VARCHAR(10)        NOT NULL,
    car_id                UUID,
    role                  VARCHAR(10)        NOT NULL
);

CREATE TABLE user_ride_history
(
    user_id UUID NOT NULL,
    ride_id UUID NOT NULL,
    PRIMARY KEY (user_id, ride_id),
    CONSTRAINT fk_driver_ride_history_driver
        FOREIGN KEY (user_id) REFERENCES driver (id)
            ON DELETE CASCADE
);

CREATE TABLE user_comments
(
    user_id UUID NOT NULL,
    comment VARCHAR(500),
    PRIMARY KEY (user_id, comment),
    CONSTRAINT fk_driver_comments_driver
        FOREIGN KEY (user_id) REFERENCES driver (id)
            ON DELETE CASCADE
);

INSERT INTO driver (id, name, surname, password, email, phone, average_rating, amount, driver_licence_number, status,
                    car_id, role)
VALUES ('a45e7d8f-624f-4521-9f8f-868bd5b4f300', 'Max', 'Vasylenko', '123', 'mvasylenko@gmail.com', '602434123', 0.0,
        3500.00, '85ugghgfu4575', 'OFFLINE', null, 'DRIVER'),
       ('a45e7d8f-624f-4521-9f8f-868bd5b4f301', 'Nadiia', 'Maksimuk', '123', 'nvasylenko@gmail.com', '602434123', 0.0,
        2500.00, '85ugghgf78575', 'OFFLINE', 'a45e7d8f-624f-4521-9f8f-868bd5b4f37a', 'DRIVER'),
       ('a45e7d8f-624f-4521-9f8f-868bd5b4f302', 'Max2', 'Vasylenko2', '123', 'mvasylenko2@gmail.com', '602434120', 0.0,
        3500.00, '85ugghgfu4570', 'OFFLINE', null, 'DRIVER')
