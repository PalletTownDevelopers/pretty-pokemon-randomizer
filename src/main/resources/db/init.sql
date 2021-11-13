CREATE TABLE ppr_rom
(
    id         SERIAL NOT NULL PRIMARY KEY,
    seed       int    NOT NULL,
    parameters jsonb   NOT NULL
);

CREATE SEQUENCE ppr_rom_id_seq START 1;