package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Documento;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de las solicitudes de documentos.
 */
public class SolicitudDocumentoDAO {

    public int registrarSolicitud(SolicitudDocumento solicitud) throws SQLException {
        int idGenerado = -1;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO solicitud_documento (fecha_limite, id_documento, id_experiencia_educativa) VALUES (?, ?, ?)";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setTimestamp(1, new Timestamp(solicitud.getFechaLimite().getTime()));
            prepararSentencia.setInt(2, solicitud.getDocumento().getIdDocumento());
            prepararSentencia.setInt(3, solicitud.getExperienciaEducativa().getIdExperienciaEducativa());
            
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

    public List<SolicitudDocumento> obtenerSolicitudesPorExperiencia(int idExperiencia) throws SQLException {
        List<SolicitudDocumento> listaSolicitudes = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT sd.id_solicitud_documento, sd.fecha_limite, d.id_documento, d.nombre_documento, d.tipo_documento " +
                          "FROM solicitud_documento sd " +
                          "INNER JOIN documento d ON sd.id_documento = d.id_documento " +
                          "WHERE sd.id_experiencia_educativa = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idExperiencia);
            resultado = prepararSentencia.executeQuery();
            
            while (resultado.next()) {
                SolicitudDocumento solicitud = new SolicitudDocumento();
                solicitud.setSolicitudDocumento(resultado.getInt("id_solicitud_documento"));
                solicitud.setFechaLimite(resultado.getTimestamp("fecha_limite"));
                
                Documento documento = new Documento();
                documento.setIdDocumento(resultado.getInt("id_documento"));
                documento.setNombreDocumento(resultado.getString("nombre_documento"));
                documento.setTipoDocumento(resultado.getString("tipo_documento"));
                
                solicitud.setDocumento(documento);
                listaSolicitudes.add(solicitud);
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
        return listaSolicitudes;
    }
}
