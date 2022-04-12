CREATE TYPE status AS ENUM ('OPEN', 'DONE');

CREATE TABLE task
(
    id          INTEGER generated by default as identity,
    name        VARCHAR               NOT NULL,
    description VARCHAR               NOT NULL,
    status      status DEFAULT 'OPEN' NOT NULL,
    business_data    VARCHAR,
    PRIMARY KEY (id)
);


