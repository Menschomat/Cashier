#! /bin/bash
mvn -f ../pom.xml clean install -P docker
cp ../target/cashier-**.jar cashier.jar
docker build -t menschomat/cashier:latest .
rm cashier.jar
