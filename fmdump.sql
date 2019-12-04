CREATE DATABASE  IF NOT EXISTS `farmersmarket` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `farmersmarket`;
-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: localhost    Database: farmersmarket
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `buyer`
--

DROP TABLE IF EXISTS `buyer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buyer` (
  `bid` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer`
--

LOCK TABLES `buyer` WRITE;
/*!40000 ALTER TABLE `buyer` DISABLE KEYS */;
INSERT INTO `buyer` VALUES (1,'Hans','Zimmer','1 Blueberry Ave'),(2,'George','Lucas','66 Sith St'),(3,'Nick','Harper','4 Main St'),(4,'Angel','Murphy','55 Rainbow Rd'),(5,'Dorothy','Brinker','17 Huntington Ave'),(6,'Penny','Wise','5 Gutter Lane'),(7,'Vito','Corleone','75 Parker Rd'),(8,'Travis ','Bickle','2 Taxi Drive'),(9,'Bruce','Wayne','45 Gotham St'),(10,'Lois','Lane','3 Lois Lane');
/*!40000 ALTER TABLE `buyer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buyer_to_posting`
--

DROP TABLE IF EXISTS `buyer_to_posting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buyer_to_posting` (
  `bid` int(11) NOT NULL,
  `postingid` int(11) NOT NULL,
  `date_sold` date NOT NULL,
  PRIMARY KEY (`bid`,`postingid`),
  KEY `buyer_to_posting_fk_posting` (`postingid`),
  CONSTRAINT `buyer_to_posting_fk_buyer` FOREIGN KEY (`bid`) REFERENCES `buyer` (`bid`) ON DELETE RESTRICT,
  CONSTRAINT `buyer_to_posting_fk_posting` FOREIGN KEY (`postingid`) REFERENCES `posting` (`postingid`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer_to_posting`
--

LOCK TABLES `buyer_to_posting` WRITE;
/*!40000 ALTER TABLE `buyer_to_posting` DISABLE KEYS */;
INSERT INTO `buyer_to_posting` VALUES (1,1,'2019-11-10'),(2,2,'2019-11-11'),(3,3,'2019-11-12'),(4,4,'2019-11-13'),(5,5,'2019-11-14');
/*!40000 ALTER TABLE `buyer_to_posting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalog`
--

DROP TABLE IF EXISTS `catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalog` (
  `cid` int(11) NOT NULL,
  `produce_name` varchar(50) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalog`
--

LOCK TABLES `catalog` WRITE;
/*!40000 ALTER TABLE `catalog` DISABLE KEYS */;
INSERT INTO `catalog` VALUES (1,'Potatoes'),(2,'Corn'),(3,'Eggs'),(4,'Apples'),(5,'Beets'),(6,'Tomatoes'),(7,'Yams'),(8,'Carrots'),(9,'Watermelon'),(10,'wheat');
/*!40000 ALTER TABLE `catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier`
--

DROP TABLE IF EXISTS `courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier` (
  `courid` int(11) NOT NULL,
  `courier_name` varchar(50) NOT NULL,
  `courier_type` varchar(50) NOT NULL,
  `fee` decimal(10,0) NOT NULL,
  PRIMARY KEY (`courid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier`
--

LOCK TABLES `courier` WRITE;
/*!40000 ALTER TABLE `courier` DISABLE KEYS */;
INSERT INTO `courier` VALUES (1,'Fedex','Express',11),(2,'UPS','Standard',6),(3,'USPS','Standard',3),(4,'Prime','Express',8),(5,'DHL','Standard',5);
/*!40000 ALTER TABLE `courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farm`
--

DROP TABLE IF EXISTS `farm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farm` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `farm_name` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  `farmer` varchar(50) NOT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farm`
--

LOCK TABLES `farm` WRITE;
/*!40000 ALTER TABLE `farm` DISABLE KEYS */;
INSERT INTO `farm` VALUES (1,'Smith','Hudson, NH','Adam Smith'),(2,'McDonald','Philadelphia, PA','Ronald McDonald'),(3,'Eastie','Boston, MA','Bob Parker'),(4,'Doe','Dallas, TX','John Doe'),(5,'Superior','Boston, MA','Raymond Smith'),(6,'Creek','Templeton, CA','Jack Creek'),(7,'Cherry Crest','Ronks, PA','Bilbo Baggins'),(8,'Snow','Winterfell, Westeros','Jon Snow'),(9,'Allandale','Chestnut Hill, MA','Alan Dale'),(10,'Shrute','Scranton, PA','Dwight Shrute');
/*!40000 ALTER TABLE `farm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posting`
--

DROP TABLE IF EXISTS `posting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posting` (
  `postingid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `courid` int(11) NOT NULL,
  `cost` decimal(10,0) NOT NULL,
  `date_posted` date NOT NULL,
  PRIMARY KEY (`postingid`),
  UNIQUE KEY `unique_sid_pid` (`sid`,`pid`),
  KEY `posting_fk_produce` (`pid`),
  KEY `posting_fk_courier` (`courid`),
  CONSTRAINT `posting_fk_courier` FOREIGN KEY (`courid`) REFERENCES `courier` (`courid`) ON DELETE RESTRICT,
  CONSTRAINT `posting_fk_produce` FOREIGN KEY (`pid`) REFERENCES `produce` (`pid`) ON DELETE RESTRICT,
  CONSTRAINT `posting_fk_seller` FOREIGN KEY (`sid`) REFERENCES `seller` (`sid`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posting`
--

LOCK TABLES `posting` WRITE;
/*!40000 ALTER TABLE `posting` DISABLE KEYS */;
INSERT INTO `posting` VALUES (1,3,1,3,10,'2019-10-11'),(2,2,2,3,21,'2019-10-12'),(3,1,3,1,20,'2019-10-13'),(4,3,4,2,16,'2019-10-16'),(5,5,5,5,12,'2019-10-17'),(6,6,6,1,12,'2019-10-18'),(7,4,7,4,38,'2019-10-19'),(8,4,8,4,6,'2019-10-19'),(9,4,9,5,3,'2019-10-24'),(10,1,1,2,11,'2019-10-11');
/*!40000 ALTER TABLE `posting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produce`
--

DROP TABLE IF EXISTS `produce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produce` (
  `pid` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `fid` int(11) NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `produce_fk_catalog` (`cid`),
  KEY `produce_fk_farm` (`fid`),
  CONSTRAINT `produce_fk_catalog` FOREIGN KEY (`cid`) REFERENCES `catalog` (`cid`) ON DELETE RESTRICT,
  CONSTRAINT `produce_fk_farm` FOREIGN KEY (`fid`) REFERENCES `farm` (`fid`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produce`
--

LOCK TABLES `produce` WRITE;
/*!40000 ALTER TABLE `produce` DISABLE KEYS */;
INSERT INTO `produce` VALUES (1,15,5,10),(2,2,2,1),(3,1,3,2),(4,2,4,3),(5,1,1,4),(6,1,9,6),(7,5,8,4),(8,10,7,4),(9,5,7,4);
/*!40000 ALTER TABLE `produce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `bid` int(11) NOT NULL,
  `fid` int(11) NOT NULL,
  `review` varchar(250) NOT NULL,
  PRIMARY KEY (`rid`),
  KEY `review_fk_buyer` (`bid`),
  KEY `review_fk_farm` (`fid`),
  CONSTRAINT `review_fk_buyer` FOREIGN KEY (`bid`) REFERENCES `buyer` (`bid`) ON DELETE RESTRICT,
  CONSTRAINT `review_fk_farm` FOREIGN KEY (`fid`) REFERENCES `farm` (`fid`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,4,8,'Excellent Products!'),(2,3,4,'Gross :('),(3,1,5,'Love it!'),(4,2,2,'No complaints');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES (1,'Adam','Smith'),(2,'John','Johnson'),(3,'Barack','Obama'),(4,'Robert','Plant'),(5,'Tom','Baker'),(6,'Harry','Jones'),(7,'Andre','Yan'),(8,'Frodo','Bolson'),(9,'Noah','Gordon');
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_to_farm`
--

DROP TABLE IF EXISTS `seller_to_farm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller_to_farm` (
  `sid` int(11) NOT NULL,
  `fid` int(11) NOT NULL,
  PRIMARY KEY (`sid`,`fid`),
  KEY `seller_to_farm_fk_farm` (`fid`),
  CONSTRAINT `seller_to_farm_fk_farm` FOREIGN KEY (`fid`) REFERENCES `farm` (`fid`),
  CONSTRAINT `seller_to_farm_fk_seller` FOREIGN KEY (`sid`) REFERENCES `seller` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_to_farm`
--

LOCK TABLES `seller_to_farm` WRITE;
/*!40000 ALTER TABLE `seller_to_farm` DISABLE KEYS */;
INSERT INTO `seller_to_farm` VALUES (1,1),(2,1),(2,2),(1,3),(3,3),(4,4),(5,4),(6,5),(7,7),(3,8),(8,8),(9,9),(1,10),(3,10);
/*!40000 ALTER TABLE `seller_to_farm` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-04 17:45:47
