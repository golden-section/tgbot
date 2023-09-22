CREATE TABLE IF NOT EXISTS bank (
    id UUID PRIMARY KEY,
    bank_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS currency (
    id UUID PRIMARY KEY,
    currency_code VARCHAR(3) UNIQUE NOT NULL CHECK(LENGTH(currency_code) = 3)
);

CREATE TABLE IF NOT EXISTS currency_rate (
    id UUID PRIMARY KEY,
    bank_id UUID,
    currency_id UUID,
    currency_buy DECIMAL NOT NULL,
    currency_sale DECIMAL NOT NULL,
    exchange_date DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (bank_id) REFERENCES bank (id),
    FOREIGN KEY (currency_id) REFERENCES currency (id)
);

