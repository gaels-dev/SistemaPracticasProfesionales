package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.dao.PracticanteDAO;
import mx.uv.sistemapracticasprofesionales.modelo.dao.TipoUsuarioDAO;
import mx.uv.sistemapracticasprofesionales.modelo.dao.UsuarioDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.TipoUsuario;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Servicio para gestionar la lógica de negocio de los practicantes.
 */
public class PracticanteService {

    private final PracticanteDAO practicanteDAO = new PracticanteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();

    public List<Practicante> obtenerPracticantesInscritosPeriodoActual() throws SQLException {
        return practicanteDAO.obtenerPracticantesInscritosPeriodoActual();
    }

    public List<Practicante> obtenerPracticantesDisponiblesParaProyecto() throws SQLException {
        return practicanteDAO.obtenerPracticantesDisponiblesParaProyecto();
    }

    public boolean existePracticanteActivoPorMatricula(String matricula) throws SQLException {
        return practicanteDAO.existePracticanteActivoPorMatricula(matricula);
    }

    public Practicante buscarPorMatricula(String matricula) throws SQLException {
        return practicanteDAO.buscarPorMatricula(matricula);
    }

    public Practicante buscarPorIdUsuario(int idUsuario) throws SQLException {
        return practicanteDAO.buscarPorIdUsuario(idUsuario);
    }

    public boolean reactivarPracticante(int idPracticante) throws SQLException {
        return practicanteDAO.reactivarPracticante(idPracticante);
    }
    public boolean registrarPracticante(Practicante practicante) throws SQLException {
        if (usuarioDAO.existeUsuarioPorNombre(practicante.getUsuario().getNombre())) {
            throw new SQLException("El nombre de usuario ingresado ya está en uso.");
        }

        TipoUsuario tipoUsuarioPracticante = tipoUsuarioDAO.buscarTipoUsuarioPorRol("Practicante");
        if (tipoUsuarioPracticante == null) {
            throw new SQLException("El tipo de usuario 'Practicante' no se encuentra registrado en el sistema.");
        }

        Usuario usuario = practicante.getUsuario();
        usuario.setTipoUsuario(tipoUsuarioPracticante);
        usuario.setActivo(true);

        Connection conexion = null;
        try {
            conexion = ConexionBD.abrirConexion();
            conexion.setAutoCommit(false);

            int idUsuario = usuarioDAO.registrarUsuario(usuario, conexion);
            if (idUsuario > 0) {
                usuario.setIdUsuario(idUsuario);
                int idPracticante = practicanteDAO.registrarPracticante(practicante, conexion);
                if (idPracticante > 0) {
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
                    System.err.println("Error al cerrar conexión en transacción: " + ex.getMessage());
                }
            }
        }
    }
}