package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.SQLException;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.modelo.dao.EntregaDocumentoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.EntregaDocumento;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Servicio para gestionar la lógica de negocio de las 
 *                  entregas de documentos.
 */
public class EntregaDocumentoService {

    private final EntregaDocumentoDAO entregaDAO = new EntregaDocumentoDAO();

    public boolean registrarEntrega(EntregaDocumento entrega) throws SQLException {
        return entregaDAO.registrarEntrega(entrega);
    }

    public EntregaDocumento buscarEntrega(int idPracticante, int idSolicitud) throws SQLException {
        return entregaDAO.buscarEntregaPorPracticanteYSolicitud(idPracticante, idSolicitud);
    }

    public byte[] obtenerArchivo(int idEntrega) throws SQLException {
        return entregaDAO.obtenerArchivoEntregado(idEntrega);
    }

    public List<EntregaDocumento> obtenerDocumentosPendientesPorPracticante(int idPracticante) throws SQLException {
        return entregaDAO.obtenerDocumentosPendientesPorPracticante(idPracticante);
    }

    public boolean validarDocumento(int idEntregaDocumento) throws SQLException {
        return entregaDAO.validarDocumento(idEntregaDocumento);
    }

    public boolean rechazarDocumento(int idEntregaDocumento, String motivoRechazo) throws SQLException {
        return entregaDAO.rechazarDocumento(idEntregaDocumento, motivoRechazo);
    }

    public boolean cancelarEntrega(int idPracticante, int idSolicitud) throws SQLException {
        return entregaDAO.cancelarEntrega(idPracticante, idSolicitud);
    }
}
