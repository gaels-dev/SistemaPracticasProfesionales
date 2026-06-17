package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;
import mx.uv.sistemapracticasprofesionales.servicio.OrganizacionVinculadaService;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 17/06/2026
 * Descripción: Controlador para la vista de registro de organizaciones vinculadas.
 */
public class FXMLRegistrarOrganizacionController implements Initializable {

    @FXML
    private TextField txfRazonSocial;
    @FXML
    private Label lblContadorRazonSocial;
    @FXML
    private TextField txfDomicilioFiscal;
    @FXML
    private TextField txfTelefono;
    @FXML
    private TextField txfRfc;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private final OrganizacionVinculadaService organizacionService = new OrganizacionVinculadaService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarContadorRazonSocial();
        configurarLimitadoresTexto();
    }

    private void configurarContadorRazonSocial() {
        txfRazonSocial.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.length() > 100) {
                    txfRazonSocial.setText(oldValue);
                } else {
                    lblContadorRazonSocial.setText(newValue.length() + "/100");
                }
            }
        });
    }

    private void configurarLimitadoresTexto() {
        txfDomicilioFiscal.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 100) {
                txfDomicilioFiscal.setText(oldValue);
            }
        });

        txfTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.length() > 10 || !newValue.matches("\\d*")) {
                    txfTelefono.setText(oldValue);
                }
            }
        });

        txfRfc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.length() > 12 || !newValue.matches("[A-Z0-9]*")) {
                    txfRfc.setText(oldValue);
                }
            }
        });
    }

    @FXML
    private void handleGuardarOrganizacion(ActionEvent event) {
        OrganizacionVinculada org = recolectarDatos();
        String errores = organizacionService.validarOrganizacion(org);

        if (!errores.isEmpty()) {
            mostrarAlerta("Datos inválidos o incompletos", "Por favor corrija lo siguiente:\n" + errores, Alert.AlertType.WARNING);
            return;
        }

        try {
            if (organizacionService.existeOrganizacionPorRfc(org.getRfc())) {
                mostrarAlerta("Organización ya registrada", "Ya existe una organización registrada con ese RFC.", Alert.AlertType.WARNING);
                return;
            }
            if (organizacionService.existeOrganizacionPorRazonSocial(org.getRazonSocial())) {
                mostrarAlerta("Razón social ya registrada", "Ya existe una organización registrada con esa razón social.", Alert.AlertType.WARNING);
                return;
            }
            if (organizacionService.existeOrganizacionPorTelefono(org.getTelefono())) {
                mostrarAlerta("Teléfono ya registrado", "Ya existe una organización registrada con ese número de teléfono.", Alert.AlertType.WARNING);
                return;
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al verificar la existencia de la organización.", Alert.AlertType.ERROR);
            return;
        }

        if (confirmarRegistro()) {
            try {
                if (organizacionService.registrarOrganizacion(org)) {
                    mostrarAlerta("Éxito", "Organización registrada correctamente.", Alert.AlertType.INFORMATION);
                    cerrarVentana();
                } else {
                    mostrarAlerta("Error", "No se pudo registrar la organización vinculada.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                mostrarAlerta("Error", "Error de conexión con la base de datos.", Alert.AlertType.ERROR);
            }
        }
    }

    private OrganizacionVinculada recolectarDatos() {
        OrganizacionVinculada org = new OrganizacionVinculada();
        org.setRazonSocial(txfRazonSocial.getText().trim());
        org.setDomicilioFiscal(txfDomicilioFiscal.getText().trim());
        org.setTelefono(txfTelefono.getText().trim());
        org.setRfc(txfRfc.getText().trim().toUpperCase());
        org.setActivo(true);
        return org;
    }

    private boolean confirmarRegistro() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Registro");
        alert.setHeaderText("¿Seguro que quiere registrar la organización vinculada?");
        alert.setContentText("Esta organización podrá ser asociada a nuevos proyectos.");

        ButtonType btnAceptar = new ButtonType("Aceptar");
        ButtonType btnSeguirEditando = new ButtonType("Seguir editando");

        alert.getButtonTypes().setAll(btnAceptar, btnSeguirEditando);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == btnAceptar;
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
