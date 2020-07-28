CREATE TABLE `unidadesmedidas` (
  `idunidadmedida` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL DEFAULT '',
  `activo` bit(1) DEFAULT b'0',
  PRIMARY KEY (`idunidadmedida`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

INSERT INTO unidadesmedidas(descripcion,activo)
VALUES ('Unidades',1),
('Kilogramos',1),
('Gramos',1),
('Litros',1);

ALTER TABLE insumos ADD idunidadmedida int;