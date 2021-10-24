CREATE DATABASE IF NOT EXISTS `basic-jdbc-userdetailsservice-authprovider`;

USE `basic-jdbc-userdetailsservice-authprovider`;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users`
(
    `id`       int          NOT NULL AUTO_INCREMENT,
    `username` varchar(45)  NOT NULL,
    `password` varchar(200) NOT NULL,
    `role`     varchar(45)  NOT NULL,
    PRIMARY KEY (`id`)
);
