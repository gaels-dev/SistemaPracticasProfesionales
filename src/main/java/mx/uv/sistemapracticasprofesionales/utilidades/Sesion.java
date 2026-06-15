package mx.uv.sistemapracticasprofesionales.utilidades;

import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para gestionar la sesión del usuario autenticado.
 */
public class Sesion {
    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Sesion.usuario = usuario;
    }
    
    public static void cerrarSesion() {
        usuario = null;
    }
}
