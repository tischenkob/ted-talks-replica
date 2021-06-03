FROM postgres
COPY ./helios-ted /backups/helios-ted
USER postgres
RUN psql -d postgres < /backups/helios-ted > /dev/null
