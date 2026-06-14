package mx.uv.sistemapracticasprofesionales.modelo.pojo;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  14/06/2026
 * Descripción:     Representa el rol de un usuario dentro del sistema.
 */
public class TipoUsuario {
    private Integer idTipoUsuario;
    private String rol;

    public TipoUsuario() {
    }

    public TipoUsuario(Integer idTipoUsuario, String rol) {
        this.idTipoUsuario = idTipoUsuario;
        this.rol = rol;
    }

    public Integer getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(Integer idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
}
