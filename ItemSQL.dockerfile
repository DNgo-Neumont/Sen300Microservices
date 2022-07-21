FROM mcr.microsoft.com/mssql/server:2019-latest

ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=abc123!!23

ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]

CMD [ "/opt/mssql/bin/sqlservr" ]