CREATE TABLE passenger
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(50)        NOT NULL,
    surname       VARCHAR(50)        NOT NULL,
    password      VARCHAR(50)        NOT NULL,
    email         VARCHAR(50) UNIQUE NOT NULL,
    phone         VARCHAR(9)         NOT NULL,
    average_rating NUMERIC(3, 2)      NOT NULL, CONSTRAINT check_rating_range CHECK (average_rating BETWEEN 0 AND 5),
    amount        NUMERIC(10, 2)     NOT NULL, CONSTRAINT check_amount_nonnegative CHECK (amount >= 0)
);

CREATE TABLE user_ride_history
(
    user_id UUID NOT NULL,
    ride_id UUID NOT NULL,
    PRIMARY KEY (user_id, ride_id),
    CONSTRAINT fk_driver_ride_history_driver
        FOREIGN KEY (user_id) REFERENCES passenger (id)
            ON DELETE CASCADE
);

CREATE TABLE user_comments
(
    user_id UUID NOT NULL,
    comment VARCHAR(500),
    PRIMARY KEY (user_id, comment),
    CONSTRAINT fk_driver_comments_driver
        FOREIGN KEY (user_id) REFERENCES passenger (id)
            ON DELETE CASCADE
);