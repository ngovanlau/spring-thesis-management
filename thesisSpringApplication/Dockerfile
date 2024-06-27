FROM tomcat:9-jdk17
MAINTAINER "ngovanlau" "ngovanlau2003@gmail.com"
RUN rm -rvf /usr/local/tomcat/webapps/ROOT
ADD target/thesisSpringApplication-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]
EXPOSE 8080