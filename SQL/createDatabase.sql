DROP DATABASE IF EXISTS courtsystem;
CREATE DATABASE courtsystem;
USE courtsystem;

CREATE TABLE directory (firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, age int NOT NULL, address VARCHAR(60) NOT NULL, phone VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, username VARCHAR(50) NOT NULL PRIMARY KEY, pword VARCHAR(50) NOT NULL, verified BOOLEAN NOT NULL, shown BOOLEAN NOT NULL, late BOOLEAN NOT NULL, penalized BOOLEAN NOT NULL, owe INT NOT NULL, guests INT NOT NULL, keepAccount BOOLEAN NOT NULL, keepConfirm BOOLEAN NOT NULL, annual INT NOT NULL);

CREATE TABLE waiting (firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, age int NOT NULL, address VARCHAR(60) NOT NULL, phone VARCHAR(50) NOT NULL, email VARCHAR(50) NOT NULL, username VARCHAR(50) NOT NULL, pword VARCHAR(50) NOT NULL, shown BOOLEAN NOT NULL, owe INT NOT NULL);

CREATE TABLE court1 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court2 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court3 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court4 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court5 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court6 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court7 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court8 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court9 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court10 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court11 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);
CREATE TABLE court12 (dayAndTime DATETIME NOT NULL PRIMARY KEY, username VARCHAR(50), occupied int NOT NULL, typeGame boolean default false);

CREATE TABLE bank (username VARCHAR(50) NOT NULL PRIMARY KEY, bankName VARCHAR(50) NOT NULL,accountNum VARCHAR(50) NOT NULL,ssn VARCHAR(50) NOT NULL,accountType VARCHAR(8) NOT NULL,total INT NOT NULL);