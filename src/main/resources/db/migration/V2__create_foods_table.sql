CREATE TABLE foods (
                       id              BIGSERIAL PRIMARY KEY,
                       name            VARCHAR(150)    NOT NULL,
                       calories        NUMERIC(7,2)    NOT NULL,
                       protein_g       NUMERIC(5,2)    NOT NULL,
                       carb_g          NUMERIC(5,2)    NOT NULL,
                       fat_g           NUMERIC(5,2)    NOT NULL,
                       fiber_g         NUMERIC(5,2),
                       created_by      BIGINT REFERENCES users(id),
                       created_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_foods_name ON foods(name);