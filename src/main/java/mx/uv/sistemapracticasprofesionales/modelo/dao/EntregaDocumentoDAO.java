package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Documento;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.EntregaDocumento;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de las entregas de documentos.
 */
public class EntregaDocumentoDAO {

    public List<EntregaDocumento> obtenerDocumentosPendientesPorPracticante(int idPracticante) throws SQLException {
        List<EntregaDocumento> listaEntregas = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT ed.id_entrega_documento, ed.fecha_entrega, ed.estado, " +
                          "sd.id_solicitud_documento, sd.fecha_limite, " +
                          "d.id_documento, d.nombre_documento " +
                          "FROM entrega_documento ed " +
                          "INNER JOIN solicitud_documento sd ON ed.id_solicitud_documento = sd.id_solicitud_documento " +
                          "INNER JOIN documento d ON sd.id_documento = d.id_documento " +
                          "WHERE ed.id_practicante = ? AND ed.estado = 'Pendiente de validacion'";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idPracticante);
            resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                EntregaDocumento entrega = new EntregaDocumento();
                entrega.setIdEntregaDocumento(resultado.getInt("id_entrega_documento"));
                entrega.setFechaEntrega(resultado.getDate("fecha_entrega"));
                entrega.setEstado(resultado.getString("estado"));
                
                SolicitudDocumento solicitud = new SolicitudDocumento();
                solicitud.setSolicitudDocumento(resultado.getInt("id_solicitud_documento"));
                solicitud.setFechaLimite(resultado.getDate("fecha_limite"));
                
                Documento documento = new Documento();
                documento.setIdDocumento(resultado.getInt("id_documento"));
                documento.setNombreDocumento(resultado.getString("nombre_documento"));
                solicitud.setDocumento(documento);
                
                entrega.setSolicitudDocumento(solicitud);
                listaEntregas.add(entrega);
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
        return listaEntregas;
    }

    public byte[] obtenerArchivoEntregado(int idEntregaDocumento) throws SQLException {
        byte[] archivo = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT archivo_entregado FROM entrega_documento WHERE id_entrega_documento = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idEntregaDocumento);
            resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                archivo = resultado.getBytes("archivo_entregado");
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
        return archivo;
    }

    public boolean validarDocumento(int idEntregaDocumento) throws SQLException {
        boolean validado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "UPDATE entrega_documento SET estado = 'Validado' WHERE id_entrega_documento = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idEntregaDocumento);
            validado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return validado;
    }

    public boolean rechazarDocumento(int idEntregaDocumento, String motivoRechazo) throws SQLException {
        boolean rechazado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "UPDATE entrega_documento SET estado = 'Rechazado', motivo_rechazo = ? WHERE id_entrega_documento = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, motivoRechazo);
            prepararSentencia.setInt(2, idEntregaDocumento);
            rechazado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return rechazado;
    }
}
