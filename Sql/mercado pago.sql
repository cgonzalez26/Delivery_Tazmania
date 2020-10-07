create table mercadopago (
idmercadopago int(11) NOT NULL AUTO_INCREMENT,
idcliente int(11) NOT NULL,
nroOperacion varchar(200) NOT NULL DEFAULT '',
importe float(9,2)  NOT NULL DEFAULT 0.00,
descripcion varchar(255) NULL DEFAULT '-',
fecha datetime NOT NULL,
activo bit(1) DEFAULT b'0',
PRIMARY KEY (idmercadopago)
);