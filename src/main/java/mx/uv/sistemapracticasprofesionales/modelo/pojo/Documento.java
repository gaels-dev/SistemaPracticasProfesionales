package mx.uv.sistemapracticasprofesionales.modelo.pojo;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa un tipo de documento requerido dentro del 
 *                  sistema, incluyendo su formato y criterios de evaluación.
 */
public class Documento {
    private Integer idDocumento;
    private String nombreDocumento;
    private String tipoDocumento;
    private Double calificacionMaxima;
    private byte[] formato;
    private Boolean activo;

    public Documento() {
    }

    public Documento(Integer idDocumento, String nombreDocumento, 
            String tipoDocumento, Double calificacionMaxima, byte[] formato) {
        this.idDocumento = idDocumento;
        this.nombreDocumento = nombreDocumento;
        this.tipoDocumento = tipoDocumento;
        this.calificacionMaxima = calificacionMaxima;
        this.formato = formato;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Double getCalificacionMaxima() {
        return calificacionMaxima;
    }

    public void setCalificacionMaxima(Double calificacionMaxima) {
        this.calificacionMaxima = calificacionMaxima;
    }

    public byte[] getFormato() {
        return formato;
    }

    public void setFormato(byte[] formato) {
        this.formato = formato;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
}
