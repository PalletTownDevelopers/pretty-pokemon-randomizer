FROM postgres

ENV POSTGRES_PASSWORD toto
ENV POSTGRES_USER randomizer
ENV POSTGRES_DB randomizer

COPY init.sql /docker-entrypoint-initdb.d/
EXPOSE 5432
