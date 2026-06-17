package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de los practicantes.
 */
public class PracticanteDAO {

    public Practicante buscarPorIdUsuario(int idUsuario) throws SQLException {
        Practicante practicante = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT p.id_practicante, p.matricula, p.nombres, p.apellido_paterno, " +
                          "p.apellido_materno, p.correo, p.sexo, p.activo, " +
                          "u.id_usuario, u.nombre " +
                          "FROM practicante p " +
                          "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                          "WHERE p.id_usuario = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idUsuario);
            resultado = prepararSentencia.executeQuery();
            
            if (resultado.next()) {
                practicante = mapearPracticante(resultado);
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
        return practicante;
    }

    public Practicante buscarPorMatricula(String matricula) throws SQLException {
        Practicante practicante = null;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT p.id_practicante, p.matricula, p.nombres, p.apellido_paterno, " +
                          "p.apellido_materno, p.correo, p.sexo, p.activo, " +
                          "u.id_usuario, u.nombre " +
                          "FROM practicante p " +
                          "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                          "WHERE p.matricula = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, matricula);
            resultado = prepararSentencia.executeQuery();
            
            if (resultado.next()) {
                practicante = mapearPracticante(resultado);
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
        return practicante;
    }

    public boolean existePracticanteActivoPorMatricula(String matricula) throws SQLException {
        boolean existe = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT COUNT(*) FROM practicante WHERE matricula = ? AND activo = 1";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, matricula);
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

    public int registrarPracticante(Practicante practicante) throws SQLException {
        int idGenerado = -1;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO practicante (matricula, nombres, apellido_paterno, " +
                          "apellido_materno, correo, sexo, id_usuario, activo) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setString(1, practicante.getMatricula());
            prepararSentencia.setString(2, practicante.getNombres());
            prepararSentencia.setString(3, practicante.getApellidoPaterno());
            prepararSentencia.setString(4, practicante.getApellidoMaterno());
            prepararSentencia.setString(5, practicante.getCorreo());
            prepararSentencia.setString(6, practicante.getSexo());
            prepararSentencia.setInt(7, practicante.getUsuario().getIdUsuario());
            prepararSentencia.setBoolean(8, practicante.getActivo());
            
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

    public int registrarPracticante(Practicante practicante, Connection conexion) throws SQLException {
        int idGenerado = -1;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO practicante (matricula, nombres, apellido_paterno, " +
                          "apellido_materno, correo, sexo, id_usuario, activo) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setString(1, practicante.getMatricula());
            prepararSentencia.setString(2, practicante.getNombres());
            prepararSentencia.setString(3, practicante.getApellidoPaterno());
            prepararSentencia.setString(4, practicante.getApellidoMaterno());
            prepararSentencia.setString(5, practicante.getCorreo());
            prepararSentencia.setString(6, practicante.getSexo());
            prepararSentencia.setInt(7, practicante.getUsuario().getIdUsuario());
            prepararSentencia.setBoolean(8, practicante.getActivo());
            
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

    public boolean reactivarPracticante(int idPracticante) throws SQLException {
        boolean reactivado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "UPDATE practicante SET activo = 1 WHERE id_practicante = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idPracticante);
            reactivado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return reactivado;
    }

    public List<Practicante> obtenerPracticantesInscritosPeriodoActual() throws SQLException {
        List<Practicante> listaPracticantes = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        
        // NOTA: Temporalmente esta consulta trae a todos los practicantes activos,
        // ya que la lógica de "Inscripción a Experiencia Educativa" aún no está implementada en los datos de la BD
        String consulta = "SELECT p.id_practicante, p.matricula, p.nombres, p.apellido_paterno, " +
                          "p.apellido_materno, p.correo, p.sexo, p.activo, " +
                          "u.id_usuario, u.nombre " +
                          "FROM practicante p " +
                          "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                          "WHERE p.activo = 1 " +
                          "ORDER BY p.nombres ASC, p.apellido_paterno ASC";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                listaPracticantes.add(mapearPracticante(resultado));
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
        return listaPracticantes;
    }

    public List<Practicante> obtenerPracticantesDisponiblesParaProyecto() throws SQLException {
        List<Practicante> listaPracticantes = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        
        // NOTA: Esta consulta está modificada para evitar verificar la tabla de inscripciones
        // ya que esa lógica de esta sección aún no está.
        String consulta = "SELECT p.id_practicante, p.matricula, p.nombres, p.apellido_paterno, " +
                          "p.apellido_materno, p.correo, p.sexo, p.activo, " +
                          "u.id_usuario, u.nombre " +
                          "FROM practicante p " +
                          "INNER JOIN usuario u ON p.id_usuario = u.id_usuario " +
                          "WHERE p.activo = 1 " +
                          "  AND NOT EXISTS ( " +
                          "      SELECT 1 FROM asignacion_proyecto ap " +
                          "      WHERE ap.id_practicante = p.id_practicante " +
                          "        AND ap.estado = 'Activa' " +
                          ")";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                listaPracticantes.add(mapearPracticante(resultado));
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
        return listaPracticantes;
    }

    public boolean practicanteTieneProyectoActivo(int idPracticante) throws SQLException {
        boolean tiene = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT COUNT(*) FROM asignacion_proyecto WHERE id_practicante = ? AND estado = 'Activa'";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idPracticante);
            resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                tiene = resultado.getInt(1) > 0;
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
        return tiene;
    }

    public boolean actualizarPracticante(Practicante practicante) throws SQLException {
        boolean actualizado = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        String consulta = "UPDATE practicante SET matricula = ?, nombres = ?, apellido_paterno = ?, " +
                          "apellido_materno = ?, correo = ?, sexo = ?, activo = ? " +
                          "WHERE id_practicante = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setString(1, practicante.getMatricula());
            prepararSentencia.setString(2, practicante.getNombres());
            prepararSentencia.setString(3, practicante.getApellidoPaterno());
            prepararSentencia.setString(4, practicante.getApellidoMaterno());
            prepararSentencia.setString(5, practicante.getCorreo());
            prepararSentencia.setString(6, practicante.getSexo());
            prepararSentencia.setBoolean(7, practicante.getActivo());
            prepararSentencia.setInt(8, practicante.getIdPracticante());
            
            actualizado = prepararSentencia.executeUpdate() > 0;
        } finally {
            if (prepararSentencia != null) {
                prepararSentencia.close();
            }
            ConexionBD.cerrarConexion(conexion);
        }
        return actualizado;
    }

    private Practicante mapearPracticante(ResultSet resultado) throws SQLException {
        Practicante practicante = new Practicante();
        practicante.setIdPracticante(resultado.getInt("id_practicante"));
        practicante.setMatricula(resultado.getString("matricula"));
        practicante.setNombres(resultado.getString("nombres"));
        practicante.setApellidoPaterno(resultado.getString("apellido_paterno"));
        practicante.setApellidoMaterno(resultado.getString("apellido_materno"));
        practicante.setCorreo(resultado.getString("correo"));
        practicante.setSexo(resultado.getString("sexo"));
        practicante.setActivo(resultado.getBoolean("activo"));
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultado.getInt("id_usuario"));
        usuario.setNombre(resultado.getString("nombre"));
        practicante.setUsuario(usuario);
        
        return practicante;
    }
}
