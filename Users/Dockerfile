FROM tomcat:9.0-jdk11-openjdk

ENV DB_HOST=usersqldb.c
ENV DB_PORT=1434
ENV DB_USER=sa
ENV DB_PASSWORD=abc123!!
ENV EUREKA_NAME=eureka.c

COPY ./target/Users-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
