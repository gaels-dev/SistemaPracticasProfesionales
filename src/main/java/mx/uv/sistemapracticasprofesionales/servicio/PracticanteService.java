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

    public String validarPracticante(Practicante practicante) {
        StringBuilder errores = new StringBuilder();

        if (practicante.getMatricula() == null || practicante.getMatricula().trim().isEmpty()) {
            errores.append("- La matrícula es obligatoria.\n");
        } else if (!practicante.getMatricula().trim().matches("^S\\d{8}$")) {
            errores.append("- El formato de la matrícula es inválido (Ej: S26000000).\n");
        }

        if (practicante.getNombres() == null || practicante.getNombres().trim().isEmpty()) {
            errores.append("- El nombre es obligatorio.\n");
        } else if (practicante.getNombres().trim().length() > 50) {
            errores.append("- El nombre no puede exceder los 50 caracteres.\n");
        }

        if (practicante.getApellidoPaterno() == null || practicante.getApellidoPaterno().trim().isEmpty()) {
            errores.append("- El apellido paterno es obligatorio.\n");
        } else if (practicante.getApellidoPaterno().trim().length() > 40) {
            errores.append("- El apellido paterno no puede exceder los 40 caracteres.\n");
        }

        if (practicante.getApellidoMaterno() == null || practicante.getApellidoMaterno().trim().isEmpty()) {
            errores.append("- El apellido materno es obligatorio.\n");
        } else if (practicante.getApellidoMaterno().trim().length() > 40) {
            errores.append("- El apellido materno no puede exceder los 40 caracteres.\n");
        }

        if (practicante.getCorreo() == null || practicante.getCorreo().trim().isEmpty()) {
            errores.append("- El correo electrónico es obligatorio.\n");
        } else if (practicante.getCorreo().trim().length() > 200) {
            errores.append("- El correo electrónico no puede exceder los 200 caracteres.\n");
        } else if (!practicante.getCorreo().matches("^[\\w+-]+(?:\\.[\\w+-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$")) {
            errores.append("- El formato del correo electrónico es inválido.\n");
        }

        if (practicante.getSexo() == null) {
            errores.append("- Debe seleccionar un sexo.\n");
        }

        if (practicante.getUsuario() == null || practicante.getUsuario().getNombre() == null || practicante.getUsuario().getNombre().trim().isEmpty()) {
            errores.append("- El nombre de usuario es obligatorio.\n");
        }

        if (practicante.getUsuario() == null || practicante.getUsuario().getContrasenia() == null || practicante.getUsuario().getContrasenia().trim().isEmpty()) {
            errores.append("- La contraseña es obligatoria.\n");
        }

        return errores.toString();
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