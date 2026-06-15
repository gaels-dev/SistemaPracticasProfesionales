package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.TipoUsuario;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Clase para el acceso a datos de los tipos de usuario
 */
public class TipoUsuarioDAO {

    public List<TipoUsuario> obtenerTiposUsuario() throws SQLException {
        List<TipoUsuario> tiposUsuario = new ArrayList<>();
        String consulta = "SELECT id_tipo_usuario, rol FROM tipo_usuario";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
             ResultSet resultado = prepararSentencia.executeQuery()) {
            
            while (resultado.next()) {
                TipoUsuario tipoUsuario = new TipoUsuario();
                tipoUsuario.setIdTipoUsuario(resultado.getInt("id_tipo_usuario"));
                tipoUsuario.setRol(resultado.getString("rol"));
                tiposUsuario.add(tipoUsuario);
            }
        }
        return tiposUsuario;
    }

    public TipoUsuario obtenerTipoUsuarioPorId(int idTipoUsuario) throws SQLException {
        TipoUsuario tipoUsuario = null;
        String consulta = "SELECT id_tipo_usuario, rol FROM tipo_usuario WHERE id_tipo_usuario = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setInt(1, idTipoUsuario);
            try (ResultSet resultado = prepararSentencia.executeQuery()) {
                if (resultado.next()) {
                    tipoUsuario = new TipoUsuario();
                    tipoUsuario.setIdTipoUsuario(resultado.getInt("id_tipo_usuario"));
                    tipoUsuario.setRol(resultado.getString("rol"));
                }
            }
        }
        return tipoUsuario;
    }
}
