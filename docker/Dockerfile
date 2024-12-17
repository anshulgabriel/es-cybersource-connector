FROM  azul/zulu-openjdk-alpine:21-jre-latest

WORKDIR /app

COPY ../build/libs/es-cybersource-connector-2.0.0-boot.jar app.jar
EXPOSE 8008
ENV AWS_REGION=ap-southeast-2


ENTRYPOINT ["java", "-jar", "app.jar"]