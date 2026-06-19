package mx.uv.sistemapracticasprofesionales.modelo.pojo;

import java.sql.Timestamp;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase que representa una notificación para un practicante.
 */
public class Notificacion {
    private int idNotificacion;
    private int idPracticante;
    private String mensaje;
    private Timestamp fechaCreacion;

    public Notificacion() {
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public int getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(int idPracticante) {
        this.idPracticante = idPracticante;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}