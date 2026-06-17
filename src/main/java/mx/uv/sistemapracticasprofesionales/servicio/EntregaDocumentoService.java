package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.SQLException;
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

    public boolean cancelarEntrega(int idPracticante, int idSolicitud) throws SQLException {
        return entregaDAO.cancelarEntrega(idPracticante, idSolicitud);
    }
}
