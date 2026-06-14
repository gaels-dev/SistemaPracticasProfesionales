package mx.uv.sistemapracticasprofesionales.modelo.pojo;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa una organización vinculada a proyectos dentro 
 *                  del sistema.
 */
public class OrganizacionVinculada {
    private Integer idOrganizacionVinculada;
    private String razonSocial;
    private String domicilioFiscal;
    private String telefono;
    private String rfc;
    private Boolean activo;

    public OrganizacionVinculada() {
    }

    public OrganizacionVinculada(Integer idOrganizacionVinculada, 
            String razonSocial, String domicilioFiscal, 
            String telefono, String rfc, Boolean activo) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
        this.razonSocial = razonSocial;
        this.domicilioFiscal = domicilioFiscal;
        this.telefono = telefono;
        this.rfc = rfc;
        this.activo = activo;
    }

    public Integer getIdOrganizacionVinculada() {
        return idOrganizacionVinculada;
    }

    public void setIdOrganizacionVinculada(Integer idOrganizacionVinculada) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    
}
