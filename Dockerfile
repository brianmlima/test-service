
# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:8-jre-alpine
ARG DEPENDENCIES=target/lib
# Copy dependencies
COPY ${DEPENDENCIES} /app/lib
# Copy application thin jar
COPY target/test-service-*.jar /app.jar
# Specify default command
ARG default_spring_profile=test
ENV spring_profile=$default_spring_profile
CMD ["/usr/bin/java","--classpath", "${DEPENDENCIES}","-jar", "-Dspring.profiles.active=${spring_profile}", "/app.jar"]