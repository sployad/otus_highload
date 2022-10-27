-- MySQL dump 10.13  Distrib 8.0.30, for macos12.5 (arm64)
--
-- Host: localhost    Database: rneretin_otus
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `rneretin_otus`
--

CREATE DATABASE IF NOT EXISTS `rneretin_otus` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `rneretin_otus`;

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
                                      `first_name` varchar(25) NOT NULL,
                                      `last_name` varchar(25) NOT NULL,
                                      `age` smallint NOT NULL,
                                      `interest` text NOT NULL,
                                      `city` varchar(50) NOT NULL,
                                      `role` varchar(25) DEFAULT NULL,
                                      `username` varchar(50) NOT NULL,
                                      `password` varchar(255) NOT NULL,
                                      `enabled` tinyint(1) DEFAULT '1',
                                      `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      `update_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `username` (`username`),
                                      KEY `FullName__index` (`first_name`,`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1004814 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client ='utf8' */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


--
-- Table structure for table `friends`
--

CREATE TABLE IF NOT EXISTS  `friends` (
                                          `id` int NOT NULL AUTO_INCREMENT,
                                          `user_from` int DEFAULT NULL,
                                          `user_to` int DEFAULT NULL,
                                          `status` varchar(15) NOT NULL DEFAULT 'WAIT' COMMENT 'Enum: Wait, Approve',
                                          PRIMARY KEY (`id`),
                                          KEY `userFromId` (`user_from`),
                                          KEY `userToId` (`user_to`),
                                          CONSTRAINT `userFromId` FOREIGN KEY (`user_from`) REFERENCES `users` (`id`),
                                          CONSTRAINT `userToId` FOREIGN KEY (`user_to`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client ='utf8' */;
