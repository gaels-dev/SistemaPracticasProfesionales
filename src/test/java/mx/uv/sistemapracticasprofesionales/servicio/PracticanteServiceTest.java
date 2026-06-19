package mx.uv.sistemapracticasprofesionales.servicio;

import java.util.Calendar;
import java.util.Date;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 18/06/2026
 * Descripción: Pruebas unitarias para las validaciones de negocio de PracticanteService
 */
public class PracticanteServiceTest {

    private PracticanteService practicanteService;
    private Practicante practicanteValido;

    @Before
    public void setUp() {
        practicanteService = new PracticanteService();
        
        Usuario usuarioValido = new Usuario();
        usuarioValido.setNombre("S22012345");
        usuarioValido.setContrasenia("passwordHashed123");

        Calendar cal = Calendar.getInstance();
        cal.set(2002, Calendar.JANUARY, 15);
        Date fechaNac = cal.getTime();

        practicanteValido = new Practicante();
        practicanteValido.setMatricula("S22012345");
        practicanteValido.setNombres("Juan Carlos");
        practicanteValido.setApellidoPaterno("Perez");
        practicanteValido.setApellidoMaterno("Gomez");
        practicanteValido.setCorreo("juan.perez@estudiantes.uv.mx");
        practicanteValido.setSexo("Masculino");
        practicanteValido.setFechaNacimiento(fechaNac);
        practicanteValido.setActivo(true);
        practicanteValido.setUsuario(usuarioValido);
    }

    @Test
    public void testValidarPracticanteValido() {
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Un practicante con todos los datos válidos debería retornar una cadena vacía de errores.", resultado.isEmpty());
    }

    @Test
    public void testValidarPracticanteMatriculaNula() {
        practicanteValido.setMatricula(null);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que la matrícula es obligatoria.", resultado.contains("La matrícula es obligatoria"));
    }

    @Test
    public void testValidarPracticanteMatriculaVacia() {
        practicanteValido.setMatricula("   ");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que la matrícula es obligatoria.", resultado.contains("La matrícula es obligatoria"));
    }

    @Test
    public void testValidarPracticanteMatriculaFormatoInvalido() {
        practicanteValido.setMatricula("A22012345");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el formato de la matrícula es inválido.", resultado.contains("El formato de la matrícula es inválido"));
    }

    @Test
    public void testValidarPracticanteNombreNulo() {
        practicanteValido.setNombres(null);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el nombre es obligatorio.", resultado.contains("El nombre es obligatorio"));
    }

    @Test
    public void testValidarPracticanteNombreVacio() {
        practicanteValido.setNombres("   ");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el nombre es obligatorio.", resultado.contains("El nombre es obligatorio"));
    }

    @Test
    public void testValidarPracticanteNombreMuyLargo() {
        String nombreLargo = "Juan".repeat(20); 
        practicanteValido.setNombres(nombreLargo);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el nombre no puede exceder los 50 caracteres.", resultado.contains("El nombre no puede exceder los 50 caracteres"));
    }

    @Test
    public void testValidarPracticanteApellidoPaternoNulo() {
        practicanteValido.setApellidoPaterno(null);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el apellido paterno es obligatorio.", resultado.contains("El apellido paterno es obligatorio"));
    }

    @Test
    public void testValidarPracticanteApellidoPaternoVacio() {
        practicanteValido.setApellidoPaterno("   ");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el apellido paterno es obligatorio.", resultado.contains("El apellido paterno es obligatorio"));
    }

    @Test
    public void testValidarPracticanteApellidoPaternoMuyLargo() {
        String apellidoLargo = "Perez".repeat(10);
        practicanteValido.setApellidoPaterno(apellidoLargo);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el apellido paterno no puede exceder los 40 caracteres.", resultado.contains("El apellido paterno no puede exceder los 40 caracteres"));
    }

    @Test
    public void testValidarPracticanteApellidoMaternoNulo() {
        practicanteValido.setApellidoMaterno(null);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el apellido materno es obligatorio.", resultado.contains("El apellido materno es obligatorio"));
    }

    @Test
    public void testValidarPracticanteApellidoMaternoVacio() {
        practicanteValido.setApellidoMaterno("   ");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el apellido materno es obligatorio.", resultado.contains("El apellido materno es obligatorio"));
    }

    @Test
    public void testValidarPracticanteApellidoMaternoMuyLargo() {
        String apellidoLargo = "Gomez".repeat(10);
        practicanteValido.setApellidoMaterno(apellidoLargo);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el apellido materno no puede exceder los 40 caracteres.", resultado.contains("El apellido materno no puede exceder los 40 caracteres"));
    }

    @Test
    public void testValidarPracticanteCorreoNulo() {
        practicanteValido.setCorreo(null);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el correo electrónico es obligatorio.", resultado.contains("El correo electrónico es obligatorio"));
    }

    @Test
    public void testValidarPracticanteCorreoVacio() {
        practicanteValido.setCorreo("   ");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el correo electrónico es obligatorio.", resultado.contains("El correo electrónico es obligatorio"));
    }

    @Test
    public void testValidarPracticanteCorreoMuyLargo() {
        String correoLargo = "a".repeat(195) + "@uv.mx";
        practicanteValido.setCorreo(correoLargo);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el correo electrónico no puede exceder los 200 caracteres.", resultado.contains("El correo electrónico no puede exceder los 200 caracteres"));
    }

    @Test
    public void testValidarPracticanteCorreoFormatoInvalido() {
        practicanteValido.setCorreo("correoInvalido.com");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el formato del correo es inválido.", resultado.contains("El formato del correo electrónico es inválido"));
    }

    @Test
    public void testValidarPracticanteSexoNulo() {
        practicanteValido.setSexo(null);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que se debe seleccionar un sexo.", resultado.contains("Debe seleccionar un sexo"));
    }

    @Test
    public void testValidarPracticanteFechaNacimientoNula() {
        practicanteValido.setFechaNacimiento(null);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que la fecha de nacimiento es obligatoria.", resultado.contains("La fecha de nacimiento es obligatoria"));
    }

    @Test
    public void testValidarPracticanteUsuarioNulo() {
        practicanteValido.setUsuario(null);
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el nombre de usuario es obligatorio.", resultado.contains("El nombre de usuario es obligatorio"));
    }

    @Test
    public void testValidarPracticanteNombreUsuarioVacio() {
        practicanteValido.getUsuario().setNombre("   ");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que el nombre de usuario es obligatorio.", resultado.contains("El nombre de usuario es obligatorio"));
    }

    @Test
    public void testValidarPracticanteContraseniaVacia() {
        practicanteValido.getUsuario().setContrasenia("   ");
        String resultado = practicanteService.validarPracticante(practicanteValido);
        assertTrue("Debería reportar que la contraseña es obligatoria.", resultado.contains("La contraseña es obligatoria"));
    }
}
