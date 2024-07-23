FROM openjdk:8-jre-alpine
MAINTAINER Contacto <contacto@contacto.com>
ADD *.jar ms-autorizacion-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "ms-autorizacion-0.0.1-SNAPSHOT.jar"]
EXPOSE 8094