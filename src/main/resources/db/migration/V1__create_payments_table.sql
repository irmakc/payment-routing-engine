CREATE TABLE payments
(
    id          UUID NOT NULL,
    amount      numeric(19, 2) NOT NULL,
    currency    VARCHAR(3) NOT NULL,
    customer_id VARCHAR(100) NOT NULL,
    provider    VARCHAR(50),
    status      VARCHAR(30) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_payments PRIMARY KEY (id)
);