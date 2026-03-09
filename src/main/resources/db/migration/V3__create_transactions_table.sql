CREATE TYPE transaction_type AS ENUM ('TRANSFER', 'DEPOSIT', 'WITHDRAWAL', 'FEE');
CREATE TYPE transaction_status AS ENUM ('PENDING', 'SUCCESS', 'FAILED');

CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    reference_no UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    sender_account_id BIGINT REFERENCES accounts(id),
    receiver_account_id BIGINT REFERENCES accounts(id),
    amount DECIMAL(19,4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    type transaction_type NOT NULL,
    status transaction_status NOT NULL DEFAULT 'PENDING',
    description VARCHAR(255),
    fee_amount DECIMAL(19,4) DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);