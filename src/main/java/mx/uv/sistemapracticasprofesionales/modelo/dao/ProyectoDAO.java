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
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Proyecto;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ResponsableTecnico;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para el acceso a datos de los proyectos de prácticas.
 */
public class ProyectoDAO {

    public int registrarProyecto(Proyecto proyecto) throws SQLException {
        int idGenerado = -1;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "INSERT INTO proyecto (nombre, descripcion, cupo_maximo, id_periodo, " +
                          "id_organizacion_vinculada, id_responsable_tecnico, activo) " +
                          "VALUES (?, ?, ?, ?, ?, ?, 1)";
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            prepararSentencia.setString(1, proyecto.getNombre());
            prepararSentencia.setString(2, proyecto.getDescripcion());
            prepararSentencia.setInt(3, proyecto.getCupoMaximo());
            prepararSentencia.setInt(4, proyecto.getPeriodo().getIdPeriodo());
            prepararSentencia.setInt(5, proyecto.getOrganizacionVinculada().getIdOrganizacionVinculada());
            prepararSentencia.setInt(6, proyecto.getResponsableTecnico().getIdResponsableTecnico());
            
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

    public List<Proyecto> obtenerTodosProyectosActivos() throws SQLException {
        List<Proyecto> listaProyectos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT p.id_proyecto, p.nombre, p.descripcion, p.cupo_maximo, p.activo, " +
                          "ov.id_organizacion_vinculada, ov.razon_social, " +
                          "rt.id_responsable_tecnico, rt.nombres AS nombres_rt, rt.apellido_paterno AS paterno_rt, " +
                          "(SELECT COUNT(*) FROM asignacion_proyecto ap WHERE ap.id_proyecto = p.id_proyecto AND ap.estado = 'Activa') AS asignados " +
                          "FROM proyecto p " +
                          "INNER JOIN organizacion_vinculada ov ON p.id_organizacion_vinculada = ov.id_organizacion_vinculada " +
                          "INNER JOIN responsable_tecnico rt ON p.id_responsable_tecnico = rt.id_responsable_tecnico " +
                          "INNER JOIN periodo per ON p.id_periodo = per.id_periodo " +
                          "WHERE p.activo = 1 AND per.cerrado = 0 " +
                          "ORDER BY p.nombre ASC";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                Proyecto proyecto = mapearProyecto(resultado);
                proyecto.setPracticantesAsignados(resultado.getInt("asignados"));
                listaProyectos.add(proyecto);
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
        return listaProyectos;
    }

    public List<Proyecto> obtenerProyectosActivosConCupo() throws SQLException {
        List<Proyecto> listaProyectos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT p.id_proyecto, p.nombre, p.descripcion, p.cupo_maximo, p.activo, " +
                          "ov.id_organizacion_vinculada, ov.razon_social, " +
                          "rt.id_responsable_tecnico, rt.nombres AS nombres_rt, rt.apellido_paterno AS paterno_rt " +
                          "FROM proyecto p " +
                          "INNER JOIN organizacion_vinculada ov ON p.id_organizacion_vinculada = ov.id_organizacion_vinculada " +
                          "INNER JOIN responsable_tecnico rt ON p.id_responsable_tecnico = rt.id_responsable_tecnico " +
                          "INNER JOIN periodo per ON p.id_periodo = per.id_periodo " +
                          "WHERE p.activo = 1 AND per.cerrado = 0 " +
                          "  AND p.cupo_maximo > ( " +
                          "      SELECT COUNT(*) FROM asignacion_proyecto ap " +
                          "      WHERE ap.id_proyecto = p.id_proyecto " +
                          "        AND ap.estado = 'Activa' " +
                          "  )";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            resultado = prepararSentencia.executeQuery();
            while (resultado.next()) {
                listaProyectos.add(mapearProyecto(resultado));
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
        return listaProyectos;
    }

    public boolean proyectoTieneCupoDisponible(int idProyecto) throws SQLException {
        boolean tieneCupo = false;
        Connection conexion = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;
        String consulta = "SELECT (p.cupo_maximo - (SELECT COUNT(*) FROM asignacion_proyecto ap " +
                          "WHERE ap.id_proyecto = p.id_proyecto AND ap.estado = 'Activa')) AS cupo_disponible " +
                          "FROM proyecto p WHERE p.id_proyecto = ?";
        
        try {
            conexion = ConexionBD.abrirConexion();
            prepararSentencia = conexion.prepareStatement(consulta);
            prepararSentencia.setInt(1, idProyecto);
            resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                tieneCupo = resultado.getInt("cupo_disponible") > 0;
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
        return tieneCupo;
    }

    private Proyecto mapearProyecto(ResultSet resultado) throws SQLException {
        Proyecto proyecto = new Proyecto();
        proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
        proyecto.setNombre(resultado.getString("nombre"));
        proyecto.setDescripcion(resultado.getString("descripcion"));
        proyecto.setCupoMaximo(resultado.getInt("cupo_maximo"));
        proyecto.setActivo(resultado.getBoolean("activo"));
        
        OrganizacionVinculada ov = new OrganizacionVinculada();
        ov.setIdOrganizacionVinculada(resultado.getInt("id_organizacion_vinculada"));
        ov.setRazonSocial(resultado.getString("razon_social"));
        proyecto.setOrganizacionVinculada(ov);
        
        ResponsableTecnico rt = new ResponsableTecnico();
        rt.setIdResponsableTecnico(resultado.getInt("id_responsable_tecnico"));
        rt.setNombres(resultado.getString("nombres_rt"));
        rt.setApellidoPaterno(resultado.getString("paterno_rt"));
        proyecto.setResponsableTecnico(rt);
        
        return proyecto;
    }
}
