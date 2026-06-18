package mx.uv.sistemapracticasprofesionales.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.configuracion.ConexionBD;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ExperienciaEducativa;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Periodo;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de las experiencias educativas (cursos)
 */
public class ExperienciaEducativaDAO {

    public int registrarExperienciaEducativa(ExperienciaEducativa ee) throws SQLException {
        int idGenerado = -1;
        String consulta = "INSERT INTO experiencia_educativa (nombre, horario, seccion, cupo_maximo, id_periodo, id_profesor) " +
                          "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            prepararSentencia.setString(1, ee.getNombre());
            prepararSentencia.setBytes(2, ee.getHorario());
            prepararSentencia.setString(3, ee.getSeccion());
            prepararSentencia.setInt(4, ee.getCupoMaximo());
            prepararSentencia.setInt(5, ee.getPeriodo().getIdPeriodo());
            
            if (ee.getProfesor() != null && ee.getProfesor().getIdPersonalAcademico() != null) {
                prepararSentencia.setInt(6, ee.getProfesor().getIdPersonalAcademico());
            } else {
                prepararSentencia.setNull(6, Types.INTEGER);
            }
            
            prepararSentencia.executeUpdate();
            
            try (ResultSet resultado = prepararSentencia.getGeneratedKeys()) {
                if (resultado.next()) {
                    idGenerado = resultado.getInt(1);
                }
            }
        }
        return idGenerado;
    }

    public List<ExperienciaEducativa> obtenerExperienciasPorPeriodo(int idPeriodo) throws SQLException {
        List<ExperienciaEducativa> listaEE = new ArrayList<>();
        String consulta = "SELECT ee.id_experiencia_educativa AS id_ee, ee.nombre AS nombre_ee, ee.horario AS horario_ee, " +
                          "ee.seccion AS seccion_ee, ee.cupo_maximo AS cupo_ee, " +
                          "p.id_periodo AS id_p, p.nombre AS nombre_p, " +
                          "pa.id_personal_academico AS id_pa, pa.nombres AS nombre_pa, pa.apellido_paterno AS paterno_pa, " +
                          "pa.apellido_materno AS materno_pa FROM experiencia_educativa ee " +
                          "INNER JOIN periodo p ON ee.id_periodo = p.id_periodo " +
                          "LEFT JOIN personal_academico pa ON ee.id_profesor = pa.id_personal_academico " +
                          "WHERE ee.id_periodo = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setInt(1, idPeriodo);
            
            try (ResultSet resultado = prepararSentencia.executeQuery()) {
                while (resultado.next()) {
                    listaEE.add(mapearExperienciaEducativa(resultado));
                }
            }
        }
        return listaEE;
    }

    public boolean asignarProfesor(int idExperiencia, int idProfesor) throws SQLException {
        boolean asignado = false;
        String consulta = "UPDATE experiencia_educativa SET id_profesor = ? WHERE id_experiencia_educativa = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setInt(1, idProfesor);
            prepararSentencia.setInt(2, idExperiencia);
            asignado = prepararSentencia.executeUpdate() > 0;
        }
        return asignado;
    }

    public boolean actualizarExperienciaEducativa(ExperienciaEducativa ee) throws SQLException {
        boolean actualizado = false;
        String consulta = "UPDATE experiencia_educativa SET nombre = ?, horario = ?, seccion = ?, cupo_maximo = ?, " +
                          "id_periodo = ?, id_profesor = ? " +
                          "WHERE id_experiencia_educativa = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setString(1, ee.getNombre());
            prepararSentencia.setBytes(2, ee.getHorario());
            prepararSentencia.setString(3, ee.getSeccion());
            prepararSentencia.setInt(4, ee.getCupoMaximo());
            prepararSentencia.setInt(5, ee.getPeriodo().getIdPeriodo());
            
            if (ee.getProfesor() != null && ee.getProfesor().getIdPersonalAcademico() != null) {
                prepararSentencia.setInt(6, ee.getProfesor().getIdPersonalAcademico());
            } else {
                prepararSentencia.setNull(6, Types.INTEGER);
            }
            
            prepararSentencia.setInt(7, ee.getIdExperienciaEducativa());
            
            actualizado = prepararSentencia.executeUpdate() > 0;
        }
        return actualizado;
    }

    public List<ExperienciaEducativa> obtenerExperienciasPorProfesor(int idProfesor) throws SQLException {
        List<ExperienciaEducativa> listaEE = new ArrayList<>();
        String consulta = "SELECT ee.id_experiencia_educativa AS id_ee, ee.nombre AS nombre_ee, ee.horario AS horario_ee, " +
                          "ee.seccion AS seccion_ee, ee.cupo_maximo AS cupo_ee, " +
                          "p.id_periodo AS id_p, p.nombre AS nombre_p, " +
                          "pa.id_personal_academico AS id_pa, pa.nombres AS nombre_pa, pa.apellido_paterno AS paterno_pa, " +
                          "pa.apellido_materno AS materno_pa FROM experiencia_educativa ee " +
                          "INNER JOIN periodo p ON ee.id_periodo = p.id_periodo " +
                          "LEFT JOIN personal_academico pa ON ee.id_profesor = pa.id_personal_academico " +
                          "WHERE ee.id_profesor = ?";
        
        try (Connection conexion = ConexionBD.abrirConexion();
             PreparedStatement prepararSentencia = conexion.prepareStatement(consulta)) {
            
            prepararSentencia.setInt(1, idProfesor);
            
            try (ResultSet resultado = prepararSentencia.executeQuery()) {
                while (resultado.next()) {
                    listaEE.add(mapearExperienciaEducativa(resultado));
                }
            }
        }
        return listaEE;
    }

    private ExperienciaEducativa mapearExperienciaEducativa(ResultSet resultado) throws SQLException {
        ExperienciaEducativa ee = new ExperienciaEducativa();
        ee.setIdExperienciaEducativa(resultado.getInt("id_ee"));
        ee.setNombre(resultado.getString("nombre_ee"));
        ee.setHorario(resultado.getBytes("horario_ee"));
        ee.setSeccion(resultado.getString("seccion_ee"));
        ee.setCupoMaximo(resultado.getInt("cupo_ee"));
        
        Periodo periodo = new Periodo();
        periodo.setIdPeriodo(resultado.getInt("id_p"));
        periodo.setNombre(resultado.getString("nombre_p"));
        ee.setPeriodo(periodo);
        
        int idProfesor = resultado.getInt("id_pa");
        if (!resultado.wasNull()) {
            PersonalAcademico profesor = new PersonalAcademico();
            profesor.setIdPersonalAcademico(idProfesor);
            profesor.setNombres(resultado.getString("nombre_pa"));
            profesor.setApellidoPaterno(resultado.getString("paterno_pa"));
            profesor.setApellidoMaterno(resultado.getString("materno_pa"));
            ee.setProfesor(profesor);
        }
        
        return ee;
    }
}
