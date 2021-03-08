FROM mysql:8.0.4

COPY dba/ /docker-entrypoint-initdb.d/
