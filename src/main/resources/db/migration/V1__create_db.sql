CREATE TABLE IF NOT EXISTS bank (
    id UUID PRIMARY KEY,
    bank_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS currency (
    id UUID PRIMARY KEY,
    currency_name VARCHAR(10) UNIQUE NOT NULL,
    currency_code VARCHAR(3) UNIQUE NOT NULL CHECK(LENGTH(currency_code) = 3)
);

CREATE TABLE IF NOT EXISTS currency_rate (
    id UUID PRIMARY KEY,
    currency_rate DECIMAL NOT NULL,
    exchange_date DATE DEFAULT CURRENT_DATE,
    bank_id UUID,
    FOREIGN KEY (bank_id) REFERENCES bank (id),
    currency_id UUID,
    FOREIGN KEY (currency_id) REFERENCES currency (id)
);