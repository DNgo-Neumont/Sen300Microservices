FROM mcr.microsoft.com/mssql/server:2019-latest

COPY ./sql_init_script.sql /home/
COPY ./itemSQLinit.sh /home/
COPY ./entrypointItem.sh .

ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=abc123!!23

# CMD ["/bin/bash","/itemSQLinit.sh"]
#Full path needed to run sqlcmd.
#CMD /opt/mssql-tools/bin/sqlcmd -s localhost:1433 -U sa -P $${SA_PASSWORD} -i sql_init_script.sql
#does not work with dockercompose!


ENTRYPOINT [ "/bin/bash", "entrypointItem.sh" ]
#RUN /opt/mssql/bin/sqlservr
