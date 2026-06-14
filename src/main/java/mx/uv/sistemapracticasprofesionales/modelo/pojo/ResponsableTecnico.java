package mx.uv.sistemapracticasprofesionales.modelo.pojo;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa a la persona encargada de un proyecto dentro de 
 *                  una organización vinculada.
 */
public class ResponsableTecnico {
    private Integer idResponsableTecnico;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private OrganizacionVinculada organizacionVinculada;

    public ResponsableTecnico() {
    }

    public ResponsableTecnico(Integer idResponsableTecnico, String nombres, 
            String apellidoPaterno, String apellidoMaterno, String correo, 
            OrganizacionVinculada organizacionVinculada) {
        this.idResponsableTecnico = idResponsableTecnico;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.organizacionVinculada = organizacionVinculada;
    }

    public Integer getIdResponsableTecnico() {
        return idResponsableTecnico;
    }

    public void setIdResponsableTecnico(Integer idResponsableTecnico) {
        this.idResponsableTecnico = idResponsableTecnico;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public OrganizacionVinculada getOrganizacionVinculada() {
        return organizacionVinculada;
    }

    public void setOrganizacionVinculada(OrganizacionVinculada organizacionVinculada) {
        this.organizacionVinculada = organizacionVinculada;
    }
    
    
}
