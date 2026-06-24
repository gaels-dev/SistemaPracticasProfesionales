package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.TipoUsuario;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Clase para el acceso a datos de los usuarios del sistema.
 */
public class UsuarioDAO {

    public Usuario autenticar(String nombre, String contrasenia) throws SQLException {
        Usuario usuario = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT u.id_usuario, u.nombre, u.id_tipo_usuario, u.activo, tu.rol " +
                          "FROM usuario u " +
                          "INNER JOIN tipo_usuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario " +
                          "WHERE u.nombre = ? AND u.contrasenia = ? AND u.activo = 1";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, nombre);
            prepararSentencia.setString(2, contrasenia);
            resultado = prepararSentencia.executeQuery();
            
            if (resultado.next()) {
                usuario = mapearUsuario(resultado);
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
        return usuario;
    }

    public boolean existeUsuarioPorNombre(String nombre) throws SQLException {
        boolean existe = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT COUNT(*) FROM usuario WHERE nombre = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, nombre);
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

    public Usuario buscarPorNombre(String nombre) throws SQLException {
        Usuario usuario = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT u.id_usuario, u.nombre, u.contrasenia, u.id_tipo_usuario, u.activo, tu.rol " +
                          "FROM usuario u " +
                          "INNER JOIN tipo_usuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario " +
                          "WHERE u.nombre = ? AND u.activo = 1";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, nombre);
            resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                usuario = mapearUsuario(resultado);
                usuario.setContrasenia(resultado.getString("contrasenia"));
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
        return usuario;
    }

    public Usuario obtenerPorId(int idUsuario) throws SQLException {
        Usuario usuario = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT u.id_usuario, u.nombre, u.contrasenia, u.id_tipo_usuario, u.activo, tu.rol " +
                          "FROM usuario u " +
                          "INNER JOIN tipo_usuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario " +
                          "WHERE u.id_usuario = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idUsuario);
            resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                usuario = mapearUsuario(resultado);
                usuario.setContrasenia(resultado.getString("contrasenia"));
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
        return usuario;
    }

    private Usuario mapearUsuario(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultado.getInt("id_usuario"));
        usuario.setNombre(resultado.getString("nombre"));
        usuario.setActivo(resultado.getBoolean("activo"));

        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setIdTipoUsuario(resultado.getInt("id_tipo_usuario"));
        tipoUsuario.setRol(resultado.getString("rol"));
        usuario.setTipoUsuario(tipoUsuario);
        return usuario;
    }

    public int registrarUsuario(Usuario usuario) throws SQLException {
        int idGenerado = -1;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO usuario (nombre, contrasenia, id_tipo_usuario, activo) " +
                          "VALUES (?, ?, ?, ?)";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setString(1, usuario.getNombre());
            prepararSentencia.setString(2, usuario.getContrasenia());
            prepararSentencia.setInt(3, usuario.getTipoUsuario().getIdTipoUsuario());
            prepararSentencia.setBoolean(4, usuario.getActivo());
            
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

    public int registrarUsuario(Usuario usuario, Connection conexion) throws SQLException {
        int idGenerado = -1;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO usuario (nombre, contrasenia, id_tipo_usuario, activo) " +
                          "VALUES (?, ?, ?, ?)";
        
        try {
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setString(1, usuario.getNombre());
            prepararSentencia.setString(2, usuario.getContrasenia());
            prepararSentencia.setInt(3, usuario.getTipoUsuario().getIdTipoUsuario());
            prepararSentencia.setBoolean(4, usuario.getActivo());
            
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

    public boolean activarUsuario(int idUsuario) throws SQLException {
        boolean reactivado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "UPDATE usuario SET activo = 1 WHERE id_usuario = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idUsuario);
            reactivado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return reactivado;
    }

    public boolean desactivarUsuario(int idUsuario) throws SQLException {
        boolean desactivado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "UPDATE usuario SET activo = 0 WHERE id_usuario = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idUsuario);
            desactivado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return desactivado;
    }

    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        boolean actualizado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "UPDATE usuario SET nombre = ?, contrasenia = ?, id_tipo_usuario = ?, activo = ? " +
                          "WHERE id_usuario = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, usuario.getNombre());
            prepararSentencia.setString(2, usuario.getContrasenia());
            prepararSentencia.setInt(3, usuario.getTipoUsuario().getIdTipoUsuario());
            prepararSentencia.setBoolean(4, usuario.getActivo());
            prepararSentencia.setInt(5, usuario.getIdUsuario());
            
            actualizado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return actualizado;
    }
}
