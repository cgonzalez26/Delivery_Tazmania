CREATE DATABASE  IF NOT EXISTS `delivery_tazmania` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `delivery_tazmania`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: delivery_tazmania
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.19-MariaDB

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
-- Table structure for table `asistencias`
--

DROP TABLE IF EXISTS `asistencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asistencias` (
  `idasistencia` int(11) NOT NULL AUTO_INCREMENT,
  `idempleado` int(11) NOT NULL,
  `fecha_asistencia` datetime NOT NULL,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idasistencia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistencias`
--

LOCK TABLES `asistencias` WRITE;
/*!40000 ALTER TABLE `asistencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `asistencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caja`
--

DROP TABLE IF EXISTS `caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caja` (
  `idcaja` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `fecha_apertura` datetime NOT NULL,
  `fecha_cierre` datetime NOT NULL,
  `monto` float(9,2) DEFAULT 0.00,
  `estado` varchar(10) DEFAULT '',
  `fecha_registro` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'1',
  PRIMARY KEY (`idcaja`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caja`
--

LOCK TABLES `caja` WRITE;
/*!40000 ALTER TABLE `caja` DISABLE KEYS */;
INSERT INTO `caja` VALUES (1,1,'2023-01-09 10:28:41','2023-01-09 10:28:41',0.00,'','2023-01-09 10:28:41',_binary ''),(2,4,'2023-01-09 10:31:12','2023-01-09 00:26:09',10000.00,'CERRADA','2023-01-09 10:31:39',_binary ''),(3,4,'2023-01-11 09:15:24','2023-01-11 09:19:05',10000.00,'ABIERTA','2023-01-11 09:19:05',_binary '');
/*!40000 ALTER TABLE `caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caja_turno`
--

DROP TABLE IF EXISTS `caja_turno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caja_turno` (
  `idcajaturno` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `idcaja` int(11) NOT NULL,
  `idturno` int(11) NOT NULL,
  `fecha_apertura` datetime NOT NULL,
  `fecha_cierre` datetime NOT NULL,
  `monto` float(9,2) DEFAULT 0.00,
  `estado` varchar(10) DEFAULT '',
  `fecha_registro` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'1',
  PRIMARY KEY (`idcajaturno`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caja_turno`
--

LOCK TABLES `caja_turno` WRITE;
/*!40000 ALTER TABLE `caja_turno` DISABLE KEYS */;
INSERT INTO `caja_turno` VALUES (97,1,1,1,'2023-01-09 10:11:40','2023-01-09 10:11:40',0.00,'','2023-01-09 10:11:40',_binary ''),(98,4,2,1,'2023-01-09 10:31:12','2023-01-09 00:26:09',10000.00,'CERRADA','2023-01-09 10:31:39',_binary ''),(99,4,3,1,'2023-01-11 09:15:24','2023-01-11 09:15:24',10000.00,'ABIERTA','2023-01-11 09:19:05',_binary '');
/*!40000 ALTER TABLE `caja_turno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoriasproductos`
--

DROP TABLE IF EXISTS `categoriasproductos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoriasproductos` (
  `idcategoriaproducto` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idcategoriaproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriasproductos`
--

LOCK TABLES `categoriasproductos` WRITE;
/*!40000 ALTER TABLE `categoriasproductos` DISABLE KEYS */;
INSERT INTO `categoriasproductos` VALUES (1,'Minutas',_binary ''),(2,'Pizzas',_binary '');
/*!40000 ALTER TABLE `categoriasproductos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) DEFAULT '-',
  `domicilio` varchar(300) DEFAULT '-',
  `telefono` varchar(50) DEFAULT '-',
  `email` varchar(80) DEFAULT '-',
  `activo` bit(1) DEFAULT b'1',
  `dni` varchar(50) NOT NULL,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (3,'Jose','Gutierrez','Los Maizales 210 Barrio Miguel Ortiz','387458964','jgutierrez@gmail.com',_binary '','27854321'),(4,'Pedro','Gonzalez','Sarmiento 1600, Salta Capital','3874546562','pgonzalez@gmail.com',_binary '','25979864');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras` (
  `idcompra` int(11) NOT NULL AUTO_INCREMENT,
  `idproveedor` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `NroCompra` varchar(100) DEFAULT NULL,
  `FechaCompra` datetime DEFAULT NULL,
  `MontoTotal` float(9,2) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idcompra`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
INSERT INTO `compras` VALUES (9,2,4,'CP_0000000000','2023-01-11 12:42:00',3550.00,_binary '');
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumosempleados`
--

DROP TABLE IF EXISTS `consumosempleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consumosempleados` (
  `idconsumo` int(11) NOT NULL AUTO_INCREMENT,
  `idproducto` int(11) NOT NULL,
  `nombreEmp` varchar(80) NOT NULL,
  `producto` varchar(80) NOT NULL,
  `cantidad` float(9,2) NOT NULL,
  `fecha` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idconsumo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumosempleados`
--

LOCK TABLES `consumosempleados` WRITE;
/*!40000 ALTER TABLE `consumosempleados` DISABLE KEYS */;
/*!40000 ALTER TABLE `consumosempleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallescompras`
--

DROP TABLE IF EXISTS `detallescompras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detallescompras` (
  `iddetallecompra` int(11) NOT NULL AUTO_INCREMENT,
  `idcompra` int(11) NOT NULL,
  `idinsumo` int(11) NOT NULL,
  `Precio` float(9,2) DEFAULT NULL,
  `Cantidad` float(9,2) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`iddetallecompra`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallescompras`
--

LOCK TABLES `detallescompras` WRITE;
/*!40000 ALTER TABLE `detallescompras` DISABLE KEYS */;
/*!40000 ALTER TABLE `detallescompras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallesventas`
--

DROP TABLE IF EXISTS `detallesventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detallesventas` (
  `iddetalleventa` int(11) NOT NULL AUTO_INCREMENT,
  `idventa` int(11) NOT NULL,
  `idproducto` int(11) NOT NULL,
  `Precio` float(9,2) DEFAULT NULL,
  `Cantidad` float(9,2) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`iddetalleventa`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallesventas`
--

LOCK TABLES `detallesventas` WRITE;
/*!40000 ALTER TABLE `detallesventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `detallesventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `egresos`
--

DROP TABLE IF EXISTS `egresos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `egresos` (
  `idegreso` int(11) NOT NULL AUTO_INCREMENT,
  `idtipoegreso` int(11) NOT NULL,
  `NroEgreso` varchar(100) DEFAULT NULL,
  `fecha_egreso` datetime NOT NULL,
  `descripcion` varchar(500) NOT NULL DEFAULT '',
  `detalle` varchar(8000) NOT NULL DEFAULT '',
  `monto` float(9,2) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idegreso`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `egresos`
--

LOCK TABLES `egresos` WRITE;
/*!40000 ALTER TABLE `egresos` DISABLE KEYS */;
/*!40000 ALTER TABLE `egresos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados` (
  `idempleado` int(11) NOT NULL AUTO_INCREMENT,
  `idtipoempleado` int(11) DEFAULT NULL,
  `idtipodocumento` int(11) DEFAULT NULL,
  `NroDocumento` varchar(100) DEFAULT NULL,
  `Nombre` varchar(100) DEFAULT NULL,
  `Apellido` varchar(100) DEFAULT NULL,
  `direccion` varchar(500) DEFAULT NULL,
  `Telefono` varchar(200) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  `SueldoBasico` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idempleado`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES (4,3,1,'30842174','Andres','Funes','Pueyrredon 2500','3874545116',_binary '',NULL),(5,1,1,'26845646','Julio','Aguilar','Calle 17 de Junio, Nº850, Ciudad del Milagro','4257897',_binary '',NULL);
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formaspagos`
--

DROP TABLE IF EXISTS `formaspagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `formaspagos` (
  `idformapago` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idformapago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formaspagos`
--

LOCK TABLES `formaspagos` WRITE;
/*!40000 ALTER TABLE `formaspagos` DISABLE KEYS */;
/*!40000 ALTER TABLE `formaspagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insumos`
--

DROP TABLE IF EXISTS `insumos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insumos` (
  `idinsumo` int(11) NOT NULL AUTO_INCREMENT,
  `idtipoinsumo` int(11) NOT NULL,
  `idproveedor` int(11) NOT NULL,
  `descripcion` varchar(500) NOT NULL DEFAULT '',
  `precio` float(9,2) DEFAULT NULL,
  `stock` float(9,2) DEFAULT NULL,
  `fecharegistro` datetime DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  `idunidadmedida` int(11) DEFAULT NULL,
  PRIMARY KEY (`idinsumo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insumos`
--

LOCK TABLES `insumos` WRITE;
/*!40000 ALTER TABLE `insumos` DISABLE KEYS */;
INSERT INTO `insumos` VALUES (1,1,1,'Carne Milanesa',200.00,5.00,'2020-11-27 02:31:00',_binary '',2),(2,1,1,'Carne Lomito',300.00,4.00,'2020-11-27 02:31:00',_binary '',2),(3,2,2,'Pan Milanesa',130.00,5.00,'2020-11-27 02:31:00',_binary '',1),(4,2,2,'Pan Lomito',150.00,9.00,'2020-11-27 02:31:00',_binary '',1);
/*!40000 ALTER TABLE `insumos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mercadopago`
--

DROP TABLE IF EXISTS `mercadopago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mercadopago` (
  `idmercadopago` int(11) NOT NULL AUTO_INCREMENT,
  `idcliente` int(11) NOT NULL,
  `nroOperacion` varchar(200) NOT NULL DEFAULT '',
  `importe` float(9,2) NOT NULL DEFAULT 0.00,
  `descripcion` varchar(255) DEFAULT '-',
  `fecha` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'0',
  `abonoCliente` float DEFAULT NULL,
  PRIMARY KEY (`idmercadopago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mercadopago`
--

LOCK TABLES `mercadopago` WRITE;
/*!40000 ALTER TABLE `mercadopago` DISABLE KEYS */;
/*!40000 ALTER TABLE `mercadopago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimientos_caja`
--

DROP TABLE IF EXISTS `movimientos_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimientos_caja` (
  `idmovimientocaja` int(11) NOT NULL AUTO_INCREMENT,
  `idtipomovimiento` int(11) NOT NULL,
  `idcajaturno` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `NroMovimiento` varchar(100) DEFAULT NULL,
  `fecha_movimiento` datetime NOT NULL,
  `monto` float(9,2) DEFAULT 0.00,
  `fecha_registro` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'1',
  `idmovimiento` int(11) DEFAULT 0,
  `detalle` varchar(8000) DEFAULT NULL,
  `descripcion` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`idmovimientocaja`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimientos_caja`
--

LOCK TABLES `movimientos_caja` WRITE;
/*!40000 ALTER TABLE `movimientos_caja` DISABLE KEYS */;
INSERT INTO `movimientos_caja` VALUES (90,1,98,4,'MC_0000000090','2023-01-09 10:31:12',10000.00,'2023-01-09 10:31:39',_binary '',0,NULL,'APERTURA DE CAJA'),(91,3,98,4,'MC_0000000091','2023-01-09 00:26:09',10000.00,'2023-01-09 12:26:21',_binary '',0,NULL,'CIERRE DE CAJA');
/*!40000 ALTER TABLE `movimientos_caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pantallas`
--

DROP TABLE IF EXISTS `pantallas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pantallas` (
  `idpantalla` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idpantalla`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pantallas`
--

LOCK TABLES `pantallas` WRITE;
/*!40000 ALTER TABLE `pantallas` DISABLE KEYS */;
INSERT INTO `pantallas` VALUES (11,'Compras',_binary ''),(12,'Proveedores',_binary ''),(13,'Ventas',_binary ''),(14,'Productos',_binary ''),(15,'Insumos',_binary ''),(16,'Empleados',_binary ''),(17,'Reportes',_binary ''),(18,'Gastos',_binary ''),(19,'Caja',_binary ''),(20,'Administrar',_binary '');
/*!40000 ALTER TABLE `pantallas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisospantallasperfiles`
--

DROP TABLE IF EXISTS `permisospantallasperfiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisospantallasperfiles` (
  `idpermisopantallaperfil` int(11) NOT NULL AUTO_INCREMENT,
  `idtipousuario` int(11) NOT NULL,
  `idpantalla` int(11) NOT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idpermisopantallaperfil`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisospantallasperfiles`
--

LOCK TABLES `permisospantallasperfiles` WRITE;
/*!40000 ALTER TABLE `permisospantallasperfiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisospantallasperfiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personas` (
  `ID_PERSONA` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_PERSONA` varchar(100) NOT NULL DEFAULT '',
  `APELLIDO_PERSONA` varchar(100) NOT NULL DEFAULT '',
  `DOMICILIO_PERSONA` varchar(100) NOT NULL DEFAULT '',
  `TELCONTACTO_PERSONA` varchar(100) DEFAULT '-',
  `EMAILCONTACTO_PERSONA` varchar(100) DEFAULT '-',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`ID_PERSONA`),
  UNIQUE KEY `ID_PERSONA` (`ID_PERSONA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personas`
--

LOCK TABLES `personas` WRITE;
/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `idproducto` int(11) NOT NULL AUTO_INCREMENT,
  `idcategoriaproducto` int(11) NOT NULL,
  `descripcion` varchar(500) NOT NULL DEFAULT '',
  `precioventa` float(9,2) DEFAULT NULL,
  `fecharegistro` datetime DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,1,'Sandwich Lomito',300.00,'0000-00-00 00:00:00',_binary ''),(2,1,'Sandwich Milanesa',250.00,'2019-12-02 05:48:00',_binary '');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedores` (
  `idproveedor` int(11) NOT NULL AUTO_INCREMENT,
  `idtipodocumento` int(11) DEFAULT NULL,
  `NroDocumento` varchar(100) DEFAULT NULL,
  `Nombre` varchar(100) DEFAULT NULL,
  `Apellido` varchar(100) DEFAULT NULL,
  `Nombre_comercial` varchar(255) DEFAULT NULL,
  `direccion` varchar(500) DEFAULT NULL,
  `Telefono` varchar(200) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idproveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,1,'-','Pablo','Perez','Carniceria TOKO','-','-',_binary ''),(2,1,'-','Jose Maria','Larrondo','Panaderia El Milagro','-','-',_binary ''),(4,1,'29584826','Roberto','Lopez','El Emporio De Los Quesos','Av. San Martín 784, Salta','03874654631',_binary '');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recetas`
--

DROP TABLE IF EXISTS `recetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recetas` (
  `idreceta` int(11) NOT NULL AUTO_INCREMENT,
  `idproducto` int(11) DEFAULT NULL,
  `idinsumo` int(11) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idreceta`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recetas`
--

LOCK TABLES `recetas` WRITE;
/*!40000 ALTER TABLE `recetas` DISABLE KEYS */;
INSERT INTO `recetas` VALUES (1,1,2,_binary ''),(2,1,4,_binary ''),(3,2,1,_binary ''),(4,2,3,_binary '');
/*!40000 ALTER TABLE `recetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposdocumentos`
--

DROP TABLE IF EXISTS `tiposdocumentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposdocumentos` (
  `idtipodocumento` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(100) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtipodocumento`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposdocumentos`
--

LOCK TABLES `tiposdocumentos` WRITE;
/*!40000 ALTER TABLE `tiposdocumentos` DISABLE KEYS */;
INSERT INTO `tiposdocumentos` VALUES (1,'DNI',_binary '');
/*!40000 ALTER TABLE `tiposdocumentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposempleados`
--

DROP TABLE IF EXISTS `tiposempleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposempleados` (
  `idtipoempleado` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  `Sueldo` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idtipoempleado`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposempleados`
--

LOCK TABLES `tiposempleados` WRITE;
/*!40000 ALTER TABLE `tiposempleados` DISABLE KEYS */;
INSERT INTO `tiposempleados` VALUES (1,'Pizzero',_binary '',NULL),(2,'Sandwichero',_binary '',NULL),(3,'Cadete',_binary '',NULL);
/*!40000 ALTER TABLE `tiposempleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposgastos`
--

DROP TABLE IF EXISTS `tiposgastos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposgastos` (
  `idtipogasto` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtipogasto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposgastos`
--

LOCK TABLES `tiposgastos` WRITE;
/*!40000 ALTER TABLE `tiposgastos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tiposgastos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposinsumos`
--

DROP TABLE IF EXISTS `tiposinsumos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposinsumos` (
  `idtipoinsumo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtipoinsumo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposinsumos`
--

LOCK TABLES `tiposinsumos` WRITE;
/*!40000 ALTER TABLE `tiposinsumos` DISABLE KEYS */;
INSERT INTO `tiposinsumos` VALUES (1,'Carnes',_binary ''),(2,'Panes',_binary '');
/*!40000 ALTER TABLE `tiposinsumos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposmovimientos`
--

DROP TABLE IF EXISTS `tiposmovimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposmovimientos` (
  `idtipomovimiento` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'1',
  `tipo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idtipomovimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposmovimientos`
--

LOCK TABLES `tiposmovimientos` WRITE;
/*!40000 ALTER TABLE `tiposmovimientos` DISABLE KEYS */;
INSERT INTO `tiposmovimientos` VALUES (1,'APERTURA DE CAJA EFECTIVO',_binary '','INGRESO'),(2,'CIERRE DE TURNO',_binary '','INGRESO'),(3,'CIERRE DE CAJA DIARIO',_binary '','EGRESO'),(4,'PAGO DE ALQUILER',_binary '','EGRESO'),(5,'PAGO DE IMPUESTO-LUZ',_binary '','EGRESO'),(6,'PAGO DE IMPUESTO-GAS',_binary '','EGRESO'),(7,'INGRESO DE EFECTIVO A CAJA',_binary '','INGRESO'),(8,'SALIDA DE EFECTIVO EN CAJA',_binary '','EGRESO'),(9,'PAGO A PROVEEDOR',_binary '','EGRESO'),(10,'VENTAS',_binary '','INGRESO'),(11,'PAGO A EMPLEADOS',_binary '','EGRESO'),(12,'COMPRA DE INSUMOS',_binary '','EGRESO'),(13,'PAGO DE IMPUESTO-WIFI',_binary '','EGRESO');
/*!40000 ALTER TABLE `tiposmovimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposusuarios`
--

DROP TABLE IF EXISTS `tiposusuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiposusuarios` (
  `idtipousuario` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtipousuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposusuarios`
--

LOCK TABLES `tiposusuarios` WRITE;
/*!40000 ALTER TABLE `tiposusuarios` DISABLE KEYS */;
INSERT INTO `tiposusuarios` VALUES (3,'Gerente',_binary '');
/*!40000 ALTER TABLE `tiposusuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turnos`
--

DROP TABLE IF EXISTS `turnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turnos` (
  `idturno` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'1',
  PRIMARY KEY (`idturno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turnos`
--

LOCK TABLES `turnos` WRITE;
/*!40000 ALTER TABLE `turnos` DISABLE KEYS */;
INSERT INTO `turnos` VALUES (1,'MAÑANA',_binary ''),(2,'NOCHE',_binary ''),(3,'MADRUGADA',_binary '');
/*!40000 ALTER TABLE `turnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidadesmedidas`
--

DROP TABLE IF EXISTS `unidadesmedidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unidadesmedidas` (
  `idunidadmedida` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idunidadmedida`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidadesmedidas`
--

LOCK TABLES `unidadesmedidas` WRITE;
/*!40000 ALTER TABLE `unidadesmedidas` DISABLE KEYS */;
INSERT INTO `unidadesmedidas` VALUES (1,'Unidades',_binary ''),(2,'Kilogramos',_binary ''),(3,'Gramos',_binary ''),(4,'Litros',_binary '');
/*!40000 ALTER TABLE `unidadesmedidas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idusuario` int(11) NOT NULL AUTO_INCREMENT,
  `idtipousuario` int(11) NOT NULL,
  `Login` varchar(255) NOT NULL DEFAULT 'NO_NAME',
  `Password` varchar(255) NOT NULL DEFAULT 'NO_PASSWORD',
  `Nombre` varchar(255) NOT NULL DEFAULT '',
  `Apellido` varchar(255) NOT NULL DEFAULT '',
  `Direccion` varchar(512) NOT NULL DEFAULT '',
  `Mail` varchar(255) NOT NULL DEFAULT '',
  `Telefono` varchar(100) NOT NULL DEFAULT '',
  `Estado` varchar(100) NOT NULL DEFAULT 'PERMITIDO',
  `activo` bit(1) NOT NULL DEFAULT b'0',
  UNIQUE KEY `id` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (4,1,'User','1234','Delivery','Tazmnia','B° Leopoldo Lugones','','387436456789','PERMITIDO',_binary '');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `idventa` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `NroFactura` varchar(100) DEFAULT NULL,
  `FechaVenta` datetime DEFAULT NULL,
  `MontoTotal` float(9,2) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idventa`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-26  9:20:37
