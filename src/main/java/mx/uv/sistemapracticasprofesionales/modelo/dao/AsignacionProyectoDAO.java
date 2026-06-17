package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.AsignacionProyecto;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de las asignaciones de proyectos a practicantes.
 */
public class AsignacionProyectoDAO {

    public int registrarAsignacionProyecto(AsignacionProyecto asignacionProyecto) throws SQLException {
        int idGenerado = -1;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO asignacion_proyecto (id_practicante, id_proyecto, fecha_asignacion, estado) " +
                          "VALUES (?, ?, CURRENT_TIMESTAMP, 'Activa')";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setInt(1, asignacionProyecto.getPracticante().getIdPracticante());
            prepararSentencia.setInt(2, asignacionProyecto.getProyecto().getIdProyecto());
            
            prepararSentencia.executeUpdate();
            resultado = prepararSentencia.getGeneratedKeys();
            if (resultado.next()) {
                idGenerado = resultado.getInt(1);
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
        return idGenerado;
    }

    public int registrarAsignacionProyecto(AsignacionProyecto asignacionProyecto, Connection conexion) throws SQLException {
        int idGenerado = -1;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO asignacion_proyecto (id_practicante, id_proyecto, fecha_asignacion, estado) " +
                          "VALUES (?, ?, CURRENT_TIMESTAMP, 'Activa')";
        
        try {
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setInt(1, asignacionProyecto.getPracticante().getIdPracticante());
            prepararSentencia.setInt(2, asignacionProyecto.getProyecto().getIdProyecto());
            
            prepararSentencia.executeUpdate();
            resultado = prepararSentencia.getGeneratedKeys();
            if (resultado.next()) {
                idGenerado = resultado.getInt(1);
            }
        } finally {
            if (resultado != null) {
                resultado.close();
            }
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
        }
        return idGenerado;
    }

    public boolean registrarNotificacionAsignacion(int idPracticante, String mensaje, Connection conexion) throws SQLException {
        boolean registrado = false;
        PreparedStatement prepararSentencia = null;
        String consulta = "INSERT INTO notificacion_practicante (id_practicante, mensaje) VALUES (?, ?)";
        
        try {
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idPracticante);
            prepararSentencia.setString(2, mensaje);
            registrado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
        }
        return registrado;
    }
}
