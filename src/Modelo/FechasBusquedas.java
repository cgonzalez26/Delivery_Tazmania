package Modelo;

import java.util.Date;

/**
 *
 * @author Colo-PC
 */
public class FechasBusquedas {

    public static Date dateDesdeAsistencias,
            dateHastaAsistencias,
            dateDesdeCompras,
            dateHastaCompras,
            dateDesdeVentas,
            dateHastaVentas,
            dateDesdeMovCaja,
            dateHastaMovCaja;
    public static boolean iniciarFechaAsistencia = false,
            iniciarFechaCompras = false,
            iniciarFechaVentas = false,
            iniciarFechaMovCaja = false;

    public FechasBusquedas() {

    }

    public static Date getDateDesdeAsistencias() {
        return dateDesdeAsistencias;
    }

    public static void setDateDesdeAsistencias(Date dateDesdeAsistencias) {
        FechasBusquedas.dateDesdeAsistencias = dateDesdeAsistencias;
    }

    public static Date getDateHastaAsistencias() {
        return dateHastaAsistencias;
    }

    public static void setDateHastaAsistencias(Date dateHastaAsistencias) {
        FechasBusquedas.dateHastaAsistencias = dateHastaAsistencias;
    }

    public static Date getDateDesdeCompras() {
        return dateDesdeCompras;
    }

    public static void setDateDesdeCompras(Date dateDesdeCompras) {
        FechasBusquedas.dateDesdeCompras = dateDesdeCompras;
    }

    public static Date getDateHastaCompras() {
        return dateHastaCompras;
    }

    public static void setDateHastaCompras(Date dateHastaCompras) {
        FechasBusquedas.dateHastaCompras = dateHastaCompras;
    }

    public static Date getDateDesdeVentas() {
        return dateDesdeVentas;
    }

    public static void setDateDesdeVentas(Date dateDesdeVentas) {
        FechasBusquedas.dateDesdeVentas = dateDesdeVentas;
    }

    public static Date getDateHastaVentas() {
        return dateHastaVentas;
    }

    public static void setDateHastaVentas(Date dateHastaVentas) {
        FechasBusquedas.dateHastaVentas = dateHastaVentas;
    }

    public static Date getDateDesdeMovCaja() {
        return dateDesdeMovCaja;
    }

    public static void setDateDesdeMovCaja(Date dateDesdeMovCaja) {
        FechasBusquedas.dateDesdeMovCaja = dateDesdeMovCaja;
    }

    public static Date getDateHastaMovCaja() {
        return dateHastaMovCaja;
    }

    public static void setDateHastaMovCaja(Date dateHastaMovCaja) {
        FechasBusquedas.dateHastaMovCaja = dateHastaMovCaja;
    }

    public static boolean isIniciarFechaAsistencia() {
        return iniciarFechaAsistencia;
    }

    public static void setIniciarFechaAsistencia(boolean iniciarFechaAsistencia) {
        FechasBusquedas.iniciarFechaAsistencia = iniciarFechaAsistencia;
    }

    public static boolean isIniciarFechaCompras() {
        return iniciarFechaCompras;
    }

    public static void setIniciarFechaCompras(boolean iniciarFechaCompras) {
        FechasBusquedas.iniciarFechaCompras = iniciarFechaCompras;
    }

    public static boolean isIniciarFechaVentas() {
        return iniciarFechaVentas;
    }

    public static void setIniciarFechaVentas(boolean iniciarFechaVentas) {
        FechasBusquedas.iniciarFechaVentas = iniciarFechaVentas;
    }

    public static boolean isIniciarFechaMovCaja() {
        return iniciarFechaMovCaja;
    }

    public static void setIniciarFechaMovCaja(boolean iniciarFechaMovCaja) {
        FechasBusquedas.iniciarFechaMovCaja = iniciarFechaMovCaja;
    }

}
