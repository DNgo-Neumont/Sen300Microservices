#!/bin/bash
#mandatory change for commit
for i in {1..100};
do
    echo "ItemSQLSetup"
    #Pulled from https://github.com/twright-msft/mssql-node-docker-demo-app
    /opt/mssql-tools/bin/sqlcmd -S localhost,1433 -U sa -P ${SA_PASSWORD} -d master -i /home/sql_init_script.sql
    if [ $? -eq 0 ]
    then
        echo "setup.sql completed"
        break
    else
        echo "not ready yet..."
        sleep 1
    fi
done

tail -f /var/opt/mssql/log/errorlog