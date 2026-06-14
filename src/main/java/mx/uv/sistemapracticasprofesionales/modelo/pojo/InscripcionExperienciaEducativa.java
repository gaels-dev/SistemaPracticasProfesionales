package mx.uv.sistemapracticasprofesionales.modelo.pojo;

import java.util.Date;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa la inscripción de un practicante en una 
 *                  experiencia educativa, incluyendo estado y calificación.
 */
public class InscripcionExperienciaEducativa {
    private ExperienciaEducativa experienciaEducativa;
    private Practicante practicante;
    private Double calificacion;
    private String estado;
    private Date fechaInscripcion;
    private Date fechaBaja;
    private String motivoBaja;

    public InscripcionExperienciaEducativa() {
    }

    public InscripcionExperienciaEducativa(
            ExperienciaEducativa experienciaEducativa, Practicante practicante, 
            Double calificacion, String estado, Date fechaInscripcion) {
        this.experienciaEducativa = experienciaEducativa;
        this.practicante = practicante;
        this.calificacion = calificacion;
        this.estado = estado;
        this.fechaInscripcion = fechaInscripcion;
    }

    public InscripcionExperienciaEducativa(
            ExperienciaEducativa experienciaEducativa, Practicante practicante, 
            Double calificacion, String estado, Date fechaInscripcion, 
            Date fechaBaja, String motivoBaja) {
        this.experienciaEducativa = experienciaEducativa;
        this.practicante = practicante;
        this.calificacion = calificacion;
        this.estado = estado;
        this.fechaInscripcion = fechaInscripcion;
        this.fechaBaja = fechaBaja;
        this.motivoBaja = motivoBaja;
    }

    public ExperienciaEducativa getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(ExperienciaEducativa experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
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
