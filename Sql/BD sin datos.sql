CREATE DATABASE  IF NOT EXISTS `delivery_tazmania` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `delivery_tazmania`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: delivery_tazmania
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `asistencias`
--

DROP TABLE IF EXISTS `asistencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asistencias` (
  `idasistencia` int(11) NOT NULL AUTO_INCREMENT,
  `idempleado` int(11) NOT NULL,
  `fecha_asistencia` datetime NOT NULL,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  `sueldo` float(9,2) DEFAULT '0.00',
  `NroAsistencia` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idasistencia`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `caja`
--

DROP TABLE IF EXISTS `caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caja` (
  `idcaja` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `fecha_apertura` datetime NOT NULL,
  `fecha_cierre` datetime NOT NULL,
  `monto` float(9,2) DEFAULT '0.00',
  `estado` varchar(10) DEFAULT '',
  `fecha_registro` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'1',
  PRIMARY KEY (`idcaja`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `caja_turno`
--

DROP TABLE IF EXISTS `caja_turno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caja_turno` (
  `idcajaturno` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `idcaja` int(11) NOT NULL,
  `idturno` int(11) NOT NULL,
  `fecha_apertura` datetime NOT NULL,
  `fecha_cierre` datetime NOT NULL,
  `monto` float(9,2) DEFAULT '0.00',
  `estado` varchar(10) DEFAULT '',
  `fecha_registro` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'1',
  PRIMARY KEY (`idcajaturno`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoriasproductos`
--

DROP TABLE IF EXISTS `categoriasproductos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoriasproductos` (
  `idcategoriaproducto` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idcategoriaproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compras` (
  `idcompra` int(11) NOT NULL AUTO_INCREMENT,
  `idproveedor` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `NroCompra` varchar(100) DEFAULT NULL,
  `FechaCompra` datetime DEFAULT NULL,
  `MontoTotal` float(9,2) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idcompra`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `consumosempleados`
--

DROP TABLE IF EXISTS `consumosempleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
-- Table structure for table `detallescompras`
--

DROP TABLE IF EXISTS `detallescompras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallescompras` (
  `iddetallecompra` int(11) NOT NULL AUTO_INCREMENT,
  `idcompra` int(11) NOT NULL,
  `idinsumo` int(11) NOT NULL,
  `Precio` float(9,2) DEFAULT NULL,
  `Cantidad` float(9,2) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  `fechaCompra` datetime NOT NULL,
  PRIMARY KEY (`iddetallecompra`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `detallesventas`
--

DROP TABLE IF EXISTS `detallesventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallesventas` (
  `iddetalleventa` int(11) NOT NULL AUTO_INCREMENT,
  `idventa` int(11) NOT NULL,
  `idproducto` int(11) NOT NULL,
  `Precio` float(9,2) DEFAULT NULL,
  `Cantidad` float(9,2) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  `fechaVenta` datetime NOT NULL,
  PRIMARY KEY (`iddetalleventa`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `egresos`
--

DROP TABLE IF EXISTS `egresos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `formaspagos`
--

DROP TABLE IF EXISTS `formaspagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formaspagos` (
  `idformapago` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idformapago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `insumos`
--

DROP TABLE IF EXISTS `insumos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `insumos` (
  `idinsumo` int(11) NOT NULL AUTO_INCREMENT,
  `idtipoinsumo` int(11) NOT NULL,
  `idproveedor` int(11) NOT NULL,
  `descripcion` varchar(500) NOT NULL DEFAULT '',
  `precio` float(9,2) DEFAULT NULL,
  `stock` float(9,2) DEFAULT '0.00',
  `fecharegistro` datetime DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  `idunidadmedida` int(11) DEFAULT NULL,
  PRIMARY KEY (`idinsumo`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `movimientos_caja`
--

DROP TABLE IF EXISTS `movimientos_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimientos_caja` (
  `idmovimientocaja` int(11) NOT NULL AUTO_INCREMENT,
  `idtipomovimiento` int(11) NOT NULL,
  `idcajaturno` int(11) NOT NULL,
  `idusuario` int(11) NOT NULL,
  `NroMovimiento` varchar(100) DEFAULT NULL,
  `fecha_movimiento` datetime NOT NULL,
  `monto` float(9,2) DEFAULT '0.00',
  `fecha_registro` datetime NOT NULL,
  `activo` bit(1) DEFAULT b'1',
  `idmovimiento` int(11) DEFAULT '0',
  `detalle` varchar(8000) DEFAULT NULL,
  `descripcion` varchar(1000) DEFAULT NULL,
  `tipoVenta` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`idmovimientocaja`)
) ENGINE=InnoDB AUTO_INCREMENT=323 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `numerospedidosfactura`
--

DROP TABLE IF EXISTS `numerospedidosfactura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `numerospedidosfactura` (
  `idnumeropedidofactura` int(11) NOT NULL AUTO_INCREMENT,
  `nropedido` varchar(100) DEFAULT '-',
  `activo` bit(1) DEFAULT b'1',
  PRIMARY KEY (`idnumeropedidofactura`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pantallas`
--

DROP TABLE IF EXISTS `pantallas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pantallas` (
  `idpantalla` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idpantalla`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `permisospantallasperfiles`
--

DROP TABLE IF EXISTS `permisospantallasperfiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permisospantallasperfiles` (
  `idpermisopantallaperfil` int(11) NOT NULL AUTO_INCREMENT,
  `idtipousuario` int(11) NOT NULL,
  `idpantalla` int(11) NOT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idpermisopantallaperfil`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `personas`
--

DROP TABLE IF EXISTS `personas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos` (
  `idproducto` int(11) NOT NULL AUTO_INCREMENT,
  `idcategoriaproducto` int(11) NOT NULL,
  `descripcion` varchar(500) NOT NULL DEFAULT '',
  `precioventa` float(9,2) DEFAULT NULL,
  `fecharegistro` datetime DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recetas`
--

DROP TABLE IF EXISTS `recetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recetas` (
  `idreceta` int(11) NOT NULL AUTO_INCREMENT,
  `idproducto` int(11) DEFAULT NULL,
  `idinsumo` int(11) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idreceta`)
) ENGINE=InnoDB AUTO_INCREMENT=365 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiposdocumentos`
--

DROP TABLE IF EXISTS `tiposdocumentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposdocumentos` (
  `idtipodocumento` int(11) NOT NULL AUTO_INCREMENT,
  `Descripcion` varchar(100) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtipodocumento`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiposempleados`
--

DROP TABLE IF EXISTS `tiposempleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposempleados` (
  `idtipoempleado` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  `Sueldo` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idtipoempleado`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiposgastos`
--

DROP TABLE IF EXISTS `tiposgastos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposgastos` (
  `idtipogasto` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtipogasto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiposinsumos`
--

DROP TABLE IF EXISTS `tiposinsumos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposinsumos` (
  `idtipoinsumo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtipoinsumo`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiposmovimientos`
--

DROP TABLE IF EXISTS `tiposmovimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposmovimientos` (
  `idtipomovimiento` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'1',
  `tipo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idtipomovimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tiposusuarios`
--

DROP TABLE IF EXISTS `tiposusuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposusuarios` (
  `idtipousuario` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idtipousuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `turnos`
--

DROP TABLE IF EXISTS `turnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `turnos` (
  `idturno` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'1',
  PRIMARY KEY (`idturno`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `unidadesmedidas`
--

DROP TABLE IF EXISTS `unidadesmedidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidadesmedidas` (
  `idunidadmedida` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idunidadmedida`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ventas` (
  `idventa` int(11) NOT NULL AUTO_INCREMENT,
  `idusuario` int(11) NOT NULL,
  `NroFactura` varchar(100) DEFAULT NULL,
  `FechaVenta` datetime DEFAULT NULL,
  `MontoTotal` float(9,2) DEFAULT NULL,
  `activo` bit(1) DEFAULT b'0',
  `tipoVenta` varchar(30) NOT NULL,
  PRIMARY KEY (`idventa`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'delivery_tazmania'
--

--
-- Dumping routines for database 'delivery_tazmania'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-13 15:16:24
