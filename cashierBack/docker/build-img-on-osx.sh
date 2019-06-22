#! /bin/bash
cp ../target/cashier-**.jar cashier.jar
docker build -t menschomat/cashier:latest .
rm cashier.jar
