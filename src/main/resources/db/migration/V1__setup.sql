CREATE TABLE numbers
(
    id   serial                not null,
    val  bigint,
    done boolean default false not null
);

CREATE INDEX numbers_done_id_idx on numbers (done, id);

INSERT INTO numbers (val)
SELECT generate_series(1, 1000)