FROM mysql:8.1
LABEL authors="ngovanlau"
LABEL email="ngovanlau2003@gmail.com"

RUN chown -R mysql:root /var/lib/mysql/

ARG MYSQL_DATABASE=thesisspringapp
ARG MYSQL_ROOT_PASSWORD=12345
ARG MYSQL_ALLOW_EMPTY_PASSWORD=yes

ENV MYSQL_DATABASE=$MYSQL_DATABASE
ENV MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD
ENV MYSQL_ALLOW_EMPTY_PASSWORD=$MYSQL_ALLOW_EMPTY_PASSWORD

ADD thesis-management-mysql.sql /etc/mysql/data.sql

RUN sed -i 's/MYSQL_DATABASE/'$MYSQL_DATABASE'/g' /etc/mysql/data.sql
RUN cp /etc/mysql/data.sql /docker-entrypoint-initdb.d

EXPOSE 3306