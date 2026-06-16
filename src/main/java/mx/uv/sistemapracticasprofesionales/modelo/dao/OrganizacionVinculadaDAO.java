package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de las organizaciones vinculadas.
 */
public class OrganizacionVinculadaDAO {

    public List<OrganizacionVinculada> obtenerOrganizacionesActivas() throws SQLException {
        List<OrganizacionVinculada> listaOrganizaciones = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT id_organizacion_vinculada, razon_social, domicilio_fiscal, telefono, rfc, activo " +
                          "FROM organizacion_vinculada WHERE activo = 1";
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                OrganizacionVinculada ov = new OrganizacionVinculada();
                ov.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
                ov.setRazonSocial(resultado.getString("razon_social"));
                ov.setDomicilioFiscal(resultado.getString("domicilio_fiscal"));
                ov.setTelefono(resultado.getString("telefono"));
                ov.setRfc(resultado.getString("rfc"));
                ov.setActivo(resultado.getBoolean("activo"));
                listaOrganizaciones.add(ov);
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
        return listaOrganizaciones;
    }

    public boolean existeOrganizacionPorRfc(String rfc) throws SQLException {
        boolean existe = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT COUNT(*) FROM organizacion_vinculada WHERE rfc = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, rfc);
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

    public int registrarOrganizacion(OrganizacionVinculada organizacion) throws SQLException {
        int idGenerado = -1;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO organizacion_vinculada (razon_social, domicilio_fiscal, telefono, " +
                          "rfc, activo) VALUES (?, ?, ?, ?, 1)";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setString(1, organizacion.getRazonSocial());
            prepararSentencia.setString(2, organizacion.getDomicilioFiscal());
            prepararSentencia.setString(3, organizacion.getTelefono());
            prepararSentencia.setString(4, organizacion.getRfc());
            
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
