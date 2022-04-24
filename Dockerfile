FROM tomcat:8.0

MAINTAINER Joao Filipe Romao
COPY ./target/movies-catalogue-service.war /usr/local/tomcat/webapps/
CMD [ "catalina.sh", "run" ]