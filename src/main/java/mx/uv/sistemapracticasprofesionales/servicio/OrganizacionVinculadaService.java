package mx.uv.sistemapracticasprofesionales.servicio;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import mx.uv.sistemapracticasprofesionales.modelo.dao.OrganizacionVinculadaDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 17/06/2026
 * Descripción: Servicio para la gestión de organizaciones vinculadas, 
 *              incluye validaciones de negocio.
 */

public class OrganizacionVinculadaService {

    private final OrganizacionVinculadaDAO organizacionDAO = new OrganizacionVinculadaDAO();

    public List<OrganizacionVinculada> obtenerOrganizacionesActivas() throws SQLException {
        return organizacionDAO.obtenerOrganizacionesActivas();
    }

    public boolean registrarOrganizacion(OrganizacionVinculada organizacion) throws SQLException {
        return organizacionDAO.registrarOrganizacion(organizacion) != -1;
    }

    public boolean existeOrganizacionPorRfc(String rfc) throws SQLException {
        return organizacionDAO.existeOrganizacionPorRfc(rfc);
    }

    public boolean existeOrganizacionPorTelefono(String telefono) throws SQLException {
        return organizacionDAO.existeOrganizacionPorTelefono(telefono);
    }

    public boolean existeOrganizacionPorRazonSocial(String razonSocial) throws SQLException {
        return organizacionDAO.existeOrganizacionPorRazonSocial(razonSocial);
    }

    public String validarOrganizacion(OrganizacionVinculada org) {
        StringBuilder errores = new StringBuilder();

        if (org.getRazonSocial() == null || org.getRazonSocial().isEmpty()) {
            errores.append("- La razón social es obligatoria.\n");
        } else if (org.getRazonSocial().length() > 100) {
            errores.append("- La razón social no puede exceder los 100 caracteres.\n");
        }

        if (org.getDomicilioFiscal() == null || org.getDomicilioFiscal().isEmpty()) {
            errores.append("- El domicilio fiscal es obligatorio.\n");
        } else if (org.getDomicilioFiscal().length() > 100) {
            errores.append("- El domicilio fiscal no puede exceder los 100 caracteres.\n");
        }

        if (org.getTelefono() == null || org.getTelefono().isEmpty()) {
            errores.append("- El teléfono es obligatorio.\n");
        } else if (!Pattern.matches("\\d{10}", org.getTelefono())) {
            errores.append("- El teléfono debe tener exactamente 10 dígitos.\n");
        }

        if (org.getRfc() == null || org.getRfc().isEmpty()) {
            errores.append("- El RFC es obligatorio.\n");
        } else if (!Pattern.matches("^[A-Z0-9]{12}$", org.getRfc())) {
            errores.append("- El RFC debe ser alfanumérico y de exactamente 12 caracteres.\n");
        }

        return errores.toString();
    }
}
