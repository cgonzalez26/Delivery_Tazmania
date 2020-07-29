/*se lo desactiva safe updates para no tener errores*/
set sql_safe_updates =0;

/* asistencias */
update asistencias set descripcion = upper(descripcion);

/*caja*/
update caja set estado = upper(estado);

/*caja turno*/
update caja_turno set estado = upper(estado);

/*categorias productos*/
update categoriasproductos set descripcion = upper(descripcion);

/*clientes*/
update clientes set nombre = upper(nombre);
update clientes set apellido = upper(apellido);
update clientes set domicilio = upper(domicilio);
update clientes set telefono = upper(telefono);
update clientes set email = upper(email);
update clientes set dni = upper(dni);
select * from clientes;


/*compras*/
/*no hay ninguno para convertir*/

/*consumos empleados*/
update consumosempleados set nombreEmp = upper(nombreEmp);
update consumosempleados set producto = upper(producto);

/*detalles compras*/
/*no hay ninguno para convertir*/

/*detalles ventas*/
/*no hay ninguno para convertir*/

/*egresos*/
update egresos set descripcion = upper(descripcion);
update egresos set detalle = upper(detalle);

/*empleados*/
update empleados set NroDocumento = upper(NroDocumento);
update empleados set Nombre = upper(Nombre);
update empleados set Apellido = upper(Apellido);
update empleados set direccion = upper(direccion);
update empleados set Telefono = upper(Telefono);

/*formas pagos*/
update formaspagos set descripcion = upper(descripcion);

/*insumos*/
update insumos set descripcion = upper(descripcion);

/*movimientos caja*/
update movimientos_caja set detalle = upper(detalle);
update movimientos_caja set descripcion = upper(descripcion);
update movimientos_caja set tipoVenta = upper(tipoVenta);

/*numeros pedidos factura*/
/*no hay datos para convertir*/

/*pantallas*/
update pantallas set nombre = upper(nombre);

/*permisos pantallas perfiles*/
/*no hay datos para convertir*/

/*productos*/
update productos set descripcion = upper(descripcion);

/*proveedores*/
update proveedores set NroDocumento = upper(NroDocumento);
update proveedores set Nombre = upper(Nombre);
update proveedores set Apellido = upper(Apellido);
update proveedores set Nombre_comercial = upper(Nombre_comercial);
update proveedores set direccion = upper(direccion);
update proveedores set Telefono = upper(Telefono);

/*recetas*/
/*no hay datos para convertir*/

/*tipos documentos*/
update tiposdocumentos set Descripcion = upper(Descripcion);

/*tipos empleados*/
update tiposempleados set descripcion = upper(descripcion);

/*tipos gastos*/
update tiposgastos set descripcion = upper(descripcion);

/*tipos insumos*/
update tiposinsumos set descripcion = upper(descripcion);

/*tipos movimientos*/
update tiposmovimientos set descripcion = upper(descripcion);
update tiposmovimientos set tipo = upper(tipo);

/*tipos usuarios*/
update tiposusuarios set descripcion = upper(descripcion);

/*turnos*/
update turnos set descripcion = upper(descripcion);

/*unidades medidas*/
update unidadesmedidas set descripcion = upper(descripcion);

/*usuarios*/
update usuarios set Login = upper(Login);
update usuarios set Password = upper(Password);
update usuarios set Nombre = upper(Nombre);
update usuarios set Apellido = upper(Apellido);
update usuarios set Direccion = upper(Direccion);
update usuarios set Mail = upper(Mail);
update usuarios set Telefono = upper(Telefono);
update usuarios set Estado = upper(Estado);

/*ventas*/
update ventas set tipoVenta = upper(tipoVenta);