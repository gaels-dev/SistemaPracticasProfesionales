package mx.uv.sistemapracticasprofesionales.modelo.pojo;

import java.util.Date;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa la entrega de un documento por parte de un 
 *                  practicante, incluyendo archivo, calificación y estado.
 */
public class EntregaDocumento {
    private Integer idEntregaDocumento;
    private Practicante practicante;
    private SolicitudDocumento solicitudDocumento;
    private byte[] archivoEntregado;
    private Date fechaEntrega;
    private Double calificacion;
    private String estado;
    private String retroalimentacion;
    private String motivoRechazo;
    private String extension;
    private String nombreArchivo;
    private PersonalAcademico profesorEvaluador;

    public EntregaDocumento() {
    }

    public EntregaDocumento(Integer idEntregaDocumento, Practicante practicante, 
            SolicitudDocumento solicitudDocumento, byte[] archivoEntregado, 
            Date fechaEntrega) {
        this.idEntregaDocumento = idEntregaDocumento;
        this.practicante = practicante;
        this.solicitudDocumento = solicitudDocumento;
        this.archivoEntregado = archivoEntregado;
        this.fechaEntrega = fechaEntrega;
    }

    public EntregaDocumento(Integer idEntregaDocumento, Practicante practicante, 
            SolicitudDocumento solicitudDocumento, byte[] archivoEntregado, 
            Date fechaEntrega, Double calificacion, String estado, 
            String retroalimentacion, String motivoRechazo, String nombreArchivo,
            PersonalAcademico profesorEvaluador) {
        this.idEntregaDocumento = idEntregaDocumento;
        this.practicante = practicante;
        this.solicitudDocumento = solicitudDocumento;
        this.archivoEntregado = archivoEntregado;
        this.fechaEntrega = fechaEntrega;
        this.calificacion = calificacion;
        this.estado = estado;
        this.retroalimentacion = retroalimentacion;
        this.motivoRechazo = motivoRechazo;
        this.nombreArchivo = nombreArchivo;
        this.profesorEvaluador = profesorEvaluador;
    }

    public Integer getIdEntregaDocumento() {
        return idEntregaDocumento;
    }

    public void setIdEntregaDocumento(Integer idEntregaDocumento) {
        this.idEntregaDocumento = idEntregaDocumento;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public SolicitudDocumento getSolicitudDocumento() {
        return solicitudDocumento;
    }

    public void setSolicitudDocumento(SolicitudDocumento solicitudDocumento) {
        this.solicitudDocumento = solicitudDocumento;
    }

    public byte[] getArchivoEntregado() {
        return archivoEntregado;
    }

    public void setArchivoEntregado(byte[] archivoEntregado) {
        this.archivoEntregado = archivoEntregado;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
    
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public PersonalAcademico getProfesorEvaluador() {
        return profesorEvaluador;
    }

    public void setProfesorEvaluador(PersonalAcademico profesorEvaluador) {
        this.profesorEvaluador = profesorEvaluador;
    }

}
