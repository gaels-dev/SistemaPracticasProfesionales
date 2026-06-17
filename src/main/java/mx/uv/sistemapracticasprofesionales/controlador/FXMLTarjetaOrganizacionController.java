package mx.uv.sistemapracticasprofesionales.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 17/06/2026
 * Descripción: Controlador para la tarjeta de una organización vinculada.
 */
public class FXMLTarjetaOrganizacionController {

    @FXML
    private Label lblRazonSocial;
    @FXML
    private Label lblRfc;
    @FXML
    private Label lblTelefono;
    @FXML
    private Label lblDomicilioFiscal;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    public void setOrganizacion(OrganizacionVinculada organizacion) {
        lblRazonSocial.setText(organizacion.getRazonSocial());
        lblRfc.setText("RFC: " + organizacion.getRfc());
        lblTelefono.setText("Teléfono: " + organizacion.getTelefono());
        lblDomicilioFiscal.setText(organizacion.getDomicilioFiscal());
    }
}
