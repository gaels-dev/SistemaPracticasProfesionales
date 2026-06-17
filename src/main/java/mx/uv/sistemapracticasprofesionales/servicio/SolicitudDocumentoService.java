package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.modelo.dao.InscripcionExperienciaEducativaDAO;
import mx.uv.sistemapracticasprofesionales.modelo.dao.SolicitudDocumentoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.InscripcionExperienciaEducativa;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Servicio para gestionar la lógica de negocio 
 *                  de las solicitudes de documentos.
 */
public class SolicitudDocumentoService {

    private final SolicitudDocumentoDAO solicitudDAO = new SolicitudDocumentoDAO();
    private final InscripcionExperienciaEducativaDAO inscripcionDAO = new InscripcionExperienciaEducativaDAO();

    public List<SolicitudDocumento> obtenerSolicitudesPorPracticanteYTipo(
            int idPracticante, String... tipos) throws SQLException {
        List<SolicitudDocumento> listaFiltrada = new ArrayList<>();
        
        InscripcionExperienciaEducativa inscripcion = 
                inscripcionDAO.obtenerInscripcionActivaPorPracticante(idPracticante);
        
        if (inscripcion != null) {
            List<SolicitudDocumento> todasLasSolicitudes = 
                    solicitudDAO.obtenerSolicitudesPorExperiencia(
                inscripcion.getExperienciaEducativa().getIdExperienciaEducativa()
            );
            
            for (SolicitudDocumento solicitud : todasLasSolicitudes) {
                for (String tipo : tipos) {
                    if (solicitud.getDocumento().getTipoDocumento().equals(tipo)) {
                        listaFiltrada.add(solicitud);
                        break;
                    }
                }
            }
        }
        
        return listaFiltrada;
    }
}
