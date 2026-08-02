CREATE TABLE users (
                       id          BIGSERIAL PRIMARY KEY,
                       name        VARCHAR(100)        NOT NULL,
                       email       VARCHAR(150)        NOT NULL UNIQUE,
                       password    VARCHAR(255)        NOT NULL,
                       birth_date  DATE,
                       sex         VARCHAR(1),
                       height_cm   NUMERIC(5,2),
                       weight_kg   NUMERIC(5,2),
                       goal        VARCHAR(20),
                       calorie_goal INTEGER,
                       created_at  TIMESTAMP           NOT NULL DEFAULT NOW()
);