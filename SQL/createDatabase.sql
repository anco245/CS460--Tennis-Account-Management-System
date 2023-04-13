DROP DATABASE IF EXISTS courtsystem;
CREATE DATABASE courtsystem;
USE courtsystem;

CREATE TABLE directory (
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	age int NOT NULL,
	address VARCHAR(60) NOT NULL,
	phone VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL PRIMARY KEY,
	pword VARCHAR(50) NOT NULL,
	verified BOOLEAN NOT NULL,
	shown BOOLEAN NOT NULL,
	late BOOLEAN NOT NULL,
    penalized BOOLEAN NOT NULL,
	owe INT NOT NULL,
    guests INT NOT NULL,
    keepAccount BOOLEAN NOT NULL,
    keepConfirm BOOLEAN NOT NULL
    -- amount of res INT NOT NULL
);

CREATE TABLE waiting (
	priority int AUTO_INCREMENT NOT NULL PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	age VARCHAR(5) NOT NULL,
	address VARCHAR(60) NOT NULL,
	phone VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	username VARCHAR(50) NOT NULL,
	pword VARCHAR(50) NOT NULL,
    shown BOOL NOT NULL,
    owe INT NOT NULL
);

CREATE TABLE court1 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court2 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
	ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court3 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
	ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court4 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court5 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court6 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court7 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court8 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court9 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court10 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court11 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);

CREATE TABLE court12 (
	dayAndTime DATETIME NOT NULL PRIMARY KEY,
    ofDay date NOT NULL, 
    ofTime time NOT NULL,
    username VARCHAR(50),
    occupied boolean NOT NULL
);