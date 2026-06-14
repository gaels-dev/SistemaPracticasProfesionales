package mx.uv.sistemapracticasprofesionales.modelo.pojo;

import java.util.Date;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa un periodo académico con fechas de inicio, 
 *                  fin y estado.
 */
public class Periodo {
    private Integer idPeriodo;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private Boolean cerrado;

    public Periodo() {
    }

    public Periodo(Integer idPeriodo, String nombre, Date fechaInicio, Date fechaFin, Boolean cerrado) {
        this.idPeriodo = idPeriodo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cerrado = cerrado;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getCerrado() {
        return cerrado;
    }

    public void setCerrado(Boolean cerrado) {
        this.cerrado = cerrado;
    }
    
    
}
