FROM anapsix/alpine-java:8

EXPOSE 8080

LABEL maintainer="falco@robaux.de"

ADD cashier.jar cashier.jar

ENTRYPOINT ["java","-jar","/cashier.jar","-Dloader.main=de.menschomat.cashier.CashierAppHeadless"]