
ALTER TABLE accounts ALTER COLUMN account_type DROP DEFAULT;
ALTER TABLE accounts ALTER COLUMN status      DROP DEFAULT;
ALTER TABLE accounts ALTER COLUMN currency    DROP DEFAULT;


ALTER TABLE accounts 
    ALTER COLUMN account_type TYPE VARCHAR(20) USING account_type::VARCHAR;

ALTER TABLE accounts 
    ALTER COLUMN status TYPE VARCHAR(20) USING status::VARCHAR;

ALTER TABLE accounts 
    ALTER COLUMN currency TYPE VARCHAR(3) USING currency::VARCHAR;


DROP TYPE IF EXISTS account_type;
DROP TYPE IF EXISTS account_status;
DROP TYPE IF EXISTS currency_type;


COMMENT ON COLUMN accounts.account_type IS 'AccountType (CHECKING, SAVINGS)';
COMMENT ON COLUMN accounts.status      IS 'AccountStatus (ACTIVE, FROZEN, CLOSED)';
COMMENT ON COLUMN accounts.currency    IS 'CurrencyType (TRY, USD, EUR)';