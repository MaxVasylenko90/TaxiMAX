CREATE TABLE auth_user
(
    id       UUID PRIMARY KEY,
    email    VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(255),
    role     VARCHAR(10) NOT NULL
)