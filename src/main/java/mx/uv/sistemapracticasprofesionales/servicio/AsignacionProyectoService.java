package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.Connection;
import java.sql.SQLException;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.dao.AsignacionProyectoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.AsignacionProyecto;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Servicio para gestionar la lógica de negocio de las asignaciones de proyectos.
 */
public class AsignacionProyectoService {

    private final AsignacionProyectoDAO asignacionProyectoDAO = new AsignacionProyectoDAO();

    public boolean registrarAsignacion(AsignacionProyecto asignacion) throws SQLException {
        Connection conexion = null;
        try {
            conexion = ConexionBD.abrirConexion();
            conexion.setAutoCommit(false); 

            int idAsignacion = asignacionProyectoDAO.registrarAsignacionProyecto(asignacion, conexion);
            if (idAsignacion > 0) {
                String mensajeNotificacion = "Se te ha asignado el proyecto: " + asignacion.getProyecto().getNombre();
                boolean notificacionRegistrada = asignacionProyectoDAO.registrarNotificacionAsignacion(
                        asignacion.getPracticante().getIdPracticante(), 
                        mensajeNotificacion, 
                        conexion
                );
                
                if (notificacionRegistrada) {
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