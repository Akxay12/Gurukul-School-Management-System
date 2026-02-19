-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: gurukulschool
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `attendance_id` int NOT NULL AUTO_INCREMENT,
  `roll_no` varchar(20) DEFAULT NULL,
  `attendance_date` date DEFAULT NULL,
  `status` varchar(10) DEFAULT 'ABSENT',
  PRIMARY KEY (`attendance_id`),
  UNIQUE KEY `roll_no` (`roll_no`,`attendance_date`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`roll_no`) REFERENCES `students` (`roll_no`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (1,'13','2025-12-07','ABSENT'),(2,'13','2026-01-26','ABSENT'),(3,'13','2025-11-19','ABSENT'),(4,'13','2026-01-01','ABSENT'),(7,'30','2025-12-05','ABSENT'),(8,'30','2026-02-10','ABSENT'),(9,'30','2025-12-26','ABSENT'),(10,'30','2025-12-17','ABSENT'),(11,'30','2025-12-01','ABSENT'),(12,'30','2025-12-22','ABSENT'),(13,'30','2025-11-23','ABSENT'),(14,'30','2025-12-08','ABSENT'),(15,'30','2026-01-29','ABSENT'),(16,'30','2026-01-15','ABSENT'),(17,'30','2026-01-17','ABSENT'),(18,'30','2026-01-16','ABSENT'),(19,'30','2025-12-20','ABSENT'),(20,'30','2026-02-12','ABSENT'),(21,'30','2026-02-01','ABSENT'),(22,'16','2026-02-10','ABSENT'),(23,'16','2026-02-19','ABSENT'),(24,'16','2026-02-08','ABSENT'),(25,'16','2026-02-09','ABSENT'),(26,'16','2026-02-17','ABSENT'),(27,'16','2026-02-06','ABSENT'),(28,'16','2026-02-18','ABSENT'),(29,'16','2026-02-07','ABSENT'),(30,'16','2026-02-15','ABSENT'),(31,'16','2026-02-04','ABSENT'),(32,'16','2026-02-16','ABSENT'),(33,'16','2026-02-05','ABSENT'),(34,'16','2026-02-13','ABSENT'),(35,'16','2026-02-14','ABSENT'),(36,'16','2026-02-11','ABSENT'),(37,'16','2026-02-12','ABSENT');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fees`
--

DROP TABLE IF EXISTS `fees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fees` (
  `fees_id` int NOT NULL AUTO_INCREMENT,
  `roll_no` varchar(50) DEFAULT NULL,
  `total_fees` decimal(10,2) DEFAULT '0.00',
  `fees_paid` decimal(10,2) DEFAULT '0.00',
  `due_date` date DEFAULT NULL,
  PRIMARY KEY (`fees_id`),
  KEY `roll_no` (`roll_no`),
  CONSTRAINT `fees_ibfk_1` FOREIGN KEY (`roll_no`) REFERENCES `students` (`roll_no`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fees`
--

LOCK TABLES `fees` WRITE;
/*!40000 ALTER TABLE `fees` DISABLE KEYS */;
INSERT INTO `fees` VALUES (1,'13',13000.00,8000.00,NULL),(2,'123',13000.00,5000.00,NULL),(3,'13',15000.00,9000.00,NULL),(4,'20',13000.00,7000.00,NULL),(5,'30',8000.00,3000.00,NULL);
/*!40000 ALTER TABLE `fees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notices`
--

DROP TABLE IF EXISTS `notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notices` (
  `notice_id` int NOT NULL AUTO_INCREMENT,
  `image1_path` varchar(500) DEFAULT NULL,
  `image2_path` varchar(500) DEFAULT NULL,
  `message` text,
  `posted_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notices`
--

LOCK TABLES `notices` WRITE;
/*!40000 ALTER TABLE `notices` DISABLE KEYS */;
INSERT INTO `notices` VALUES (1,'','','kal school ko chutti hai','2026-02-19 11:26:59');
/*!40000 ALTER TABLE `notices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `result` (
  `marks_id` int NOT NULL AUTO_INCREMENT,
  `roll_no` int DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `marks_obtained` int DEFAULT NULL,
  `max_marks` int DEFAULT '80',
  PRIMARY KEY (`marks_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `result`
--

LOCK TABLES `result` WRITE;
/*!40000 ALTER TABLE `result` DISABLE KEYS */;
INSERT INTO `result` VALUES (1,13,'marathi',74,80),(2,13,'mathmatics',68,80),(3,123,'maths',43,80),(4,123,'marathi',63,80),(5,123,'geography',71,80),(6,123,'History',65,80),(7,123,'hindi',53,80),(8,123,'hindi',60,80),(9,123,'History',79,80),(10,30,'maths',66,80),(11,30,'science',72,80),(12,30,'general knowledge',52,80),(13,30,'marathhi',60,80),(14,30,'English',56,80);
/*!40000 ALTER TABLE `result` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `roll_no` varchar(20) DEFAULT NULL,
  `class` varchar(10) DEFAULT NULL,
  `dob` varchar(20) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `parent_name` varchar(100) DEFAULT NULL,
  `blood_group` varchar(10) DEFAULT NULL,
  `school_group` varchar(50) DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `roll_no` (`roll_no`),
  UNIQUE KEY `roll_no_2` (`roll_no`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,NULL,NULL,NULL,'akshay ','12','10','12/12/2005','28902','kihxk','nvod','o','sil',NULL),(2,'akshayyyy','akpass','student','akshay','13','10','12/12/2005','565685','kusumba ','vinod','o','silver',NULL),(3,'admin','admin123','admin','System Admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'iamadmin','admin@098','admin','System Admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'mina12','wash','student','mina','123','5','2/4/2005','02899330','ksnknks','aakaash','o','gold',NULL),(9,'abhisk','abhi','student','abhishek','20','9','12/04/2006','92992902','pune','ashok','a','silver',NULL),(10,'nitins','nitin2007','student','nitin suryavanshi','30','8','13-09-2007','568523697','supreme colony','mukesh suryavanshi','A+','gold',NULL),(11,'raghav12','ram','student','raghav','44','10','12-09-2005','56946432','ayodhya nagar','dashrath','A+','diamond','C:\\Users\\akshay vinod patil\\OneDrive\\Desktop\\Attachments\\Shri_Ram_png_4.png'),(13,'sidsh','sid08','student','siddhesh','16','9','15-02-2008','965775325','mehrun','satish tripathi','B+','silver','C:\\Users\\akshay vinod patil\\OneDrive\\Pictures\\Camera Roll\\WIN_20250302_07_26_29_Pro.jpg');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-19 18:25:05
