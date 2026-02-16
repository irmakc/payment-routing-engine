alter table payments
    add column idempotency_key varchar(100);

create unique index ux_payments_idempotency_key
    on payments(idempotency_key);