FROM tomcat:9.0-jdk11-openjdk

ENV DB_HOST=UserSQLDB
ENV DB_PORT=1433
ENV DB_USER=sa
ENV DB_PASSWORD=abc123!!@

COPY ./target/Users-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
