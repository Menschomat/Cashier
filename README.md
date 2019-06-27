# Cashier

> ## **Warning:** README IS OUTDATED SINCE I CHANGED FROM MONGODB TO HIBERNATE/JPA

> ## **Warning:** Current releases are not under test-coverage! Keep that in mind, while using!

## Things to do for more stability:
:construction: TODO/InProgress

:white_check_mark: Done

* JUnit test for backend :construction:
* Karma-Test for frontend :construction:
* Automated test-pipeline (Jenkins?) :construction:

## What is Cashier?

Cashier is a tracking and monitoring tool for your personal cashflow. Many people still use paper or an Excel-sheet as their budget book.

Cashier moves the household book to the web.

---

## Features:
:construction: TODO/InProgress

:white_check_mark: Done

* Scheduled-Transactions :white_check_mark:
* rudimental summary for timespan :white_check_mark:
* Multiuser-Support :white_check_mark:
* Darkmode :white_check_mark:
* Roles (Admin/User) :white_check_mark:
* Transaction list with pagebased-loading and sorting :white_check_mark:
* Incoming/Outgoing money :white_check_mark:
* Fully editable Users :white_check_mark:
* Fully editable Transactions :construction:
* Fancy Charts :construction:
* Hashtag system (nice and fancy) :white_check_mark:
* Docker support :white_check_mark:
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
* JDK 8+ (Works with Java 12 as well)
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
