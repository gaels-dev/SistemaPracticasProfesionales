package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.TipoUsuario;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos del personal académico (profesores y coordinadores)
 */
public class PersonalAcademicoDAO {

    public int registrarPersonalAcademico(PersonalAcademico personal) throws SQLException {
        int idGenerado = -1;
        String consulta = "INSERT INTO personal_academico (no_personal, nombres, apellido_paterno, " +
                          "apellido_materno, correo, id_usuario, activo) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            prepararSentencia.setString(1, personal.getNoPersonal());
            prepararSentencia.setString(2, personal.getNombres());
            prepararSentencia.setString(3, personal.getApellidoPaterno());
            prepararSentencia.setString(4, personal.getApellidoMaterno());
            prepararSentencia.setString(5, personal.getCorreo());
            prepararSentencia.setInt(6, personal.getUsuario().getIdUsuario());
            prepararSentencia.setBoolean(7, personal.getActivo());
            
            prepararSentencia.executeUpdate();
            
            try (ResultSet resultado = prepararSentencia.getGeneratedKeys()) {
                if (resultado.next()) {
                    idGenerado = resultado.getInt(1);
                }
            }
        }
        return idGenerado;
    }

    public int registrarPersonalAcademico(PersonalAcademico personal, Connection conexion) throws SQLException {
        int idGenerado = -1;
        String consulta = "INSERT INTO personal_academico (no_personal, nombres, apellido_paterno, " +
                          "apellido_materno, correo, id_usuario, activo) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement prepararSentencia = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepararSentencia.setString(1, personal.getNoPersonal());
            prepararSentencia.setString(2, personal.getNombres());
            prepararSentencia.setString(3, personal.getApellidoPaterno());
            prepararSentencia.setString(4, personal.getApellidoMaterno());
            prepararSentencia.setString(5, personal.getCorreo());
            prepararSentencia.setInt(6, personal.getUsuario().getIdUsuario());
            prepararSentencia.setBoolean(7, personal.getActivo());
            
            prepararSentencia.executeUpdate();
            
            try (ResultSet resultado = prepararSentencia.getGeneratedKeys()) {
                if (resultado.next()) {
                    idGenerado = resultado.getInt(1);
                }
            }
        }
        return idGenerado;
    }

    public List<PersonalAcademico> obtenerPersonalAcademicoPorRol(String rol) throws SQLException {
        List<PersonalAcademico> listaPersonal = new ArrayList<>();
        String consulta = "SELECT pa.id_personal_academico, pa.no_personal, pa.nombres, pa.apellido_paterno, " +
                          "pa.apellido_materno, pa.correo, pa.activo, " +
                          "u.id_usuario, u.nombre, tu.id_tipo_usuario, tu.rol " +
                          "FROM personal_academico pa " +
                          "INNER JOIN usuario u ON pa.id_usuario = u.id_usuario " +
                          "INNER JOIN tipo_usuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario " +
                          "WHERE tu.rol = ? AND pa.activo = 1";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setString(1, rol);
            
            try (ResultSet resultado = prepararSentencia.executeQuery()) {
                while (resultado.next()) {
                    listaPersonal.add(mapearPersonalAcademico(resultado));
                }
            }
        }
        return listaPersonal;
    }

    public boolean actualizarPersonalAcademico(PersonalAcademico personal) throws SQLException {
        boolean actualizado = false;
        String consulta = "UPDATE personal_academico SET no_personal = ?, nombres = ?, apellido_paterno = ?, " +
                          "apellido_materno = ?, correo = ?, activo = ? " +
                          "WHERE id_personal_academico = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setString(1, personal.getNoPersonal());
            prepararSentencia.setString(2, personal.getNombres());
            prepararSentencia.setString(3, personal.getApellidoPaterno());
            prepararSentencia.setString(4, personal.getApellidoMaterno());
            prepararSentencia.setString(5, personal.getCorreo());
            prepararSentencia.setBoolean(6, personal.getActivo());
            prepararSentencia.setInt(7, personal.getIdPersonalAcademico());
            
            actualizado = prepararSentencia.executeUpdate() > 0;
        }
        return actualizado;
    }

    public boolean darDeBajaPersonalAcademico(int idPersonalAcademico) throws SQLException {
        boolean dadoDeBaja = false;
        String consulta = "UPDATE personal_academico SET activo = 0 WHERE id_personal_academico = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setInt(1, idPersonalAcademico);
            dadoDeBaja = prepararSentencia.executeUpdate() > 0;
        }
        return dadoDeBaja;
    }

    public boolean existePersonalPorNumeroYRol(String noPersonal, String rol) throws SQLException {
        boolean existe = false;
        String consulta = "SELECT COUNT(*) FROM personal_academico pa " +
                          "INNER JOIN usuario u ON pa.id_usuario = u.id_usuario " +
                          "INNER JOIN tipo_usuario tu ON u.id_tipo_usuario = tu.id_tipo_usuario " +
                          "WHERE pa.no_personal = ? AND tu.rol = ? AND pa.activo = 1";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setString(1, noPersonal);
            prepararSentencia.setString(2, rol);
            
            try (ResultSet resultado = prepararSentencia.executeQuery()) {
                if (resultado.next()) {
                    existe = resultado.getInt(1) > 0;
                }
            }
        }
        return existe;
    }

    private PersonalAcademico mapearPersonalAcademico(ResultSet resultado) throws SQLException {
        PersonalAcademico personal = new PersonalAcademico();
        personal.setIdPersonalAcademico(resultado.getInt("id_personal_academico"));
        personal.setNoPersonal(resultado.getString("no_personal"));
        personal.setNombres(resultado.getString("nombres"));
        personal.setApellidoPaterno(resultado.getString("apellido_paterno"));
        personal.setApellidoMaterno(resultado.getString("apellido_materno"));
        personal.setCorreo(resultado.getString("correo"));
        personal.setActivo(resultado.getBoolean("activo"));
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultado.getInt("id_usuario"));
        usuario.setNombre(resultado.getString("nombre"));
        
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setIdTipoUsuario(resultado.getInt("id_tipo_usuario"));
        tipoUsuario.setRol(resultado.getString("rol"));
        usuario.setTipoUsuario(tipoUsuario);
        
        personal.setUsuario(usuario);
        return personal;
    }
}
