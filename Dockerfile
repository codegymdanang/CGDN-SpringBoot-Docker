FROM openjdk:8-jre-alpine
COPY docker/demo-0.0.1-SNAPSHOT.jar /app.war
EXPOSE 8080
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"]