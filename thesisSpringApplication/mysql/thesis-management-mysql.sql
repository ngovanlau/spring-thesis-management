-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: thesisspringapp
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `committee`
--

DROP TABLE IF EXISTS `committee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `committee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `active` bit(1) DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `committee`
--

LOCK TABLES `committee` WRITE;
/*!40000 ALTER TABLE `committee` DISABLE KEYS */;
INSERT INTO `committee` VALUES (2,'Hội đồng A',_binary ''),(4,'Hội đồng C',_binary '\0'),(6,'Hội đồng E',_binary '');
/*!40000 ALTER TABLE `committee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `committee_user`
--

DROP TABLE IF EXISTS `committee_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `committee_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(50) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `committee_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_COMMITTEEUSER` (`user_id`),
  KEY `FK_COMMITTEE_COMMITTEEUSER` (`committee_id`),
  CONSTRAINT `FK_COMMITTEE_COMMITTEEUSER` FOREIGN KEY (`committee_id`) REFERENCES `committee` (`id`),
  CONSTRAINT `FK_USER_COMMITTEEUSER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `committee_user`
--

LOCK TABLES `committee_user` WRITE;
/*!40000 ALTER TABLE `committee_user` DISABLE KEYS */;
INSERT INTO `committee_user` VALUES (5,'Chủ tịch',5,2),(6,'Thư kí',8,2),(7,'Phản biện',6,2),(8,'Thành viên',7,2),(13,'Chủ tịch',8,4),(14,'Thư kí',5,4),(15,'Phản biện',6,4),(19,'Chủ tịch',6,6),(20,'Thư kí',7,6),(21,'Phản biện',5,6),(22,'Thành viên',8,6);
/*!40000 ALTER TABLE `committee_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `criteria`
--

DROP TABLE IF EXISTS `criteria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `criteria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `active` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `criteria`
--

LOCK TABLES `criteria` WRITE;
/*!40000 ALTER TABLE `criteria` DISABLE KEYS */;
INSERT INTO `criteria` VALUES (1,'Trình bày',_binary ''),(2,'Kết cấu báo cáo',_binary ''),(3,'Văn phong',_binary ''),(4,'Kỹ năng phân tích',_binary ''),(5,'Mục tiêu',_binary ''),(6,'Kết quả',_binary ''),(7,'Ý nghĩa đề tài',_binary ''),(8,'Thái độ của sinh viên',_binary ''),(9,'Trả lời câu hỏi',_binary '');
/*!40000 ALTER TABLE `criteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (6,'Cơ khí'),(1,'Công nghệ thông tin'),(7,'Điện điện tử'),(5,'Hệ thống thông tin'),(4,'Khoa học máy tính'),(3,'Không có'),(2,'Xã hội học');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `otp_code` varchar(255) NOT NULL,
  `expiry_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `otp_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp`
--

LOCK TABLES `otp` WRITE;
/*!40000 ALTER TABLE `otp` DISABLE KEYS */;
INSERT INTO `otp` VALUES (3,1,'$2a$10$Z5PuPqmHRa7o8i81WyWg7OtN.GJwrWy69rT7bTFPMfHcXbPUsR3ZG','2024-06-20 09:48:15'),(4,11,'$2a$10$f8QQS6NsRJIiLIgdP1nAte.nrfXdo7ihHnGV7DlAXxGL4qCO20s3e','2024-06-20 17:41:27');
/*!40000 ALTER TABLE `otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentvnpaydetail`
--

DROP TABLE IF EXISTS `paymentvnpaydetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paymentvnpaydetail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(20) NOT NULL,
  `amount` float NOT NULL,
  `order_desc` varchar(255) DEFAULT NULL,
  `vnp_TransactionNo` varchar(255) DEFAULT NULL,
  `vnp_ResponseCode` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_VNPAY_USER` (`user_id`),
  CONSTRAINT `FK_VNPAY_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentvnpaydetail`
--

LOCK TABLES `paymentvnpaydetail` WRITE;
/*!40000 ALTER TABLE `paymentvnpaydetail` DISABLE KEYS */;
INSERT INTO `paymentvnpaydetail` VALUES (1,'66361340',10000,'Thanh toán phí download file PDF khóa luận có mã 1 của sinh vien THESISSINHVIEN2151050249','14438187','00',2),(2,'72014876',10000,'Thanh toán phí download file PDF khóa luận có mã 1 của sinh vien THESISSINHVIEN2151050249','14438258','00',2),(3,'04573327',10000,'Thanh toán phí download file PDF khóa luận có mã 1 của sinh vien THESISSINHVIEN2151050249','14439142','00',2),(4,'43754548',10000,'Thanh toán phí download file PDF khóa luận có mã 2 của sinh vien THESISSINHVIEN2161029374','14469919','00',4),(5,'47337129',10000,'Thanh toán phí download file PDF khóa luận có mã 2 của sinh vien THESISSINHVIEN2151050249','14469957','00',2),(6,'12194302',10000,'Thanh toán phí download file PDF khóa luận có mã 3 của sinh vien THESISSINHVIEN2151050249','14470242','00',2),(7,'68276817',10000,'Thanh toán phí download file PDF khóa luận có mã 3 của sinh vien THESISSINHVIEN2151050249','14470247','00',2),(8,'79955640',10000,'Thanh toán phí download file PDF khóa luận có mã 4 của sinh vien THESISSINHVIEN2192026548','14470943','00',15);
/*!40000 ALTER TABLE `paymentvnpaydetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(3,'ROLE_GIANGVIEN'),(4,'ROLE_GIAOVU'),(2,'ROLE_SINHVIEN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `score` float NOT NULL,
  `thesis_id` int DEFAULT NULL,
  `criteria_id` int DEFAULT NULL,
  `committee_user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_THESIS_SCORE` (`thesis_id`),
  KEY `FK_CRITERIA_SCORE` (`criteria_id`),
  KEY `FK_COMMITTEEUSER_SCORE` (`committee_user_id`),
  CONSTRAINT `FK_COMMITTEEUSER_SCORE` FOREIGN KEY (`committee_user_id`) REFERENCES `committee_user` (`id`),
  CONSTRAINT `FK_CRITERIA_SCORE` FOREIGN KEY (`criteria_id`) REFERENCES `criteria` (`id`),
  CONSTRAINT `FK_THESIS_SCORE` FOREIGN KEY (`thesis_id`) REFERENCES `thesis` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (73,8,3,1,5),(74,7,3,2,5),(75,7,3,3,5),(76,7,3,4,5),(77,9,3,5,5),(78,7,3,6,5),(79,8,3,7,5),(80,7,3,8,5),(81,9.6,3,9,5),(82,7.6,3,1,6),(83,8.6,3,2,6),(84,9.7,3,3,6),(85,7.8,3,4,6),(86,7.9,3,5,6),(87,9.7,3,6,6),(88,8.3,3,7,6),(89,6.8,3,8,6),(90,9.6,3,9,6),(91,7.8,3,1,7),(92,8.9,3,2,7),(93,9.6,3,3,7),(94,8,3,4,7),(95,9,3,5,7),(96,10,3,6,7),(97,6,3,7,7),(98,7.8,3,8,7),(99,6.5,3,9,7),(100,7,3,1,8),(101,8,3,2,8),(102,9,3,3,8),(103,10,3,4,8),(104,8,3,5,8),(105,7,3,6,8),(106,6,3,7,8),(107,8,3,8,8),(108,7,3,9,8),(109,7,4,1,14),(110,8,4,2,14),(111,6.7,4,3,14),(112,8.5,4,4,14),(113,7.8,4,5,14),(114,8.6,4,6,14),(115,9.7,4,7,14),(116,7,4,8,14),(117,8.9,4,9,14),(118,9.5,4,1,15),(119,5.8,4,2,15),(120,6.8,4,3,15),(121,8.5,4,4,15),(122,8.9,4,5,15),(123,9.3,4,6,15),(124,8.7,4,7,15),(125,6,4,8,15),(126,7,4,9,15),(127,9,4,1,13),(128,9,4,2,13),(129,8,4,3,13),(130,8,4,4,13),(131,7,4,5,13),(132,7,4,6,13),(133,7,4,7,13),(134,7,4,8,13),(135,7,4,9,13);
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thesis`
--

DROP TABLE IF EXISTS `thesis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thesis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `score` float DEFAULT NULL,
  `active` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thesis`
--

LOCK TABLES `thesis` WRITE;
/*!40000 ALTER TABLE `thesis` DISABLE KEYS */;
INSERT INTO `thesis` VALUES (3,'Khóa luận A','2024-06-20 22:20:59','2024-06-20 22:26:24',8.03333,_binary ''),(4,'Thị giác máy tính','2024-06-21 10:45:03','2024-06-22 23:25:25',7.85,_binary '\0'),(5,'Đối ngoại quốc tế','2024-06-21 12:45:51','2024-06-21 12:46:01',NULL,_binary '');
/*!40000 ALTER TABLE `thesis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thesis_committee_rate`
--

DROP TABLE IF EXISTS `thesis_committee_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thesis_committee_rate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `thesis_id` int DEFAULT NULL,
  `committee_id` int DEFAULT NULL,
  `status_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_THESIS_THESISCOMMITTEERATE` (`thesis_id`),
  KEY `FK_COMMITTEE_THESISCOMMITTEERATE` (`committee_id`),
  KEY `FK_STATUS_THESISCOMMITTEERATE` (`status_id`),
  CONSTRAINT `FK_COMMITTEE_THESISCOMMITTEERATE` FOREIGN KEY (`committee_id`) REFERENCES `committee` (`id`),
  CONSTRAINT `FK_STATUS_THESISCOMMITTEERATE` FOREIGN KEY (`status_id`) REFERENCES `thesis_status` (`id`),
  CONSTRAINT `FK_THESIS_THESISCOMMITTEERATE` FOREIGN KEY (`thesis_id`) REFERENCES `thesis` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thesis_committee_rate`
--

LOCK TABLES `thesis_committee_rate` WRITE;
/*!40000 ALTER TABLE `thesis_committee_rate` DISABLE KEYS */;
INSERT INTO `thesis_committee_rate` VALUES (3,3,2,2),(4,4,4,3),(5,5,2,2);
/*!40000 ALTER TABLE `thesis_committee_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thesis_status`
--

DROP TABLE IF EXISTS `thesis_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thesis_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status_name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thesis_status`
--

LOCK TABLES `thesis_status` WRITE;
/*!40000 ALTER TABLE `thesis_status` DISABLE KEYS */;
INSERT INTO `thesis_status` VALUES (1,'Đang phân công'),(2,'Đang chấm điểm'),(3,'Đã chấm xong');
/*!40000 ALTER TABLE `thesis_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thesis_user`
--

DROP TABLE IF EXISTS `thesis_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thesis_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `thesis_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_THESIS_THESISUSER` (`thesis_id`),
  KEY `FK_USER_THESISUSER` (`user_id`),
  CONSTRAINT `FK_THESIS_THESISUSER` FOREIGN KEY (`thesis_id`) REFERENCES `thesis` (`id`),
  CONSTRAINT `FK_USER_THESISUSER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thesis_user`
--

LOCK TABLES `thesis_user` WRITE;
/*!40000 ALTER TABLE `thesis_user` DISABLE KEYS */;
INSERT INTO `thesis_user` VALUES (9,3,6),(10,3,7),(11,3,4),(12,3,2),(13,4,7),(14,4,8),(15,4,15),(16,4,16),(17,5,18),(18,5,7),(19,5,9),(20,5,19);
/*!40000 ALTER TABLE `thesis_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `useruniversityid` varchar(10) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstName` varchar(40) DEFAULT NULL,
  `lastName` varchar(40) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `birthday` datetime NOT NULL,
  `active` bit(1) DEFAULT b'0',
  `role_id` int NOT NULL,
  `faculty_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `useruniversityid` (`useruniversityid`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  KEY `FK_ROLE_USER` (`role_id`),
  KEY `FK_FACULTY_USER` (`faculty_id`),
  CONSTRAINT `FK_FACULTY_USER` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`),
  CONSTRAINT `FK_ROLE_USER` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'https://res.cloudinary.com/diwxda8bi/image/upload/v1716013417/dyesgkd1imvrj5naunva.jpg','1729379012','THESISQUANTRI1729379012','$2a$10$PUKpHjRi9CukPMQ7UI/79.7b.VRvbsH6KPQAUXwh8iVPT5c6.A7WS','admin','','male','admin@gmail.com','0918293763','1999-11-24 00:00:00',_binary '',1,1),(2,'https://res.cloudinary.com/diwxda8bi/image/upload/v1717128524/lpmoea8sc1ngcaevkvro.png','2151050249','THESISSINHVIEN2151050249','$2a$10$dDXr48x0dmEMSott7o.AzeIZZrllnbyTmN.h3/GfysejPsIciaewW','Mạnh','Nguyễn Đức','male','2151050249manh@ou.edu.vn','0918298922','2003-07-10 00:00:00',_binary '',2,1),(3,'https://res.cloudinary.com/diwxda8bi/image/upload/v1719298765/frepgnrddju4kgybszwc.png','1729300273','THESISGIAOVU1729300273','$2a$10$u4jNBvABCgh18KWTpNlPqubhAiL7H9sjCN1wogZX9...RVDK73b.S','Vụ','Giáo','male','giaovu1@gmail.com','0192836521','1994-11-04 00:00:00',_binary '',4,3),(4,'https://res.cloudinary.com/diwxda8bi/image/upload/v1717129313/yqrol4mtcq539fc55i3x.png','2161029374','THESISSINHVIEN2161029374','$2a$10$.60w.wQp3cfzR.SUt.8T1.1y7h4Mg/5688sc.juwuha95BNLhy.da','Lâu','Nguyễn Văn','male','ducmanhnguyen0710@gmail.com','0926371522','2003-04-14 00:00:00',_binary '',2,1),(5,'https://res.cloudinary.com/diwxda8bi/image/upload/v1717134368/tbjwytrfgcqiaqf2tbt7.png','1927456323','THESISGIANGVIEN1927456323','$2a$10$.0iR0Kvg4ZUEy3NQt00ZL.hg2D25eb0vhXGyMzM6ECPJ/NxLNo8TK','Viên 1','Giảng','male','manhwuday@gmail.com','0918264324','1992-11-03 00:00:00',_binary '',3,1),(6,'https://res.cloudinary.com/diwxda8bi/image/upload/v1717134492/jq1wlkcdqnafkeigvbpj.png','1829304753','THESISGIANGVIEN182930475','$2a$10$N7wwEd.jX410ctBlsdhAW.txH1AJqcUhEbEoOdBsakFDrpVcUu4nG','Viên 2','Giảng','male','daviddegea0710@gmail.com','0928372172','1997-03-10 00:00:00',_binary '',3,1),(7,'https://res.cloudinary.com/diwxda8bi/image/upload/v1717134849/gzkzreik5gdbjqqmuvvn.png','1728364512','THESISGIANGVIEN1728364512','$2a$10$Ae24V6DaZz9crzIHcpbLTuncwloP270DkZh9U.hMRq/tqlQ05AM6y','Viên 3','Giảng','male','gv3@gmail.com','0162839122','1997-02-04 00:00:00',_binary '',3,1),(8,'https://res.cloudinary.com/diwxda8bi/image/upload/v1717137677/oebxhmqjxcbmju7vav6l.png','1829364665','THESISGIANGVIEN1829364665','$2a$10$QQPiw2SBxzwfRY1XS3/zh.lp7DvX3AL3jYDlGwaiwaIirogD9oz/W','Viên 4','Giảng','male','gv4@gmail.com','0927365263','1999-07-31 00:00:00',_binary '',3,1),(9,'https://res.cloudinary.com/diwxda8bi/image/upload/v1718880098/pc1ivinibmidnceivxrp.png','2161024859','THESISSINHVIEN2161024859','$2a$10$odviDVqfPOg3I4eouAtRGel2Eq75BQwYIaj/NwKsWqqauNybIc39.','A','Nguyen Van','male','a@gmail.com','0928394758','2003-02-03 00:00:00',_binary '',2,2),(11,'https://res.cloudinary.com/diwxda8bi/image/upload/v1718880039/swjajb0j4uxqht5ahpe0.png','2181728364','THESISSINHVIEN2181728364','$2a$10$zHxblB0qHo7Cxi07EfZcHutxxUqU/T00UdiZg0iB9meQnDerIBENu','Toan','Le Van','male','81223wigw@gmail.com','0928273831','2004-05-10 00:00:00',_binary '',2,1),(15,'https://res.cloudinary.com/diwxda8bi/image/upload/v1718941150/g2j8fm0lx4ddojtu1rbh.png','2192026548','THESISSINHVIEN2192026548','$2a$10$c1dUQElCtHJb50swBy.zfu1B/Qe98t.FBgJ35K0EXVBTWDEnRzjb2','Nghia','Nguyen Anh','male','wfjk@gmail.com','0273893736','2004-07-07 00:00:00',_binary '',2,4),(16,'https://res.cloudinary.com/diwxda8bi/image/upload/v1718941359/qplhkficolz4pi5gvxmi.png','2182930223','THESISSINHVIEN2182930223','$2a$10$DyoGnM9evbUjWxMaweh6.e710ku7YyCLGgLvN7nA5ZbRuhHd4/atC','Chi','Dang Thanh','male','wkjfbow@gmail.com','0928273922','2002-06-05 00:00:00',_binary '',2,5),(17,'https://res.cloudinary.com/diwxda8bi/image/upload/v1713088939/yyplfcrhzurbla0t8vy4.jpg','2182930374','THESISSINHVIEN2182930374','$2a$10$zHxblB0qHo7Cxi07EfZcHutxxUqU/T00UdiZg0iB9meQnDerIBENu','Khoa','Le Dang','male','dkwk@gmail.com','0928200182','2002-07-10 00:00:00',_binary '\0',2,7),(18,'https://res.cloudinary.com/diwxda8bi/image/upload/v1713088939/yyplfcrhzurbla0t8vy4.jpg','2171829304','THESISGIANGVIEN2171829304','$2a$10$PUKpHjRi9CukPMQ7UI/79.7b.VRvbsH6KPQAUXwh8iVPT5c6.A7WS','Lau',' Ngo Van','male','ngovanlau2003@gmail.com','0918266271','1989-03-08 00:00:00',_binary '\0',3,4),(19,'https://res.cloudinary.com/diwxda8bi/image/upload/v1713088939/yyplfcrhzurbla0t8vy4.jpg','0192837483','THESISSINHVIEN0192837483','$2a$10$PUKpHjRi9CukPMQ7UI/79.7b.VRvbsH6KPQAUXwh8iVPT5c6.A7WS','B','Nguyen Van','male','vanlau.luzy@gmail.com','0827292830','2004-02-18 00:00:00',_binary '\0',2,7),(20,'https://res.cloudinary.com/diwxda8bi/image/upload/v1713088939/yyplfcrhzurbla0t8vy4.jpg','2029285647','THESISSINHVIEN2029285647','$2a$10$PUKpHjRi9CukPMQ7UI/79.7b.VRvbsH6KPQAUXwh8iVPT5c6.A7WS','Lau','Ngo Van','male','2151053034lau@ou.edu.vn','0918273900','2003-07-24 00:00:00',_binary '\0',2,5);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-25 20:17:58
