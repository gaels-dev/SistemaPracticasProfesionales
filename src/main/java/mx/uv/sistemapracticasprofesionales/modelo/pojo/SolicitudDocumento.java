package mx.uv.sistemapracticasprofesionales.modelo.pojo;

import java.util.Date;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa la solicitud de un documento dentro de una 
 *                  experiencia educativa, incluyendo fecha límite.
 */
public class SolicitudDocumento {
    private Integer solicitudDocumento;
    private Date fechaLimite;
    private Documento documento;
    private ExperienciaEducativa experienciaEducativa;

    public SolicitudDocumento() {
    }

    public SolicitudDocumento(Integer solicitudDocumento, Date fechaLimite, 
            Documento documento, ExperienciaEducativa experienciaEducativa) {
        this.solicitudDocumento = solicitudDocumento;
        this.fechaLimite = fechaLimite;
        this.documento = documento;
        this.experienciaEducativa = experienciaEducativa;
    }

    public Integer getSolicitudDocumento() {
        return solicitudDocumento;
    }

    public void setSolicitudDocumento(Integer solicitudDocumento) {
        this.solicitudDocumento = solicitudDocumento;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public ExperienciaEducativa getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(ExperienciaEducativa experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }
    
    
}
