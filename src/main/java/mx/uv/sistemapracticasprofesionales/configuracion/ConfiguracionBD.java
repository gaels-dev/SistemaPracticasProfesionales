package mx.uv.sistemapracticasprofesionales.configuracion;

import java.io.InputStream;
import java.util.Properties;
import mx.uv.sistemapracticasprofesionales.excepciones.ConexionException;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Carga la configuración de la base de datos desde el archivo db.properties
 */
public class ConfiguracionBD {
    private static final Properties PROPIEDADES = new Properties();

    static {
        try (InputStream input = ConfiguracionBD.class.getResourceAsStream("/db.properties")) {
            if (input == null) {
                throw new ConexionException("No se encontró el archivo de configuración");
            }
            PROPIEDADES.load(input);
        } catch (Exception e) {
            throw new ConexionException("Error cargando configuración de BD", e);
        }
    }

    public static String getUrl() {
        return PROPIEDADES.getProperty("db.url");
    }

    public static String getUser() {
        return PROPIEDADES.getProperty("db.user");
    }

    public static String getPassword() {
        return PROPIEDADES.getProperty("db.password");
    }
}
