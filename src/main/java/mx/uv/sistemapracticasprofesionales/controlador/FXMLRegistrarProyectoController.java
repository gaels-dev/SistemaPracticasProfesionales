package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Periodo;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Proyecto;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ResponsableTecnico;
import mx.uv.sistemapracticasprofesionales.servicio.ProyectoService;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 15/06/2026
 * Descripción: Controlador para la vista de registro de proyectos.
 */
public class FXMLRegistrarProyectoController implements Initializable {

    @FXML
    private TextField txfNombreProyecto;
    @FXML
    private ComboBox<OrganizacionVinculada> cmbOrganizacion;
    @FXML
    private TextField txfNombresResponsable;
    @FXML
    private TextField txfApellidoPaternoResponsable;
    @FXML
    private TextField txfApellidoMaternoResponsable;
    @FXML
    private TextField txfCorreoResponsable;
    @FXML
    private TextArea txaDescripcion;
    @FXML
    private Label lblContadorCaracteres;
    @FXML
    private TextField txfVacantes;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private final ProyectoService proyectoService = new ProyectoService();
    private Periodo periodoActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBox();
        cargarOrganizaciones();
        cargarPeriodoActual();
        configurarContadorCaracteres();
    }

    private void configurarComboBox() {
        cmbOrganizacion.setConverter(new StringConverter<OrganizacionVinculada>() {
            @Override
            public String toString(OrganizacionVinculada object) {
                return object == null ? "" : object.getRazonSocial();
            }

            @Override
            public OrganizacionVinculada fromString(String string) {
                return null;
            }
        });
    }

    private void cargarOrganizaciones() {
        try {
            List<OrganizacionVinculada> organizaciones = proyectoService.obtenerOrganizacionesActivas();
            ObservableList<OrganizacionVinculada> items = FXCollections.observableArrayList(organizaciones);
            cmbOrganizacion.setItems(items);
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo obtener la lista de organizaciones activas.", Alert.AlertType.ERROR);
        }
    }

    private void cargarPeriodoActual() {
        try {
            periodoActual = proyectoService.obtenerPeriodoActual();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al obtener el periodo actual.", Alert.AlertType.ERROR);
            btnGuardar.setDisable(true);
        }
    }

    private void configurarContadorCaracteres() {
        txaDescripcion.textProperty().addListener((observable, oldValue, newValue) -> {
            int length = newValue.length();
            lblContadorCaracteres.setText(length + "/200");
            if (length > 200) {
                lblContadorCaracteres.setStyle("-fx-text-fill: red;");
            } else {
                lblContadorCaracteres.setStyle("-fx-text-fill: #666666;");
            }
        });
    }

    @FXML
    private void handleGuardarProyecto(ActionEvent event) {
        Proyecto proyecto = recolectarDatos();
        String errores = proyectoService.validarProyecto(proyecto);

        if (!errores.isEmpty()) {
            mostrarAlerta("Datos inválidos o incompletos", "Por favor corrija lo siguiente:\n" + errores, Alert.AlertType.WARNING);
            return;
        }

        if (confirmarRegistro()) {
            try {
                if (proyectoService.registrarProyecto(proyecto)) {
                    mostrarAlerta("Éxito", "Proyecto registrado correctamente.", Alert.AlertType.INFORMATION);
                    cerrarVentana();
                } else {
                    mostrarAlerta("Error", "No se pudo registrar el proyecto, inténtelo más tarde.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                mostrarAlerta("Error", "Error de conexión con la base de datos.", Alert.AlertType.ERROR);
            }
        }
    }

    private Proyecto recolectarDatos() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(txfNombreProyecto.getText().trim());
        proyecto.setDescripcion(txaDescripcion.getText().trim());
        try {
            proyecto.setCupoMaximo(Integer.parseInt(txfVacantes.getText().trim()));
        } catch (NumberFormatException e) {
            proyecto.setCupoMaximo(0);
        }
        proyecto.setOrganizacionVinculada(cmbOrganizacion.getSelectionModel().getSelectedItem());
        proyecto.setPeriodo(periodoActual);
        proyecto.setActivo(true);

        ResponsableTecnico rt = new ResponsableTecnico();
        rt.setNombres(txfNombresResponsable.getText().trim());
        rt.setApellidoPaterno(txfApellidoPaternoResponsable.getText().trim());
        rt.setApellidoMaterno(txfApellidoMaternoResponsable.getText().trim());
        rt.setCorreo(txfCorreoResponsable.getText().trim());
        rt.setOrganizacionVinculada(proyecto.getOrganizacionVinculada());
        
        proyecto.setResponsableTecnico(rt);
        
        return proyecto;
    }

    private boolean confirmarRegistro() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Registro");
        alert.setHeaderText("¿Está seguro de registrar este proyecto?");
        alert.setContentText("Una vez registrado, estará disponible para los practicantes.");

        ButtonType btnConfirmar = new ButtonType("Confirmar");
        ButtonType btnSeguirEditando = new ButtonType("Seguir editando");

        alert.getButtonTypes().setAll(btnConfirmar, btnSeguirEditando);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == btnConfirmar;
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
