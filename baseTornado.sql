CREATE DATABASE  IF NOT EXISTS `basetornado` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `basetornado`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: basetornado
-- ------------------------------------------------------
-- Server version	5.7.13-log

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
-- Table structure for table `arbitros`
--

DROP TABLE IF EXISTS `arbitros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `arbitros` (
  `idArbitro` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  PRIMARY KEY (`idArbitro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `arbitros`
--

LOCK TABLES `arbitros` WRITE;
/*!40000 ALTER TABLE `arbitros` DISABLE KEYS */;
/*!40000 ALTER TABLE `arbitros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `canchas`
--

DROP TABLE IF EXISTS `canchas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `canchas` (
  `idCancha` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCancha`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canchas`
--

LOCK TABLES `canchas` WRITE;
/*!40000 ALTER TABLE `canchas` DISABLE KEYS */;
/*!40000 ALTER TABLE `canchas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipo_jugador`
--

DROP TABLE IF EXISTS `equipo_jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipo_jugador` (
  `nroDocumento` varchar(10) NOT NULL,
  `idEquipo` int(11) NOT NULL,
  PRIMARY KEY (`nroDocumento`,`idEquipo`),
  KEY `FKidEquipo_idx` (`idEquipo`),
  CONSTRAINT `FKidEquipo` FOREIGN KEY (`idEquipo`) REFERENCES `equipos` (`idEquipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKnroDocumento` FOREIGN KEY (`nroDocumento`) REFERENCES `jugadores` (`nroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo_jugador`
--

LOCK TABLES `equipo_jugador` WRITE;
/*!40000 ALTER TABLE `equipo_jugador` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipo_jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipos`
--

DROP TABLE IF EXISTS `equipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipos` (
  `idEquipo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `categoria` int(11) NOT NULL,
  `capitan` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idEquipo`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `FKcapitan_idx` (`capitan`),
  CONSTRAINT `FKcapitan` FOREIGN KEY (`capitan`) REFERENCES `jugadores` (`nroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipos`
--

LOCK TABLES `equipos` WRITE;
/*!40000 ALTER TABLE `equipos` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goles`
--

DROP TABLE IF EXISTS `goles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goles` (
  `nroDocumento` varchar(10) NOT NULL,
  `idPartido` int(11) NOT NULL,
  `cantidad` int(10) unsigned NOT NULL,
  PRIMARY KEY (`nroDocumento`,`idPartido`),
  KEY `FKPGidPartido_idx` (`idPartido`),
  CONSTRAINT `FKJGnroDocumento` FOREIGN KEY (`nroDocumento`) REFERENCES `jugadores` (`nroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKPGidPartido` FOREIGN KEY (`idPartido`) REFERENCES `partidos` (`idPartido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goles`
--

LOCK TABLES `goles` WRITE;
/*!40000 ALTER TABLE `goles` DISABLE KEYS */;
/*!40000 ALTER TABLE `goles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugadores`
--

DROP TABLE IF EXISTS `jugadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jugadores` (
  `nroDocumento` varchar(10) NOT NULL,
  `categoria` int(10) unsigned NOT NULL,
  `mail` varchar(45) NOT NULL,
  `estado` varchar(10) NOT NULL,
  `fechaNacimiento` datetime NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  PRIMARY KEY (`nroDocumento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugadores`
--

LOCK TABLES `jugadores` WRITE;
/*!40000 ALTER TABLE `jugadores` DISABLE KEYS */;
/*!40000 ALTER TABLE `jugadores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partidos`
--

DROP TABLE IF EXISTS `partidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partidos` (
  `idPartido` int(11) NOT NULL,
  `equipoA` int(11) DEFAULT NULL,
  `equipoB` int(11) DEFAULT NULL,
  `arbitro` int(11) DEFAULT NULL,
  `torneo` int(11) DEFAULT NULL,
  `cancha` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `nroFecha` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPartido`),
  KEY `FKequipoA_idx` (`equipoA`),
  KEY `FKequipoB_idx` (`equipoB`),
  KEY `FKarbitro_idx` (`arbitro`),
  KEY `FKtorneo_idx` (`torneo`),
  KEY `FKcancha_idx` (`cancha`),
  CONSTRAINT `FKarbitro` FOREIGN KEY (`arbitro`) REFERENCES `arbitros` (`idArbitro`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKcancha` FOREIGN KEY (`cancha`) REFERENCES `canchas` (`idCancha`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKequipoA` FOREIGN KEY (`equipoA`) REFERENCES `equipos` (`idEquipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKequipoB` FOREIGN KEY (`equipoB`) REFERENCES `equipos` (`idEquipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKtorneo` FOREIGN KEY (`torneo`) REFERENCES `torneos` (`idTorneo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partidos`
--

LOCK TABLES `partidos` WRITE;
/*!40000 ALTER TABLE `partidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `partidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posiciones`
--

DROP TABLE IF EXISTS `posiciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posiciones` (
  `idEquipo` int(11) NOT NULL,
  `idTorneo` int(11) NOT NULL,
  `ganados` int(10) unsigned zerofill NOT NULL,
  `perdidos` int(10) unsigned zerofill NOT NULL,
  `empatados` int(10) unsigned zerofill NOT NULL,
  `golesFavor` int(10) unsigned zerofill NOT NULL,
  `golesContra` int(10) unsigned zerofill NOT NULL,
  `puntos` int(10) unsigned zerofill NOT NULL,
  PRIMARY KEY (`idEquipo`,`idTorneo`),
  KEY `FKTPidTorneo_idx` (`idTorneo`),
  CONSTRAINT `FKEPidEquipo` FOREIGN KEY (`idEquipo`) REFERENCES `equipos` (`idEquipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKTPidTorneo` FOREIGN KEY (`idTorneo`) REFERENCES `torneos` (`idTorneo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posiciones`
--

LOCK TABLES `posiciones` WRITE;
/*!40000 ALTER TABLE `posiciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `posiciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suspensiones`
--

DROP TABLE IF EXISTS `suspensiones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suspensiones` (
  `nroDocumento` varchar(10) NOT NULL,
  `idTorneo` int(11) NOT NULL,
  `nroFecha` int(11) NOT NULL,
  `duracion` int(11) NOT NULL,
  PRIMARY KEY (`nroDocumento`,`idTorneo`,`nroFecha`),
  KEY `FKTSnroFecha_idx` (`idTorneo`),
  CONSTRAINT `FKJSnroDocumento` FOREIGN KEY (`nroDocumento`) REFERENCES `jugadores` (`nroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKTSidTorneo` FOREIGN KEY (`idTorneo`) REFERENCES `torneos` (`idTorneo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suspensiones`
--

LOCK TABLES `suspensiones` WRITE;
/*!40000 ALTER TABLE `suspensiones` DISABLE KEYS */;
/*!40000 ALTER TABLE `suspensiones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjetas`
--

DROP TABLE IF EXISTS `tarjetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjetas` (
  `nroDocumento` varchar(10) NOT NULL,
  `idPartido` int(11) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`nroDocumento`,`tipo`,`idPartido`),
  KEY `FKidPartido_idx` (`idPartido`),
  CONSTRAINT `FKTJnroDocumento` FOREIGN KEY (`nroDocumento`) REFERENCES `jugadores` (`nroDocumento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKTPidPartido` FOREIGN KEY (`idPartido`) REFERENCES `partidos` (`idPartido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjetas`
--

LOCK TABLES `tarjetas` WRITE;
/*!40000 ALTER TABLE `tarjetas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tarjetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `torneos`
--

DROP TABLE IF EXISTS `torneos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `torneos` (
  `idTorneo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `estado` varchar(15) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `categoria` int(11) NOT NULL,
  `inicio` date NOT NULL,
  PRIMARY KEY (`idTorneo`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='    private List<Cancha> canchas;\n    private List<Arbitro> arbitro;\n    private List<Equipo> equipos;\n    private List<Posicion> posiciones;\n    private List<Suspension> suspensiones;';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `torneos`
--

LOCK TABLES `torneos` WRITE;
/*!40000 ALTER TABLE `torneos` DISABLE KEYS */;
/*!40000 ALTER TABLE `torneos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-03 18:51:31
