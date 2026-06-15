package mx.uv.sistemapracticasprofesionales.excepciones;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Excepción lanzada cuando las credenciales de inicio de sesión son incorrectas.
 */
public class CredencialesInvalidasException extends Exception {
    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
