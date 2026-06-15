package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Periodo;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de los periodos escolares.
 */
public class PeriodoDAO {

    public boolean existePeriodoAbierto() throws SQLException {
        boolean existe = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT COUNT(*) FROM periodo WHERE cerrado = 0";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                existe = resultado.getInt(1) > 0;
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
        return existe;
    }

    public int registrarPeriodo(Periodo periodo) throws SQLException {
        int idGenerado = -1;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO periodo (nombre, fecha_inicio, fecha_fin, cerrado) VALUES (?, ?, ?, 0)";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setString(1, periodo.getNombre());
            prepararSentencia.setDate(2, new Date(periodo.getFechaInicio().getTime()));
            prepararSentencia.setDate(3, new Date(periodo.getFechaFin().getTime()));
            
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
}
