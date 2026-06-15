package mx.uv.sistemapracticasprofesionales.excepciones;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Excepción personalizada para cachar errores de conexión a la base de datos
 */
public class ConexionException extends RuntimeException {

    public ConexionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public ConexionException(String mensaje) {
        super(mensaje);
    }
}
