DROP DATABASE if exists unilib; 
CREATE DATABASE unilib; 
USE unilib; 
-- MySQL dump 10.13  Distrib 5.6.21, for Win32 (x86)
--
-- Host: localhost    Database: unilib
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `esami`
--

DROP TABLE IF EXISTS `esami`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `esami` (
  `matricolaStudente` int(10) unsigned NOT NULL,
  `codiceEsame` char(5) NOT NULL,
  `insegnamento` varchar(127) NOT NULL,
  `INF` tinyint(1) NOT NULL,
  `crediti` int(10) unsigned NOT NULL,
  `voto` varchar(4) NOT NULL,
  `data` date NOT NULL,
  PRIMARY KEY (`matricolaStudente`,`codiceEsame`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `esami`
--

LOCK TABLES `esami` WRITE;
/*!40000 ALTER TABLE `esami` DISABLE KEYS */;
INSERT INTO `esami` VALUES (111111,'011BB','Fisica Generale',0,12,'23','2018-09-13'),(111111,'074II','Reti Logiche',1,9,'18','2019-02-05'),(111111,'076II','Elettronica Digitale',0,9,'27','2017-11-16'),(111111,'546II','Programmazione Avanzata',1,6,'25','2019-09-25'),(123456,'011BB','Fisica Generale',0,12,'22','2019-05-21'),(123456,'074II','Reti Logiche',1,9,'29','2017-02-23'),(123456,'170AA','Ricerca Operativa',0,9,'29','2019-02-27'),(123456,'374II','Ingegneria del Software',1,6,'29','2015-03-19'),(123456,'544II','Sistemi Operativi',1,9,'30','2017-05-24'),(123456,'546II','Programmazione Avanzata',1,6,'30L','2019-01-12'),(518402,'004AA','Analisi Matematica I',0,12,'19','2015-07-15'),(518402,'011BB','Fisica Generale',0,12,'25','2018-02-14'),(518402,'073II','Elettrotecnica',0,6,'30','2018-02-21'),(518402,'074II','Reti Logiche',1,9,'19','2016-07-04'),(518402,'075II','Comunicazioni Numeriche',1,9,'28','2019-06-04'),(518402,'076II','Elettronica Digitale',0,9,'23','2018-07-25'),(518402,'077II','Fondamenti di Automatica',0,9,'19','2019-02-18'),(518402,'078II','Calcolatori Elettronici',1,9,'30L','2017-01-20'),(518402,'080II','Progettazione Web',1,6,'26','2019-07-19'),(518402,'116II','Economia e Organizzazione Aziendale',0,6,'30L','2018-06-30'),(518402,'170AA','Ricerca Operativa',0,9,'24','2019-02-27'),(518402,'173AA','Calcolo Numerico',0,6,'25','2018-11-19'),(518402,'374II','Ingegneria del Software',1,6,'19','2017-07-05'),(518402,'544II','Sistemi Operativi',1,9,'18','2017-02-15'),(518402,'545II','Reti Informatiche',1,9,'27','2017-06-07'),(518402,'549II','Fondamenti di Informatica II',1,12,'24','2016-03-15'),(518402,'550II','Fondamenti di Informatica I',1,12,'29','2015-01-27'),(518402,'591AA','Analisi Matematica II e Algebra Lineare',0,12,'19','2016-06-16'),(518402,'615II','Progettazione di Reti Informatiche',1,6,'28','2017-09-15'),(654321,'073II','Elettrotecnica',0,6,'29','2018-09-12'),(654321,'080II','Progettazione Web',1,6,'30L','2019-09-09'),(654321,'116II','Economia e Organizzazione Aziendale',0,6,'18','2017-09-14'),(654321,'544II','Sistemi Operativi',1,9,'27','2018-09-20'),(654321,'545II','Reti Informatiche',1,9,'26','2019-01-09'),(654321,'549II','Fondamenti di Informatica II',1,12,'28','2018-03-14'),(654321,'550II','Fondamenti di Informatica I',1,12,'21','2019-07-10');
/*!40000 ALTER TABLE `esami` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-15 19:29:33
