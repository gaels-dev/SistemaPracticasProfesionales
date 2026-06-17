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

    public boolean registrarPersonal(PersonalAcademico personal, String rol) throws SQLException {
        if (usuarioDAO.existeUsuarioPorNombre(personal.getUsuario().getNombre())) {
            throw new SQLException("El nombre de usuario ingresado ya está en uso.");
        }

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
}
