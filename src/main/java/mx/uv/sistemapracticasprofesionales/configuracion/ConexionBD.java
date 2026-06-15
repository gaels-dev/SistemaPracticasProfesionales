package mx.uv.sistemapracticasprofesionales.configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mx.uv.sistemapracticasprofesionales.excepciones.ConexionException;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Clase para gestionar la apertura y cierre de conexiones a la base de datos
 */
public class ConexionBD {

    public static Connection abrirConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(
                ConfiguracionBD.getUrl(),
                ConfiguracionBD.getUser(),
                ConfiguracionBD.getPassword()
            );
        } catch (SQLException e) {
            throw new ConexionException("Error al abrir la conexión con la base de datos", e);
        }
        return conexion;
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                throw new ConexionException("Error al cerrar la conexión con la base de datos", e);
            }
        }
    }
}
