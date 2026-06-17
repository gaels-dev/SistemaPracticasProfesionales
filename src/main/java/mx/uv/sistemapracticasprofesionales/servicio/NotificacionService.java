package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.SQLException;
import java.util.List;
import mx.uv.sistemapracticasprofesionales.modelo.dao.NotificacionDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Notificacion;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Servicio para gestionar las notificaciones.
 */
public class NotificacionService {
    private final NotificacionDAO notificacionDAO = new NotificacionDAO();

    public List<Notificacion> obtenerNotificacionesPorPracticante(int idPracticante) throws SQLException {
        return notificacionDAO.obtenerNotificacionesPorPracticante(idPracticante);
    }

    public boolean eliminarNotificacion(int idNotificacion) throws SQLException {
        return notificacionDAO.eliminarNotificacion(idNotificacion);
    }
}