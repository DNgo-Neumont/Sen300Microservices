#!/bin/bash
# set -e

# if [ "$1" = '/opt/mssql/bin/sqlservr' ]; then
#   if [ ! -f /tmp/app-initialized ]; then
#     function initialize_app_database() {

#       #/opt/msql-tools/bin/sqlcmd -S localhost -U sa -P abc123!!23 -d master -i init_sql_user.sql
      
#       touch /tmp/app-initialized
#     }
    
#     initialize_app_database & userSQLinit.sh
#   fi
# fi

# exec "$@"
/opt/mssql/bin/sqlservr & /home/userSQLinit.sh
