package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.SQLException;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.modelo.dao.OrganizacionVinculadaDAO;
import mx.uv.sistemapracticasprofesionales.modelo.dao.PeriodoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.dao.ProyectoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.dao.ResponsableTecnicoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Periodo;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Proyecto;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ResponsableTecnico;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Servicio para gestionar la lógica de negocio de los proyectos.
 */
public class ProyectoService {

    private final ProyectoDAO proyectoDAO = new ProyectoDAO();
    private final OrganizacionVinculadaDAO organizacionDAO = new OrganizacionVinculadaDAO();
    private final ResponsableTecnicoDAO responsableDAO = new ResponsableTecnicoDAO();
    private final PeriodoDAO periodoDAO = new PeriodoDAO();

    public List<OrganizacionVinculada> obtenerOrganizacionesActivas() throws SQLException {
        return organizacionDAO.obtenerOrganizacionesActivas();
    }

    public Periodo obtenerPeriodoActual() throws SQLException {
        return periodoDAO.obtenerPeriodoAbierto();
    }

    public List<Proyecto> obtenerTodosProyectosActivos() throws SQLException {
        return proyectoDAO.obtenerTodosProyectosActivos();
    }

    public List<Proyecto> obtenerProyectosActivosConCupo() throws SQLException {
        return proyectoDAO.obtenerProyectosActivosConCupo();
    }

    public boolean registrarProyecto(Proyecto proyecto) throws SQLException { 
        ResponsableTecnico rt = proyecto.getResponsableTecnico();
        ResponsableTecnico existente = responsableDAO.buscarResponsablePorCorreo(rt.getCorreo());
        
        int idResponsable;
        if (existente != null) {
            idResponsable = existente.getIdResponsableTecnico();
        } else {
            idResponsable = responsableDAO.registrarResponsableTecnico(rt);
        }
        
        if (idResponsable > 0) {
            proyecto.getResponsableTecnico().setIdResponsableTecnico(idResponsable);
            int idProyecto = proyectoDAO.registrarProyecto(proyecto);
            return idProyecto > 0;
        }
        return false;
    }
    
    public String validarProyecto(Proyecto proyecto) {
        StringBuilder errores = new StringBuilder();
        
        if (proyecto.getNombre() == null || proyecto.getNombre().trim().isEmpty()) {
            errores.append("- Nombre del proyecto es obligatorio.\n");
        } else if (proyecto.getNombre().length() > 50) {
            errores.append("- El nombre del proyecto no puede exceder los 50 caracteres.\n");
        }
        if (proyecto.getDescripcion() == null || proyecto.getDescripcion().trim().isEmpty()) {
            errores.append("- Descripción del proyecto es obligatoria.\n");
        } else if (proyecto.getDescripcion().length() > 200) {
            errores.append("- La descripción no puede exceder los 200 caracteres.\n");
        }
        if (proyecto.getCupoMaximo() == null || proyecto.getCupoMaximo() <= 0) {
            errores.append("- El cupo máximo debe ser mayor a 0.\n");
        } else if (proyecto.getCupoMaximo() > 20) {
            errores.append("- El cupo máximo no puede exceder las 20 vacantes.\n");
        }
        if (proyecto.getOrganizacionVinculada() == null) {
            errores.append("- Debe seleccionar una organización vinculada.\n");
        }
        if (proyecto.getPeriodo() == null) {
            errores.append("- No hay un periodo escolar abierto disponible.\n");
        }
        
        ResponsableTecnico rt = proyecto.getResponsableTecnico();
        if (rt == null) {
            errores.append("- Datos del responsable técnico son obligatorios.\n");
        } else {
            if (rt.getNombres() == null || rt.getNombres().trim().isEmpty()) {
                errores.append("- Nombre del responsable es obligatorio.\n");
            } else if (rt.getNombres().length() > 50) {
                errores.append("- El nombre del responsable no puede exceder los 50 caracteres.\n");
            }
            if (rt.getApellidoPaterno() == null || rt.getApellidoPaterno().trim().isEmpty()) {
                errores.append("- Apellido paterno del responsable es obligatorio.\n");
            } else if (rt.getApellidoPaterno().length() > 40) {
                errores.append("- El apellido paterno no puede exceder los 40 caracteres.\n");
            }
            if (rt.getApellidoMaterno() == null || rt.getApellidoMaterno().trim().isEmpty()) {
                errores.append("- Apellido materno del responsable es obligatorio.\n");
            } else if (rt.getApellidoMaterno().length() > 40) {
                errores.append("- El apellido materno no puede exceder los 40 caracteres.\n");
            }
            if (rt.getCorreo() == null || rt.getCorreo().trim().isEmpty()) {
                errores.append("- Correo del responsable es obligatorio.\n");
            } else if (rt.getCorreo().length() > 200) {
                errores.append("- El correo electrónico no puede exceder los 200 caracteres.\n");
            } else if (!rt.getCorreo().matches("^[\\w+-]+(?:\\.[\\w+-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$")) {
                errores.append("- El formato del correo electrónico es inválido.\n");
            }
        }
        
        return errores.toString();
    }
}
