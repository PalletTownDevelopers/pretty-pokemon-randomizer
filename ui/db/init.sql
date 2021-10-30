CREATE TABLE ppr_rom
(
    id         SERIAL NOT NULL PRIMARY KEY,
    seed       int    NOT NULL,
    parameters jsonb   NOT NULL
);