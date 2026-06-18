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

    public EntregaDocumento buscarEntregaPorPracticanteYSolicitud(int idPracticante, int idSolicitud) throws SQLException {
        EntregaDocumento entrega = null;
        String consulta = "SELECT id_entrega_documento, archivo_entregado, fecha_entrega, estado, extension, nombre_archivo " +
                          "FROM entrega_documento WHERE id_practicante = ? AND id_solicitud_documento = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement ps = conexion.prepareStatement(consulta)) {
            
            ps.setInt(1, idPracticante);
            ps.setInt(2, idSolicitud);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entrega = new EntregaDocumento();
                    entrega.setIdEntregaDocumento(rs.getInt("id_entrega_documento"));
                    entrega.setArchivoEntregado(rs.getBytes("archivo_entregado"));
                    entrega.setFechaEntrega(rs.getDate("fecha_entrega"));
                    entrega.setEstado(rs.getString("estado"));
                    entrega.setExtension(rs.getString("extension"));
                    entrega.setNombreArchivo(rs.getString("nombre_archivo"));
                }
            }
        }
        return entrega;
    }

    public boolean cancelarEntrega(int idPracticante, int idSolicitudDocumento) throws SQLException {
        boolean eliminado = false;
        String consulta = "DELETE FROM entrega_documento WHERE id_practicante = ? AND id_solicitud_documento = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, idPracticante);
            ps.setInt(2, idSolicitudDocumento);
            eliminado = ps.executeUpdate() > 0;
        }
        return eliminado;
    }

    public boolean registrarEntrega(EntregaDocumento entrega) throws SQLException {
        boolean registrado = false;
        String consulta = "INSERT INTO entrega_documento (id_practicante, id_solicitud_documento, " +
                          "archivo_entregado, fecha_entrega, estado, extension, nombre_archivo) VALUES (?, ?, ?, ?, 'Pendiente de validacion', ?, ?)";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setInt(1, entrega.getPracticante().getIdPracticante());
            prepararSentencia.setInt(2, entrega.getSolicitudDocumento().getSolicitudDocumento());
            prepararSentencia.setBytes(3, entrega.getArchivoEntregado());
            prepararSentencia.setTimestamp(4, new java.sql.Timestamp(entrega.getFechaEntrega().getTime()));
            prepararSentencia.setString(5, entrega.getExtension());
            prepararSentencia.setString(6, entrega.getNombreArchivo());
            
            registrado = prepararSentencia.executeUpdate() > 0;
        }
        return registrado;
    }

    public List<EntregaDocumento> obtenerDocumentosPendientesPorPracticante(int idPracticante) throws SQLException {
        List<EntregaDocumento> listaEntregas = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT ed.id_entrega_documento, ed.fecha_entrega, ed.estado, ed.extension, ed.nombre_archivo, " +
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
                entrega.setExtension(resultado.getString("extension"));
                entrega.setNombreArchivo(resultado.getString("nombre_archivo"));
                
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

    public boolean evaluarEntrega(EntregaDocumento entrega) throws SQLException {
        boolean evaluado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "UPDATE entrega_documento SET calificacion = ?, retroalimentacion = ?, " +
                          "estado = 'Evaluado', id_profesor_evaluador = ? WHERE id_entrega_documento = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setDouble(1, entrega.getCalificacion());
            prepararSentencia.setString(2, entrega.getRetroalimentacion());
            prepararSentencia.setInt(3, entrega.getProfesorEvaluador().getIdPersonalAcademico());
            prepararSentencia.setInt(4, entrega.getIdEntregaDocumento());
            evaluado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return evaluado;
    }

    public List<EntregaDocumento> obtenerEntregasPorSolicitud(int idSolicitud) throws SQLException {
        List<EntregaDocumento> listaEntregas = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT p.id_practicante, p.nombres, p.apellido_paterno, p.apellido_materno, p.matricula, " +
                          "ed.id_entrega_documento, ed.fecha_entrega, ed.estado, ed.calificacion, ed.retroalimentacion, " +
                          "ed.nombre_archivo, ed.extension " +
                          "FROM practicante p " +
                          "INNER JOIN inscripcion_experiencia_educativa iee ON p.id_practicante = iee.id_practicante " +
                          "INNER JOIN solicitud_documento sd ON iee.id_experiencia_educativa = sd.id_experiencia_educativa " +
                          "LEFT JOIN entrega_documento ed ON p.id_practicante = ed.id_practicante AND ed.id_solicitud_documento = sd.id_solicitud_documento " +
                          "WHERE sd.id_solicitud_documento = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idSolicitud);
            resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                EntregaDocumento entrega = new EntregaDocumento();
                int idEntrega = resultado.getInt("id_entrega_documento");
                if (!resultado.wasNull()) {
                    entrega.setIdEntregaDocumento(idEntrega);
                    entrega.setFechaEntrega(resultado.getDate("fecha_entrega"));
                    entrega.setEstado(resultado.getString("estado"));
                    entrega.setCalificacion(resultado.getDouble("calificacion"));
                    entrega.setRetroalimentacion(resultado.getString("retroalimentacion"));
                    entrega.setNombreArchivo(resultado.getString("nombre_archivo"));
                    entrega.setExtension(resultado.getString("extension"));
                }
                
                mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante practicante = new mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante();
                practicante.setIdPracticante(resultado.getInt("id_practicante"));
                practicante.setNombres(resultado.getString("nombres"));
                practicante.setApellidoPaterno(resultado.getString("apellido_paterno"));
                practicante.setApellidoMaterno(resultado.getString("apellido_materno"));
                practicante.setMatricula(resultado.getString("matricula"));
                
                entrega.setPracticante(practicante);
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
}
