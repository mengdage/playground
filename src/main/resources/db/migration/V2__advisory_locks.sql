CREATE TABLE IF NOT EXISTS advisory_locks (
    id                        BIGSERIAL                         NOT NULL PRIMARY KEY,
    lock_identifier           VARCHAR                           NOT NULL UNIQUE,
    created_on                TIMESTAMP WITHOUT TIME ZONE       NOT NULL,
    expires_on                TIMESTAMP WITHOUT TIME ZONE       NOT NULL,
    lock_holder_identifier    VARCHAR                           NOT NULL
);
