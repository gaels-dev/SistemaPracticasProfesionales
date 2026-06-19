package mx.uv.sistemapracticasprofesionales.servicio;

import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Periodo;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Proyecto;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ResponsableTecnico;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 18/06/2026
 * Descripción: Pruebas unitarias para las validaciones de negocio de ProyectoService
 */
public class ProyectoServiceTest {

    private ProyectoService proyectoService;
    private Proyecto proyectoValido;

    @Before
    public void setUp() {
        proyectoService = new ProyectoService();

        OrganizacionVinculada org = new OrganizacionVinculada();
        org.setIdOrganizacionVinculada(1);
        org.setRazonSocial("Empresa Tech S.A.");

        Periodo periodo = new Periodo();
        periodo.setIdPeriodo(1);
        periodo.setNombre("FEB-JUN 2026");

        ResponsableTecnico rt = new ResponsableTecnico();
        rt.setIdResponsableTecnico(1);
        rt.setNombres("Carlos");
        rt.setApellidoPaterno("Martinez");
        rt.setApellidoMaterno("Silva");
        rt.setCorreo("carlos.martinez@tech.com");

        proyectoValido = new Proyecto();
        proyectoValido.setNombre("Desarrollo de Sistema SPP");
        proyectoValido.setDescripcion("Desarrollo de un sistema para control de prácticas profesionales.");
        proyectoValido.setCupoMaximo(5);
        proyectoValido.setOrganizacionVinculada(org);
        proyectoValido.setPeriodo(periodo);
        proyectoValido.setResponsableTecnico(rt);
        proyectoValido.setActivo(true);
    }

    @Test
    public void testValidarProyectoValido() {
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Un proyecto con datos válidos debería retornar una cadena vacía.", resultado.isEmpty());
    }

    @Test
    public void testValidarProyectoNombreNulo() {
        proyectoValido.setNombre(null);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el nombre del proyecto es obligatorio.", 
                resultado.contains("Nombre del proyecto es obligatorio"));
    }

    @Test
    public void testValidarProyectoNombreVacio() {
        proyectoValido.setNombre("   ");
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el nombre del proyecto es obligatorio.", 
                resultado.contains("Nombre del proyecto es obligatorio"));
    }

    @Test
    public void testValidarProyectoNombreMuyLargo() {
        proyectoValido.setNombre("A".repeat(55));
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el nombre no puede exceder 50 caracteres.", 
                resultado.contains("El nombre del proyecto no puede exceder los 50 caracteres"));
    }

    @Test
    public void testValidarProyectoDescripcionNula() {
        proyectoValido.setDescripcion(null);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que la descripción es obligatoria.", 
                resultado.contains("Descripción del proyecto es obligatoria"));
    }

    @Test
    public void testValidarProyectoDescripcionVacia() {
        proyectoValido.setDescripcion("   ");
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que la descripción es obligatoria.", 
                resultado.contains("Descripción del proyecto es obligatoria"));
    }

    @Test
    public void testValidarProyectoDescripcionMuyLarga() {
        proyectoValido.setDescripcion("A".repeat(210));
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que la descripción no puede exceder 200 caracteres.", 
                resultado.contains("La descripción no puede exceder los 200 caracteres"));
    }

    @Test
    public void testValidarProyectoCupoMaximoNulo() {
        proyectoValido.setCupoMaximo(null);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el cupo máximo debe ser mayor a 0.", 
                resultado.contains("El cupo máximo debe ser mayor a 0"));
    }

    @Test
    public void testValidarProyectoCupoMaximoInvalidoCero() {
        proyectoValido.setCupoMaximo(0);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el cupo máximo debe ser mayor a 0.", 
                resultado.contains("El cupo máximo debe ser mayor a 0"));
    }

    @Test
    public void testValidarProyectoCupoMaximoInvalidoNegativo() {
        proyectoValido.setCupoMaximo(-5);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el cupo máximo debe ser mayor a 0.", 
                resultado.contains("El cupo máximo debe ser mayor a 0"));
    }

    @Test
    public void testValidarProyectoCupoMaximoExcedido() {
        proyectoValido.setCupoMaximo(25);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el cupo no puede exceder 20 vacantes.", 
                resultado.contains("El cupo máximo no puede exceder las 20 vacantes"));
    }

    @Test
    public void testValidarProyectoOrganizacionNula() {
        proyectoValido.setOrganizacionVinculada(null);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que debe seleccionar una organización.", 
                resultado.contains("Debe seleccionar una organización vinculada"));
    }

    @Test
    public void testValidarProyectoPeriodoNulo() {
        proyectoValido.setPeriodo(null);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el periodo no está disponible.", 
                resultado.contains("No hay un periodo escolar abierto disponible"));
    }

    @Test
    public void testValidarProyectoResponsableNulo() {
        proyectoValido.setResponsableTecnico(null);
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que los datos del responsable técnico son obligatorios.", 
                resultado.contains("Datos del responsable técnico son obligatorios"));
    }

    @Test
    public void testValidarProyectoResponsableNombresVacio() {
        proyectoValido.getResponsableTecnico().setNombres("  ");
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el nombre del responsable es obligatorio.", 
                resultado.contains("Nombre del responsable es obligatorio"));
    }

    @Test
    public void testValidarProyectoResponsableNombresMuyLargo() {
        proyectoValido.getResponsableTecnico().setNombres("A".repeat(55));
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el nombre no puede exceder 50 caracteres.", 
                resultado.contains("El nombre del responsable no puede exceder los 50 caracteres"));
    }

    @Test
    public void testValidarProyectoResponsablePaternoVacio() {
        proyectoValido.getResponsableTecnico().setApellidoPaterno("  ");
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el apellido paterno del responsable es obligatorio.", 
                resultado.contains("Apellido paterno del responsable es obligatorio"));
    }

    @Test
    public void testValidarProyectoResponsablePaternoMuyLargo() {
        proyectoValido.getResponsableTecnico().setApellidoPaterno("A".repeat(45));
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el apellido paterno no puede exceder 40 caracteres.", 
                resultado.contains("El apellido paterno no puede exceder los 40 caracteres"));
    }

    @Test
    public void testValidarProyectoResponsableMaternoVacio() {
        proyectoValido.getResponsableTecnico().setApellidoMaterno("  ");
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el apellido materno del responsable es obligatorio.", 
                resultado.contains("Apellido materno del responsable es obligatorio"));
    }

    @Test
    public void testValidarProyectoResponsableMaternoMuyLargo() {
        proyectoValido.getResponsableTecnico().setApellidoMaterno("A".repeat(45));
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el apellido materno no puede exceder 40 caracteres.", 
                resultado.contains("El apellido materno no puede exceder los 40 caracteres"));
    }

    @Test
    public void testValidarProyectoResponsableCorreoVacio() {
        proyectoValido.getResponsableTecnico().setCorreo("  ");
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el correo del responsable es obligatorio.", 
                resultado.contains("Correo del responsable es obligatorio"));
    }

    @Test
    public void testValidarProyectoResponsableCorreoMuyLargo() {
        proyectoValido.getResponsableTecnico().setCorreo("A".repeat(195) + "@tech.com");
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el correo no puede exceder 200 caracteres.", 
                resultado.contains("El correo electrónico no puede exceder los 200 caracteres"));
    }

    @Test
    public void testValidarProyectoResponsableCorreoFormatoInvalido() {
        proyectoValido.getResponsableTecnico().setCorreo("correoInvalido");
        String resultado = proyectoService.validarProyecto(proyectoValido);
        assertTrue("Debería indicar que el formato del correo es inválido.", 
                resultado.contains("El formato del correo electrónico es inválido"));
    }
}
