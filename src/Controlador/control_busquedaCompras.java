package Controlador;

import java.sql.Connection;

/**
 *
 * @author usuario
 */
public class control_busquedaCompras {
    Sentencias_sql sql;
    private final Conexion mysql = new Conexion();
    private final Connection cn = mysql.obtener();
    
    public control_busquedaCompras(){
        sql = new Sentencias_sql();
    }
    
    public Object[][] NroComprasFechas(String desde, String hasta){
        String[] columnas = {"idcompra", "idproveedor", "idusuario", "Nombre_comercial", "NroCompra", "Login", "MontoTotal", "FechaCompra"};
        Object[][] datos = sql.GetTabla(columnas, "compras", "select c.idcompra,p.idproveedor,u.idusuario,p.Nombre_comercial,c.NroCompra,u.Login,c.MontoTotal,date_format(c.FechaCompra,'%d/%m/%Y %H:%i') as FechaCompra from proveedores p INNER JOIN compras c on p.idproveedor=c.idproveedor INNER JOIN usuarios u on u.idusuario=c.idusuario where date(c.FechaCompra) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and c.activo=1 order by FechaCompra asc;");
        return datos;
    }
    
    public Object[][] NroDetallesComprasFechas(String desde, String hasta){
        String[] columnas = {"iddetallecompra", "idcompra", "idinsumo", "NroCompra", "descripcion", "Precio", "Cantidad","FechaCompra"};
        Object[][] datos = sql.GetTabla(columnas, "detallescompras", "select d.iddetallecompra,c.idcompra,i.idinsumo,c.NroCompra,i.descripcion,d.Precio,d.Cantidad,date_format(d.fechaCompra,'%d/%m/%Y %H:%i') as FechaCompra from compras c INNER JOIN detallescompras d on c.idcompra=d.idcompra INNER JOIN insumos i on i.idinsumo=d.idinsumo where date(d.fechaCompra) between str_to_date((str_to_date('" + desde + "','%d/%m/%Y')),'%Y-%m-%d') and str_to_date((str_to_date('" + hasta + "','%d/%m/%Y')),'%Y-%m-%d') and d.activo=1 order by c.NroCompra desc");
        return datos;
    }
    
}
