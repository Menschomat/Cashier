# Cashier

> ## First relase of Cashier is still in development!

## Things to do for release:
:construction: TODO/InProgress

:white_check_mark: Done

* Implementation of Scheduled-Transactions in backend :white_check_mark:
* Implementation of Scheduled-Transactions in frontend :white_check_mark:
* Implementation of rudimental summary for timespan :construction:
* Docker-Image :construction:
* (JUnit test for backend) :construction:

## What is Cashier?

Cashier is a tracking and monitoring tool for your personal cashflow. Many people still use paper or an Excel-sheet as their budget book.

Cashier moves the household book to the web.

---

## Features:
:construction: TODO/InProgress

:white_check_mark: Done

* Scheduled-Transactions in backend :construction:
* Multiuser-Support :white_check_mark:
* Darkmode :white_check_mark:
* Roles (Admin/User) :white_check_mark:
* Transaction list with pagebased-loading and sorting :white_check_mark:
* Incoming/Outgoing money :white_check_mark:
* Fully editable Users :white_check_mark:
* Fully editable Transactions :construction:
* Fancy Charts :construction:
* Hashtag system (nice and fancy) :white_check_mark:
* Docker support :construction:
* Standalone mode (Json as storage) :construction:
* Secure login via OAuth :white_check_mark: 
* Fast :white_check_mark:

---

## Technology:

### Backend:
* Java
* Spring Boot
* Mongo DB

### Frontend:
* Angular
* Angular Material
* Chart.js

### Security:
* OAuth

---

## Development (You'll get more information after first release-version is ready):

>User `admin` is created at first startup with password `admin123` !

### 1) Requirements:
* JDK 8+
* NodeJs + NPM
* MongoDB-Server
* Docker for MongoDB-Server (recommended)
* Angular CLI
* VS-Studio Code (recommended)
* IntelliJ (recommended)


### 2.1) Checkout the Project:

`git clone https://github.com/Menschomat/Cashier.git`

### 2.2) Working with backend:

You will need a runing Mongo-DB-Server (without login at the moment) on default-port (I reccommend running this server dockerized)!

The backend is am Maven-Project, so make sure you installed all dependencies.

### 2.3) Working with frontend:

The frontend uses npm for packagemanagement. Run `npm install` to install all dependencies. To start the dev-server run `ng serve --proxy-config proxy.conf.json`.
