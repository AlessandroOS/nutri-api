CREATE TABLE meals (
                       id          BIGSERIAL PRIMARY KEY,
                       user_id     BIGINT          NOT NULL REFERENCES users(id),
                       meal_type   VARCHAR(20)     NOT NULL,
                       date        DATE            NOT NULL,
                       created_at  TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE meal_items (
                            id          BIGSERIAL PRIMARY KEY,
                            meal_id     BIGINT          NOT NULL REFERENCES meals(id) ON DELETE CASCADE,
                            food_id     BIGINT          NOT NULL REFERENCES foods(id),
                            quantity_g  NUMERIC(7,2)    NOT NULL,
                            calories    NUMERIC(7,2)    NOT NULL,
                            protein_g   NUMERIC(5,2)    NOT NULL,
                            carb_g      NUMERIC(5,2)    NOT NULL,
                            fat_g       NUMERIC(5,2)    NOT NULL
);

CREATE INDEX idx_meals_user_date ON meals(user_id, date);