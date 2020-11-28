FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package

FROM openjdk:8-jre-alpine3.9
COPY --from=MAVEN_BUILD /target/smart-pot-core-1.0-SNAPSHOT.jar /smart-pot-core.jar
EXPOSE 8080
CMD ["java", "-jar", "/smart-pot-core.jar"]
