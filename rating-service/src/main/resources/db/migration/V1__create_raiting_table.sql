CREATE TABLE rating (
                         id UUID PRIMARY KEY,
                         user_id UUID NOT NULL,
                         stars INT CHECK (stars BETWEEN 1 AND 5),
                         comment VARCHAR(500),
                         date TIMESTAMP
);