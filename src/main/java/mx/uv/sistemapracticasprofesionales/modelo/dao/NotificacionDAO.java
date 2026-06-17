package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Notificacion;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: DAO para gestionar las notificaciones de los practicantes.
 */
public class NotificacionDAO {

    public List<Notificacion> obtenerNotificacionesPorPracticante(int idPracticante) throws SQLException {
        List<Notificacion> notificaciones = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT id_notificacion, id_practicante, mensaje, fecha_creacion " +
                          "FROM notificacion_practicante " +
                          "WHERE id_practicante = ? " +
                          "ORDER BY fecha_creacion ASC";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idPracticante);
            resultado = prepararSentencia.executeQuery();
            
            while (resultado.next()) {
                Notificacion notificacion = new Notificacion();
                notificacion.setIdNotificacion(resultado.getInt("id_notificacion"));
                notificacion.setIdPracticante(resultado.getInt("id_practicante"));
                notificacion.setMensaje(resultado.getString("mensaje"));
                notificacion.setFechaCreacion(resultado.getTimestamp("fecha_creacion"));
                notificaciones.add(notificacion);
            }
        } finally {
            if (resultado != null) {
                resultado.close();
            }
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return notificaciones;
    }

    public boolean eliminarNotificacion(int idNotificacion) throws SQLException {
        boolean eliminado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "DELETE FROM notificacion_practicante WHERE id_notificacion = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idNotificacion);
            eliminado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return eliminado;
    }
}