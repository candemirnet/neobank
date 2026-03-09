CREATE TYPE account_type AS ENUM ('CHECKING', 'SAVINGS');
CREATE TYPE account_status AS ENUM ('ACTIVE', 'FROZEN', 'CLOSED');
CREATE TYPE currency_type AS ENUM ('TRY', 'USD', 'EUR');

CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    account_name VARCHAR(100),
    iban VARCHAR(26) NOT NULL UNIQUE,
    balance DECIMAL(19,4) NOT NULL DEFAULT 0,
    currency currency_type NOT NULL DEFAULT 'TRY',
    account_type account_type NOT NULL DEFAULT 'CHECKING',
    status account_status NOT NULL DEFAULT 'ACTIVE',
    daily_limit DECIMAL(19,4) NOT NULL DEFAULT 100000,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);