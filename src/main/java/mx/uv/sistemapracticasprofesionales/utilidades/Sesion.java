package mx.uv.sistemapracticasprofesionales.utilidades;

import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Clase para gestionar la sesión del usuario autenticado.
 */
public class Sesion {
    private static Usuario usuario;
    private static mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico personalAcademico;
    private static mx.uv.sistemapracticasprofesionales.modelo.pojo.ExperienciaEducativa eeSeleccionada;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Sesion.usuario = usuario;
    }

    public static mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico getPersonalAcademico() {
        return personalAcademico;
    }

    public static void setPersonalAcademico(mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico personalAcademico) {
        Sesion.personalAcademico = personalAcademico;
    }

    public static mx.uv.sistemapracticasprofesionales.modelo.pojo.ExperienciaEducativa getEeSeleccionada() {
        return eeSeleccionada;
    }

    public static void setEeSeleccionada(mx.uv.sistemapracticasprofesionales.modelo.pojo.ExperienciaEducativa eeSeleccionada) {
        Sesion.eeSeleccionada = eeSeleccionada;
    }
    
    public static void cerrarSesion() {
        usuario = null;
        personalAcademico = null;
        eeSeleccionada = null;
    }
}
