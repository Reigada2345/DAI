-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_dai
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `autocarros`
--

DROP TABLE IF EXISTS `autocarros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `autocarros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lotacao` int NOT NULL,
  `temperatura` float NOT NULL,
  `numero` varchar(50) NOT NULL,
  `capacidade` int NOT NULL,
  `modelo` varchar(100) NOT NULL,
  `rota` varchar(100) NOT NULL,
  `matricula` varchar(20) NOT NULL,
  `ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `matricula` (`matricula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autocarros`
--

LOCK TABLES `autocarros` WRITE;
/*!40000 ALTER TABLE `autocarros` DISABLE KEYS */;
/*!40000 ALTER TABLE `autocarros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_proprio` varchar(100) NOT NULL,
  `apelido` varchar(100) NOT NULL,
  `contacto` varchar(20) NOT NULL,
  `utilizador_prioritario` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Mariana','Machado','912345678',0,'mariana@gmail.com','1234'),(2,'','','',0,'',''),(5,'chad','a','12345678',0,'a@gmail.com','1234'),(6,'ana','aguiar','6543',0,'afro@gmail.com','afro');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `motoristas`
--

DROP TABLE IF EXISTS `motoristas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `motoristas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_proprio` varchar(100) NOT NULL,
  `apelido` varchar(100) NOT NULL,
  `contacto` varchar(20) DEFAULT NULL,
  `utilizador_prioritario` tinyint(1) DEFAULT '0',
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `criado_em` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motoristas`
--

LOCK TABLES `motoristas` WRITE;
/*!40000 ALTER TABLE `motoristas` DISABLE KEYS */;
/*!40000 ALTER TABLE `motoristas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paragens`
--

DROP TABLE IF EXISTS `stops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stops` (
  `stop_id` varchar(255) NOT NULL, -- GTFS stop_id (pode ser string)
  `stop_code` varchar(50) DEFAULT NULL, -- GTFS stop_code
  `stop_name` varchar(255) NOT NULL, -- GTFS stop_name (mapeado do teu 'nome')
  `stop_desc` varchar(255) DEFAULT NULL, -- GTFS stop_desc
  `stop_lat` decimal(10,8) NOT NULL, -- GTFS stop_lat
  `stop_lon` decimal(11,8) NOT NULL, -- GTFS stop_lon
  `zone_id` varchar(50) DEFAULT NULL, -- GTFS zone_id
  `stop_url` varchar(255) DEFAULT NULL, -- GTFS stop_url
  `location_type` int DEFAULT '0', -- GTFS location_type (0: Stop, 1: Station, etc.)
  `parent_station` varchar(255) DEFAULT NULL, -- GTFS parent_station
  `stop_timezone` varchar(50) DEFAULT NULL, -- GTFS stop_timezone
  `wheelchair_boarding` int DEFAULT '0', -- GTFS wheelchair_boarding (0, 1, 2)
  `varias_rotas` tinyint(1) DEFAULT NULL, -- Mantido do teu esquema original
  `lotacao` int DEFAULT NULL, -- Mantido do teu esquema original
  `ativa` tinyint(1) DEFAULT '1', -- Mantido do teu esquema original
  PRIMARY KEY (`stop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paragens`
--

LOCK TABLES `paragens` WRITE;
/*!40000 ALTER TABLE `paragens` DISABLE KEYS */;
/*!40000 ALTER TABLE `paragens` ENABLE KEYS */;
UNLOCK TABLES;

--DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routes` (
  `route_id` int NOT NULL, -- GTFS route_id (identificador da rota)
  `agency_id` varchar(255) DEFAULT NULL, -- GTFS agency_id
  `route_short_name` varchar(255) NOT NULL, -- GTFS route_short_name
  `route_long_name` varchar(255) DEFAULT NULL, -- GTFS route_long_name
  `route_type` int NOT NULL, -- GTFS route_type (tipo de transporte)
  PRIMARY KEY (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Table structure for table `passageiros`
--

DROP TABLE IF EXISTS `passageiros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passageiros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `idade` int NOT NULL,
  `bilhete` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passageiros`
--

LOCK TABLES `passageiros` WRITE;
/*!40000 ALTER TABLE `passageiros` DISABLE KEYS */;
/*!40000 ALTER TABLE `passageiros` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-25  0:42:06
