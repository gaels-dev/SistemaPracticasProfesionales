package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Documento;


/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de los documentos (catálogo).
 */
public class DocumentoDAO {

    public List<Documento> obtenerDocumentosActivos() throws SQLException {
        List<Documento> listaDocumentos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT id_documento, nombre_documento, tipo_documento, calificacion_maxima, " +
                          "formato, activo FROM documento WHERE activo = 1";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            resultado = prepararSentencia.executeQuery();
            
            while (resultado.next()) {
                Documento documento = new Documento();
                documento.setIdDocumento(resultado.getInt("id_documento"));
                documento.setNombreDocumento(resultado.getString("nombre_documento"));
                documento.setTipoDocumento(resultado.getString("tipo_documento"));
                documento.setCalificacionMaxima(resultado.getDouble("calificacion_maxima"));
                documento.setFormato(resultado.getBytes("formato"));
                documento.setActivo(resultado.getBoolean("activo"));
                listaDocumentos.add(documento);
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
        return listaDocumentos;
    }

    public Documento obtenerDocumentoPorId(int idDocumento) throws SQLException {
        Documento documento = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT id_documento, nombre_documento, tipo_documento, calificacion_maxima, " +
                "formato, activo FROM documento WHERE id_documento = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idDocumento);
            resultado = prepararSentencia.executeQuery();
            
            if (resultado.next()) {
                documento = new Documento();
                documento.setIdDocumento(resultado.getInt("id_documento"));
                documento.setNombreDocumento(resultado.getString("nombre_documento"));
                documento.setTipoDocumento(resultado.getString("tipo_documento"));
                documento.setCalificacionMaxima(resultado.getDouble("calificacion_maxima"));
                documento.setFormato(resultado.getBytes("formato"));
                documento.setActivo(resultado.getBoolean("activo"));
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
        return documento;
    }
}
