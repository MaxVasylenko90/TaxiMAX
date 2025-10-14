CREATE TABLE payment
(
    id          UUID PRIMARY KEY,
    amount      NUMERIC(10, 2) NOT NULL,
    CONSTRAINT check_amount_nonnegative CHECK (amount >= 0),
    sender_id   UUID           NOT NULL,
    receiver_id UUID           NOT NULL,
    date        TIMESTAMP      NOT NULL,
    status      VARCHAR        NOT NULL
);

CREATE TABLE payment_history
(
    id         UUID PRIMARY KEY,
    payment_id UUID      NOT NULL,
    date       TIMESTAMP NOT NULL,
    status     VARCHAR   NOT NULL
)