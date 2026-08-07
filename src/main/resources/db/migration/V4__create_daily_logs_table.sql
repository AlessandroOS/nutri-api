CREATE TABLE daily_logs (
                            id              BIGSERIAL PRIMARY KEY,
                            user_id         BIGINT          NOT NULL REFERENCES users(id),
                            date            DATE            NOT NULL,
                            weight_kg       NUMERIC(5,2),
                            total_calories  NUMERIC(7,2)    NOT NULL DEFAULT 0,
                            total_protein_g NUMERIC(5,2)    NOT NULL DEFAULT 0,
                            total_carb_g    NUMERIC(5,2)    NOT NULL DEFAULT 0,
                            total_fat_g     NUMERIC(5,2)    NOT NULL DEFAULT 0,
                            CONSTRAINT uq_daily_logs_user_date UNIQUE (user_id, date)
);

CREATE INDEX idx_daily_logs_user_date ON daily_logs(user_id, date);