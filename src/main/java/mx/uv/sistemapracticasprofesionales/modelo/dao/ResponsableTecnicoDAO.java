package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ResponsableTecnico;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de los responsables técnicos de las organizaciones.
 */
public class ResponsableTecnicoDAO {

    public ResponsableTecnico buscarResponsablePorCorreo(String correo) throws SQLException {
        ResponsableTecnico responsable = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT id_responsable_tecnico, nombres, apellido_paterno, apellido_materno, " +
                          "correo, id_organizacion_vinculada FROM responsable_tecnico WHERE correo = ?";
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, correo);
            resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                responsable = new ResponsableTecnico();
                responsable.setIdResponsableTecnico(resultado.getInt("id_responsable_tecnico"));
                responsable.setNombres(resultado.getString("nombres"));
                responsable.setApellidoPaterno(resultado.getString("apellido_paterno"));
                responsable.setApellidoMaterno(resultado.getString("apellido_materno"));
                responsable.setCorreo(resultado.getString("correo"));
                
                OrganizacionVinculada ov = new OrganizacionVinculada();
                ov.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
                responsable.setOrganizacionVinculada(ov);
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
        return responsable;
    }

    public int registrarResponsableTecnico(ResponsableTecnico responsable) throws SQLException {
        int idGenerado = -1;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO responsable_tecnico (nombres, apellido_paterno, apellido_materno, " +
                          "correo, id_organizacion_vinculada) VALUES (?, ?, ?, ?, ?)";
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setString(1, responsable.getNombres());
            prepararSentencia.setString(2, responsable.getApellidoPaterno());
            prepararSentencia.setString(3, responsable.getApellidoMaterno());
            prepararSentencia.setString(4, responsable.getCorreo());
            prepararSentencia.setInt(5, responsable.getOrganizacionVinculada().getIdOrganizacionVinculada());
            
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

    public ResponsableTecnico obtenerResponsablePorID(int idResponsableTecnico) throws SQLException {
        ResponsableTecnico responsable = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT id_responsable_tecnico, nombres, apellido_paterno, apellido_materno, " +
                          "correo, id_organizacion_vinculada FROM responsable_tecnico WHERE id_responsable_tecnico = ?";
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idResponsableTecnico);
            resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                responsable = new ResponsableTecnico();
                responsable.setIdResponsableTecnico(resultado.getInt("id_responsable_tecnico"));
                responsable.setNombres(resultado.getString("nombres"));
                responsable.setApellidoPaterno(resultado.getString("apellido_paterno"));
                responsable.setApellidoMaterno(resultado.getString("apellido_materno"));
                responsable.setCorreo(resultado.getString("correo"));
                
                OrganizacionVinculada ov = new OrganizacionVinculada();
                ov.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
                responsable.setOrganizacionVinculada(ov);
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
        return responsable;
    }
}
