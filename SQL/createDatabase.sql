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
    keepAccount BOOLEAN NOT NULL
);

CREATE TABLE reservation (
	courtNum INT NOT NULL PRIMARY KEY,
	username VARCHAR(50),
    resTime datetime,
    isRes BOOLEAN NOT NULL,
    FOREIGN KEY (username) REFERENCES directory(username) 
		ON DELETE CASCADE
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

INSERT INTO reservation (courtNum, isRes) VALUES (1, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (2, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (3, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (4, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (5, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (6, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (7, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (8, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (9, FALSE);

INSERT INTO reservation (courtNum, isRes) VALUES (10, FALSE);