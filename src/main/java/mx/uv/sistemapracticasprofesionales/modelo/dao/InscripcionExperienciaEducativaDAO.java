package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.InscripcionExperienciaEducativa;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de las inscripciones a experiencias educativas.
 */
public class InscripcionExperienciaEducativaDAO {

    public boolean registrarInscripcion(InscripcionExperienciaEducativa inscripcion) throws SQLException {
        boolean registrado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "INSERT INTO inscripcion_experiencia_educativa (id_experiencia_educativa, id_practicante, " +
                          "estado) VALUES (?, ?, 'Inscrito')";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, inscripcion.getExperienciaEducativa().getIdExperienciaEducativa());
            prepararSentencia.setInt(2, inscripcion.getPracticante().getIdPracticante());
            
            registrado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return registrado;
    }

    public boolean existeInscripcionActiva(int idPracticante, int idExperienciaEducativa) throws SQLException {
        boolean existe = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT COUNT(*) FROM inscripcion_experiencia_educativa WHERE id_practicante = ? " +
                "AND id_experiencia_educativa = ? AND estado IN ('Inscrito', 'Cursando')";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idPracticante);
            prepararSentencia.setInt(2, idExperienciaEducativa);
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
}
