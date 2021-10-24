CREATE DATABASE IF NOT EXISTS `basic-jdbc`;

USE `basic-jdbc`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `authorities`;

CREATE TABLE IF NOT EXISTS `users`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(128) NOT NULL,
    `enabled`  INT         NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `authorities`
(
    `id`        int         NOT NULL AUTO_INCREMENT,
    `username`  varchar(45) NOT NULL,
    `authority` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);