package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.dao.PersonalAcademicoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.dao.TipoUsuarioDAO;
import mx.uv.sistemapracticasprofesionales.modelo.dao.UsuarioDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.TipoUsuario;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;

/**
 * Autor: Oscar Turrent Peña
 * Fecha creación: 16/06/2026
 * Descripción: Servicio para gestionar la lógica de negocio del personal académico.
 */
public class PersonalAcademicoService {

    private final PersonalAcademicoDAO personalAcademicoDAO = new PersonalAcademicoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();

    public List<PersonalAcademico> obtenerPersonalPorRol(String rol) throws SQLException {
        return personalAcademicoDAO.obtenerPersonalAcademicoPorRol(rol);
    }

    public boolean existePersonalPorNumeroYRol(String noPersonal, String rol) throws SQLException {
        return personalAcademicoDAO.existePersonalPorNumeroYRol(noPersonal, rol);
    }
    
    public String validarFormato(PersonalAcademico personal) {
        StringBuilder errores = new StringBuilder();
        
        validarNombre(personal.getNombres(), "Nombre", errores);
        validarNombre(personal.getApellidoPaterno(), "Apellido paterno", errores);
        validarNombre(personal.getApellidoMaterno(), "Apellido materno", errores);
        
        if (!personal.getCorreo().matches("^[\\w.-]+@uv\\.mx$")) {
            errores.append("- El correo electrónico debe pertenecer al "
                    + "dominio @uv.mx\n");
        }
        if (!personal.getNoPersonal().matches("NP\\d{4}")) {
            errores.append("- El numero de personal debe empezar con \"NP\""
                    + " seguido de 4 digitos.\n");
        }
        
        return errores.toString();
    }
    
    private void validarNombre(String texto, String nombreCampo, 
            StringBuilder errores) {
        if (texto.length() < 3) {
            errores.append("- ").append(nombreCampo).append(
                    " debe tener al menos 3 letras.\n");
        } else if (!texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            errores.append("- ").append(nombreCampo)
                .append(" solo debe contener letras y espacios.\n");
        } else if (texto.toLowerCase().matches(".*(.)\\1{2,}.*")) {
            errores.append("- ").append(nombreCampo)
                .append(" no puede tener un mismo carácter repetido 3 o más "
                        + "veces consecutivas.\n");
        }
    }

    public boolean registrarPersonal(PersonalAcademico personal, String rol) throws SQLException {

        TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorRol(rol);
        if (tipoUsuario == null) {
            throw new SQLException("El tipo de usuario '" + rol + "' no se encuentra registrado.");
        }

        Usuario usuario = personal.getUsuario();
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setActivo(true);

        Connection conexion = null;
        try {
            conexion = ConexionBD.abrirConexion();
            conexion.setAutoCommit(false);

            int idUsuario = usuarioDAO.registrarUsuario(usuario, conexion);
            if (idUsuario > 0) {
                usuario.setIdUsuario(idUsuario);
                int idPersonal = personalAcademicoDAO.registrarPersonalAcademico(personal, conexion);
                if (idPersonal > 0) {
                    conexion.commit();
                    return true;
                } else {
                    conexion.rollback();
                    return false;
                }
            } else {
                conexion.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                    ConexionBD.cerrarConexion(conexion);
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
    }
    
    public Boolean activarPersonalAcademico(PersonalAcademico personal) throws SQLException {
        int idPersonal = (int) personal.getIdPersonalAcademico();
        personalAcademicoDAO.activarPersonalAcademico(idPersonal);
        int idUsuario = (int) personal.getUsuario().getIdUsuario();
        usuarioDAO.activarUsuario(idUsuario);
        
        return true;
    }
    
    public Boolean desactivarPersonalAcademico(PersonalAcademico personal) throws SQLException {
        int idPersonal = (int) personal.getIdPersonalAcademico();
        personalAcademicoDAO.darDeBajaPersonalAcademico(idPersonal);
        int idUsuario = (int) personal.getUsuario().getIdUsuario();
        usuarioDAO.desactivarUsuario(idUsuario);
        
        return true;
    }
    
    public boolean activarCoordinador(PersonalAcademico coordinador) throws SQLException {
        PersonalAcademico coordinadorActual = personalAcademicoDAO.obtenerCoordinadorActivo();
        Boolean activado = activarPersonalAcademico(coordinador);
        if (activado == null) {
            throw new SQLException("Error al activar al coordinador, intentelo más tarde");
        }
        
        if (activado) {
            coordinador.setActivo(true);
            coordinador.getUsuario().setActivo(true);
            
            if (coordinadorActual != null) {
                desactivarPersonalAcademico(coordinadorActual);
            }
        } else {
            activado = false;
        }
        
        return activado;
    }
}
