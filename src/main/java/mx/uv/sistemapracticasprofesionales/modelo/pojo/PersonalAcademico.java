package mx.uv.sistemapracticasprofesionales.modelo.pojo;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa al personal académico responsable de 
 *                  experiencias educativas o a un coordinador.
 */
public class PersonalAcademico {
    private Integer idPersonalAcademico;
    private String noPersonal;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private Boolean activo;
    private Usuario usuario;

    public PersonalAcademico() {
    }

    public PersonalAcademico(Integer idPersonalAcademico, String noPersonal, 
            String nombres, String apellidoPaterno, String apellidoMaterno, 
            String correo, Boolean activo, Usuario usuario) {
        this.idPersonalAcademico = idPersonalAcademico;
        this.noPersonal = noPersonal;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.activo = activo;
        this.usuario = usuario;
    }

    public Integer getIdPersonalAcademico() {
        return idPersonalAcademico;
    }

    public void setIdPersonalAcademico(Integer idPersonalAcademico) {
        this.idPersonalAcademico = idPersonalAcademico;
    }

    public String getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
