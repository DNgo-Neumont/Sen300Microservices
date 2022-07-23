FROM mcr.microsoft.com/mssql/server:2019-latest

COPY ./init_sql_database.sql /home/
COPY ./userSQLinit.sh /home/
COPY ./entrypointUser.sh .

ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=abc123!!23

#RUN /userSQLinit.sh
#Full path required to run sqlcmd proper.
# CMD /opt/mssql-tools/bin/sqlcmd -s usersqldb.c -U sa -P $${SA_PASSWORD} -i init_sql_database.sql
#does not work with dockercompose!



ENTRYPOINT [ "/bin/bash", "entrypointUser.sh" ]
#CMD /opt/mssql/bin/sqlservr
