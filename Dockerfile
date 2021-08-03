FROM anapsix/alpine-java:8

EXPOSE 8080

LABEL maintainer="falco@robaux.de"

ADD target/cashier-**.jar cashier.jar
ADD docker/docker.properties application.properties

ENTRYPOINT ["java","-jar","/cashier.jar","--spring.config.location=application.properties"]