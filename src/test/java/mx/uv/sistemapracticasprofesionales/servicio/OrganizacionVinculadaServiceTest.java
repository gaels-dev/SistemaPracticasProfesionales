package mx.uv.sistemapracticasprofesionales.servicio;

import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 18/06/2026
 * Descripción: Pruebas unitarias para las validaciones de negocio de OrganizacionVinculadaService
 */
public class OrganizacionVinculadaServiceTest {

    private OrganizacionVinculadaService organizacionService;
    private OrganizacionVinculada organizacionValida;

    @Before
    public void setUp() {
        organizacionService = new OrganizacionVinculadaService();

        organizacionValida = new OrganizacionVinculada();
        organizacionValida.setRazonSocial("Desarrollos del Golfo S.A.");
        organizacionValida.setDomicilioFiscal("Av. Universidad 123, Col. Centro, Xalapa, Ver.");
        organizacionValida.setTelefono("2288123456");
        organizacionValida.setRfc("ORG123456AA1");
        organizacionValida.setActivo(true);
    }

    @Test
    public void testValidarOrganizacionValida() {
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Una organización con todos los datos correctos debería retornar cadena vacía.", resultado.isEmpty());
    }

    @Test
    public void testValidarOrganizacionRazonSocialNula() {
        organizacionValida.setRazonSocial(null);
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que la razón social es obligatoria.", resultado.contains("La razón social es obligatoria"));
    }

    @Test
    public void testValidarOrganizacionRazonSocialVacia() {
        organizacionValida.setRazonSocial("");
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que la razón social es obligatoria.", resultado.contains("La razón social es obligatoria"));
    }

    @Test
    public void testValidarOrganizacionRazonSocialMuyLarga() {
        organizacionValida.setRazonSocial("A".repeat(105));
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que la razón social no puede exceder 100 caracteres.", 
                resultado.contains("La razón social no puede exceder los 100 caracteres"));
    }

    @Test
    public void testValidarOrganizacionDomicilioNulo() {
        organizacionValida.setDomicilioFiscal(null);
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el domicilio fiscal es obligatorio.", 
                resultado.contains("El domicilio fiscal es obligatorio"));
    }

    @Test
    public void testValidarOrganizacionDomicilioVacio() {
        organizacionValida.setDomicilioFiscal("");
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el domicilio fiscal es obligatorio.", 
                resultado.contains("El domicilio fiscal es obligatorio"));
    }

    @Test
    public void testValidarOrganizacionDomicilioMuyLargo() {
        organizacionValida.setDomicilioFiscal("A".repeat(105));
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el domicilio fiscal no puede exceder 100 caracteres.", 
                resultado.contains("El domicilio fiscal no puede exceder los 100 caracteres"));
    }

    @Test
    public void testValidarOrganizacionTelefonoNulo() {
        organizacionValida.setTelefono(null);
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el teléfono es obligatorio.", 
                resultado.contains("El teléfono es obligatorio"));
    }

    @Test
    public void testValidarOrganizacionTelefonoVacio() {
        organizacionValida.setTelefono("");
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el teléfono es obligatorio.", 
                resultado.contains("El teléfono es obligatorio"));
    }

    @Test
    public void testValidarOrganizacionTelefonoInvalidoLetras() {
        organizacionValida.setTelefono("228812345a");
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el teléfono debe tener exactamente 10 dígitos.", 
                resultado.contains("El teléfono debe tener exactamente 10 dígitos"));
    }

    @Test
    public void testValidarOrganizacionTelefonoInvalidoLargo() {
        organizacionValida.setTelefono("228812345");
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el teléfono debe tener exactamente 10 dígitos.", 
                resultado.contains("El teléfono debe tener exactamente 10 dígitos"));
    }

    @Test
    public void testValidarOrganizacionRfcNulo() {
        organizacionValida.setRfc(null);
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el RFC es obligatorio.", 
                resultado.contains("El RFC es obligatorio"));
    }

    @Test
    public void testValidarOrganizacionRfcVacio() {
        organizacionValida.setRfc("");
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el RFC es obligatorio.", resultado.contains("El RFC es obligatorio"));
    }

    @Test
    public void testValidarOrganizacionRfcInvalidoFormat() {
        organizacionValida.setRfc("ORG123456AA");
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el RFC debe tener exactamente 12 caracteres y formato alfanumérico.", 
                resultado.contains("El RFC debe ser alfanumérico y de exactamente 12 caracteres"));
    }

    @Test
    public void testValidarOrganizacionRfcInvalidoFormatConEspecial() {
        organizacionValida.setRfc("ORG123456AA#");
        String resultado = organizacionService.validarOrganizacion(organizacionValida);
        assertTrue("Debería indicar que el RFC debe tener exactamente 12 caracteres y formato alfanumérico.", 
                resultado.contains("El RFC debe ser alfanumérico y de exactamente 12 caracteres"));
    }
}
