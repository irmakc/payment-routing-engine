create table payment_attempts (
                                  id uuid primary key,
                                  payment_id uuid not null,
                                  provider varchar(50) not null,
                                  status varchar(30) not null,
                                  failure_reason varchar(500),
                                  created_at timestamp not null
);

create index idx_payment_attempts_payment_id on payment_attempts(payment_id);