-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: bostadbästdb
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bostadsrätt`
--

DROP TABLE IF EXISTS `bostadsrätt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bostadsrätt` (
  `nummer` int(11) NOT NULL,
  `typ` varchar(45) NOT NULL,
  `yta` int(10) unsigned NOT NULL,
  `våning` int(11) NOT NULL,
  `hus` int(11) NOT NULL,
  `årsavgiftsgrupp` int(11) NOT NULL,
  `bID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`bID`),
  UNIQUE KEY `AN1` (`nummer`,`hus`),
  KEY `FK_bostadsrätt_hus_idx` (`hus`),
  KEY `FK_bostadsrätt_årsavgiftsgrupp_idx` (`årsavgiftsgrupp`),
  CONSTRAINT `FK_bostadsrätt_hus` FOREIGN KEY (`hus`) REFERENCES `hus` (`husID`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `FK_bostadsrätt_årsavgiftsgrupp` FOREIGN KEY (`årsavgiftsgrupp`) REFERENCES `årsavgiftsgrupp` (`gID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bostadsrätt`
--

LOCK TABLES `bostadsrätt` WRITE;
/*!40000 ALTER TABLE `bostadsrätt` DISABLE KEYS */;
INSERT INTO `bostadsrätt` VALUES (1,'2a',42,-2,3,3,1),(2,'1a',24,-1,3,1,2),(3,'1a',24,-1,3,1,3),(4,'1',25,0,3,3,4),(5,'2',42,1,3,3,5),(1,'3a',60,1,4,3,6),(2,'3a',65,3,4,4,7),(1,'1a',21,0,5,2,8),(1,'2a',32,1,6,2,9),(1,'3a',63,2,7,2,10),(1,'4a',70,2,8,5,11);
/*!40000 ALTER TABLE `bostadsrätt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cykelrum`
--

DROP TABLE IF EXISTS `cykelrum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cykelrum` (
  `yta` int(10) unsigned NOT NULL,
  `namn` varchar(45) DEFAULT NULL,
  `hus` int(11) NOT NULL,
  `cID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cID`),
  KEY `FK_cykelrum_hus_idx` (`hus`),
  CONSTRAINT `FK_cykelrum_hus` FOREIGN KEY (`hus`) REFERENCES `hus` (`husID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cykelrum`
--

LOCK TABLES `cykelrum` WRITE;
/*!40000 ALTER TABLE `cykelrum` DISABLE KEYS */;
INSERT INTO `cykelrum` VALUES (10,NULL,3,12),(10,'1',4,13),(20,'1',5,14),(15,'2',5,15),(5,'1a',6,16),(5,'1b',6,17),(10,'2a',6,18),(10,'2b',6,19),(10,'1a',7,20),(18,'A',9,21),(8,NULL,10,22);
/*!40000 ALTER TABLE `cykelrum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `förening`
--

DROP TABLE IF EXISTS `förening`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `förening` (
  `orgnr` varchar(45) NOT NULL,
  `namn` varchar(45) NOT NULL,
  `maxAntalParkeringar` int(10) unsigned NOT NULL,
  PRIMARY KEY (`orgnr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `förening`
--

LOCK TABLES `förening` WRITE;
/*!40000 ALTER TABLE `förening` DISABLE KEYS */;
INSERT INTO `förening` VALUES ('123456-1234','Solgläntan',0),('246732-5728','Helvettet',4),('298922-1234','Bergdalen',1),('318753-4235','Skogstoppen',2),('556831-7704','Solgläntan',1),('723486-4591','Vindfältet',3);
/*!40000 ALTER TABLE `förening` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_INSERT_förening_orgnr` BEFORE INSERT ON `förening` FOR EACH ROW BEGIN 
IF (NEW.orgnr NOT REGEXP "^[0-9]{6}(-)[0-9]{4}$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Ogiltigt organisationsnummer!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_UPDATE_förening_orgnr` BEFORE UPDATE ON `förening` FOR EACH ROW BEGIN 
IF (NEW.orgnr NOT REGEXP "^[0-9]{6}(-)[0-9]{4}$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Ogiltigt organisationsnummer!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `hus`
--

DROP TABLE IF EXISTS `hus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hus` (
  `husID` int(11) NOT NULL AUTO_INCREMENT,
  `våningar` int(10) unsigned NOT NULL,
  `postnummer` varchar(45) NOT NULL,
  `postort` varchar(45) NOT NULL,
  `gatuadress` varchar(45) NOT NULL,
  `gatunummer` varchar(45) NOT NULL,
  `byggår` year(4) NOT NULL,
  `förening` varchar(45) NOT NULL,
  PRIMARY KEY (`husID`),
  UNIQUE KEY `AN1` (`postnummer`,`gatuadress`,`gatunummer`),
  UNIQUE KEY `AN2` (`förening`,`gatuadress`,`gatunummer`),
  KEY `FK_hus_förening_idx` (`förening`),
  CONSTRAINT `FK_hus_förening` FOREIGN KEY (`förening`) REFERENCES `förening` (`orgnr`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hus`
--

LOCK TABLES `hus` WRITE;
/*!40000 ALTER TABLE `hus` DISABLE KEYS */;
INSERT INTO `hus` VALUES (3,1,'16856','Bromma','Tintinvägen','1a',2001,'556831-7704'),(4,1,'16856','Bromma','Tintinvägen','1b',2001,'556831-7704'),(5,2,'15864','Kista','Sveavägen','17',2000,'298922-1234'),(6,10,'13234','Saltsjö-boo','Sveavägen','18',2000,'298922-1234'),(7,1,'18378','Spånga','Storstenvägen','21',1954,'298922-1234'),(8,4,'17315','Tensta','Örnvägen','14a',2007,'723486-4591'),(9,1,'66666','Tyresö','Kungsvägen','57',1999,'246732-5728'),(10,2,'33657','Järfälla','Vasavägen','512',1989,'246732-5728');
/*!40000 ALTER TABLE `hus` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_INSERT_hus_våningar` BEFORE INSERT ON `hus` FOR EACH ROW BEGIN 
IF (NEW.våningar = 0) THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Antalet våningar får inte vara noll!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_INSERT_hus_postnummer` BEFORE INSERT ON `hus` FOR EACH ROW BEGIN 
IF (NEW.postnummer NOT REGEXP "^[0-9]{5}$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Postnumret måste bestå av fem siffror!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_INSERT_hus_postort` BEFORE INSERT ON `hus` FOR EACH ROW BEGIN 
IF (NEW.postort NOT REGEXP "^([[:alpha:]]+(-)?[[:blank:]]?)+$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Postorten får endast innehålla bokstäver, mellanslag eller bindessträck!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_INSERT_hus_gatuadress` BEFORE INSERT ON `hus` FOR EACH ROW BEGIN 
IF (NEW.gatuadress NOT REGEXP "^([[:alpha:]]+(-)?[[:blank:]]?)+$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Gatuadressen får endast innehålla bokstäver, mellanslag eller bindessträck!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_INSERT_hus_gatunummer` BEFORE INSERT ON `hus` FOR EACH ROW BEGIN 
IF (NEW.gatunummer NOT REGEXP "^([[:alnum:]]+(-)?)+$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Gatunumret får inte börja med bindessträck men får innehålla bokstäver och siffror!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_UPDATE_hus_våningar` BEFORE UPDATE ON `hus` FOR EACH ROW BEGIN 
IF (NEW.våningar = 0) THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Antalet våningar får inte vara noll!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_UPDATE_hus_postnummer` BEFORE UPDATE ON `hus` FOR EACH ROW BEGIN 
IF (NEW.postnummer NOT REGEXP "^[0-9]{5}$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Postnumret måste bestå av fem siffror!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_UPDATE_hus_postort` BEFORE UPDATE ON `hus` FOR EACH ROW BEGIN 
IF (NEW.postort NOT REGEXP "^([[:alpha:]]+(-)?[[:blank:]]?)+$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Postorten får endast innehålla bokstäver, mellanslag eller bindessträck!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_UPDATE_hus_gatuadress` BEFORE UPDATE ON `hus` FOR EACH ROW BEGIN 
IF (NEW.gatuadress NOT REGEXP "^([[:alpha:]]+(-)?[[:blank:]]?)+$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Gatuadressen får endast innehålla bokstäver, mellanslag eller bindessträck!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_UPDATE_hus_gatunummer` BEFORE UPDATE ON `hus` FOR EACH ROW BEGIN 
IF (NEW.gatunummer NOT REGEXP "^([[:alnum:]]+(-)?)+$") THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Gatunumret får inte börja med bindessträck men får innehålla bokstäver och siffror!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `årsavgift`
--

DROP TABLE IF EXISTS `årsavgift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `årsavgift` (
  `avgift` double NOT NULL,
  `år` year(4) NOT NULL,
  `årsavgiftsgrupp` int(11) NOT NULL,
  PRIMARY KEY (`år`,`årsavgiftsgrupp`),
  KEY `FK_årsavgift_årsavgiftsgrupp_idx` (`årsavgiftsgrupp`),
  CONSTRAINT `FK_årsavgift_årsavgiftsgrupp` FOREIGN KEY (`årsavgiftsgrupp`) REFERENCES `årsavgiftsgrupp` (`gID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `årsavgift`
--

LOCK TABLES `årsavgift` WRITE;
/*!40000 ALTER TABLE `årsavgift` DISABLE KEYS */;
INSERT INTO `årsavgift` VALUES (2341.5,1999,6),(2000,2007,1),(2100,2008,1),(1900.9,2008,6),(2200,2009,1),(1950,2009,6),(2300,2010,1),(1940,2010,6),(2400,2011,1),(2000,2011,6),(2500,2012,1),(2400,2013,1),(2412,2014,1),(2300,2015,1),(2200,2016,1),(2050,2017,1),(2000,2018,1),(2000,2018,3),(1900,2019,1),(1800,2019,2),(1800,2019,3),(1700,2019,4),(1700,2019,5);
/*!40000 ALTER TABLE `årsavgift` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_INSERT_årsavgift_avgift` BEFORE INSERT ON `årsavgift` FOR EACH ROW BEGIN 
IF (NEW.avgift < 0) THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Avgiften får inte vara negativ!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `TRIGGER_UPDATE_årsavgift_avgift` BEFORE UPDATE ON `årsavgift` FOR EACH ROW BEGIN 
IF (NEW.avgift < 0) THEN 
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Avgiften får inte vara negativ!';
END IF; 
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `årsavgiftsgrupp`
--

DROP TABLE IF EXISTS `årsavgiftsgrupp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `årsavgiftsgrupp` (
  `gNamn` varchar(45) NOT NULL,
  `förening` varchar(45) NOT NULL,
  `gID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`gID`),
  UNIQUE KEY `AN1` (`gNamn`,`förening`),
  KEY `FK_årsavgiftsgrupp_förening_idx` (`förening`),
  CONSTRAINT `FK_årsavgiftsgrupp_förening` FOREIGN KEY (`förening`) REFERENCES `förening` (`orgnr`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `årsavgiftsgrupp`
--

LOCK TABLES `årsavgiftsgrupp` WRITE;
/*!40000 ALTER TABLE `årsavgiftsgrupp` DISABLE KEYS */;
INSERT INTO `årsavgiftsgrupp` VALUES ('110','556831-7704',4),('112','298922-1234',2),('112','556831-7704',1),('114','556831-7704',3),('121C','723486-4591',5),('128A','318753-4235',6),('129B','318753-4235',7);
/*!40000 ALTER TABLE `årsavgiftsgrupp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-02 17:34:18
