CREATE TABLE auth_user
(
    id       UUID PRIMARY KEY,
    email    VARCHAR(30) NOT NULL,
    password VARCHAR(50),
    role     VARCHAR(10) NOT NULL
)