package mx.uv.sistemapracticasprofesionales.modelo.pojo;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa a un estudiante que participa como practicante 
 *                  al estar inscrito en la EE Prácticas Profesionales.
 */
public class Practicante {
    private Integer idPracticante;
    private String matricula;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String sexo;
    private Boolean activo;
    private Usuario usuario;

    public Practicante() {
    }

    public Practicante(Integer idPracticante, String matricula, String nombres, 
            String apellidoPaterno, String apellidoMaterno, String correo, 
            String sexo, Boolean activo, Usuario usuario) {
        this.idPracticante = idPracticante;
        this.matricula = matricula;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.sexo = sexo;
        this.activo = activo;
        this.usuario = usuario;
    }

    public Integer getIdPracticante() {
        return idPracticante;
    }

    public void setIdPracticante(Integer idPracticante) {
        this.idPracticante = idPracticante;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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
