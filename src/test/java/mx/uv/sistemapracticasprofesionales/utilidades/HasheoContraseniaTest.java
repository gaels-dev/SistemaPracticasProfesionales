package mx.uv.sistemapracticasprofesionales.utilidades;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 18/06/2026
 * Descripción: Pruebas unitarias para la clase de utilidad HasheoContrasenia
 */
public class HasheoContraseniaTest {

    @Test
    public void testHashContraseniaConTextoConocido() {
        String contraseniaInput = "12345";
        String hashEsperado = "0cd6cee600ab584fc1d4fbea064424e86bc57fc5d49fe5c385cf9fa3e1b58ddc";
        String hashObtenido = HasheoContrasenia.hashContrasenia(contraseniaInput);
        assertEquals("El hash obtenido no coincide con el hash esperado de la contraseña con sal.", hashEsperado, hashObtenido);
    }

    @Test
    public void testHashContraseniaMismaEntradaIdenticos() {
        String contraseniaInput = "contraseniaSegura123";
        String hashUno = HasheoContrasenia.hashContrasenia(contraseniaInput);
        String hashDos = HasheoContrasenia.hashContrasenia(contraseniaInput);
        assertEquals("El hasheo de la misma contraseña en distintos momentos debería ser idéntico.", hashUno, hashDos);
    }

    @Test
    public void testHashContraseniaDistintasEntradasDiferentes() {
        String contraseniaUno = "contraseniaUno";
        String contraseniaDos = "contraseniaDos";
        String hashUno = HasheoContrasenia.hashContrasenia(contraseniaUno);
        String hashDos = HasheoContrasenia.hashContrasenia(contraseniaDos);
        assertNotEquals("Contraseñas distintas deberían generar hashes diferentes.", hashUno, hashDos);
    }

    @Test
    public void testHashContraniaVacia() {
        String contraseniaInput = "";
        String hashObtenido = HasheoContrasenia.hashContrasenia(contraseniaInput);
        assertNotNull("El hash obtenido no debe ser nulo para una cadena vacía.", hashObtenido);
        assertFalse("El hash obtenido no debe estar vacío para una cadena vacía.", hashObtenido.isEmpty());
    }
}
