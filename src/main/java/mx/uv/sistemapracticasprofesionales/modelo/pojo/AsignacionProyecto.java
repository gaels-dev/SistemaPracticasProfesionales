package mx.uv.sistemapracticasprofesionales.modelo.pojo;

import java.util.Date;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa la asignación de un practicante a un proyecto,
 *                  inclyendo fechas y estado de la asignación
 */
public class AsignacionProyecto {
    private Integer idAsignacionProyecto;
    private Practicante practicante;
    private Proyecto proyecto;
    private Date fechaAsignacion;
    private String estado;
    private Date fechaBaja;
    private String motivoBaja;

    public AsignacionProyecto() {
    }

    public AsignacionProyecto(Integer idAsignacionProyecto, 
            Practicante practicante, Proyecto proyecto, Date fechaAsignacion) {
        this.idAsignacionProyecto = idAsignacionProyecto;
        this.practicante = practicante;
        this.proyecto = proyecto;
        this.fechaAsignacion = fechaAsignacion;
    }

    public AsignacionProyecto(Integer idAsignacionProyecto, 
            Practicante practicante, Proyecto proyecto, Date fechaAsignacion, 
            String estado, Date fechaBaja, String motivoBaja) {
        this.idAsignacionProyecto = idAsignacionProyecto;
        this.practicante = practicante;
        this.proyecto = proyecto;
        this.fechaAsignacion = fechaAsignacion;
        this.estado = estado;
        this.fechaBaja = fechaBaja;
        this.motivoBaja = motivoBaja;
    }

    public Integer getIdAsignacionProyecto() {
        return idAsignacionProyecto;
    }

    public void setIdAsignacionProyecto(Integer idAsignacionProyecto) {
        this.idAsignacionProyecto = idAsignacionProyecto;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }
    
}
