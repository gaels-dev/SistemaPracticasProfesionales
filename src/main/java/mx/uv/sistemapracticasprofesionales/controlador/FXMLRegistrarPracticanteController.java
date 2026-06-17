package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;
import mx.uv.sistemapracticasprofesionales.servicio.PracticanteService;
import mx.uv.sistemapracticasprofesionales.utilidades.HasheoContrasenia;

/**
 * FXML Controller class
 *
 * @author gaels
 */
public class FXMLRegistrarPracticanteController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfNombres;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCorreo;
    @FXML
    private ComboBox<String> cmbSexo;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private PasswordField pfContrasena;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnRegistrar;

    private final PracticanteService practicanteService = new PracticanteService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbSexo.setItems(FXCollections.observableArrayList("Masculino", "Femenino", "Otro"));
        configurarDatePicker();
    }

    private void configurarDatePicker() {
        dpFechaNacimiento.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDate minDate = LocalDate.of(1980, 1, 1);
                LocalDate maxDate = LocalDate.of(2012, 12, 31);
                
                if (newValue.isBefore(minDate)) {
                    dpFechaNacimiento.setValue(minDate);
                } else if (newValue.isAfter(maxDate)) {
                    dpFechaNacimiento.setValue(null);
                    mostrarAlerta("Fecha inválida", "La fecha de nacimiento no puede ser posterior " +
                                  "al 31 de diciembre del 2012.", Alert.AlertType.WARNING);
                }
            }
        });
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        regresarAGestionPracticantes();
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAGestionPracticantes();
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        String errores = validarCampos();
        if (!errores.isEmpty()) {
            mostrarAlerta("Datos inválidos o incompletos", "Por favor corrija lo siguiente:\n" + errores, Alert.AlertType.WARNING);
            return;
        }

        Practicante practicante = recolectarDatos();
        
        try {
            if (practicanteService.existePracticanteActivoPorMatricula(practicante.getMatricula())) {
                mostrarAlerta("Practicante registrado", "El practicante ya se encuentra registrado y activo.", Alert.AlertType.WARNING);
                return;
            }
            
            Practicante practicanteInactivo = practicanteService.buscarPorMatricula(practicante.getMatricula());
            if (practicanteInactivo != null && !practicanteInactivo.getActivo()) {
                if (confirmarReactivacion()) {
                    if (practicanteService.reactivarPracticante(practicanteInactivo.getIdPracticante())) {
                        mostrarAlerta("Éxito", "Practicante reactivado correctamente.", Alert.AlertType.INFORMATION);
                        regresarAGestionPracticantes();
                    } else {
                        mostrarAlerta("Error", "No se pudo reactivar al practicante.", Alert.AlertType.ERROR);
                    }
                }
                return;
            }
            
            if (confirmarRegistro()) {
                if (practicanteService.registrarPracticante(practicante)) {
                    mostrarAlerta("Éxito", "Practicante registrado correctamente.", Alert.AlertType.INFORMATION);
                    regresarAGestionPracticantes();
                } else {
                    mostrarAlerta("Error", "No se pudo registrar al practicante, inténtelo más tarde.", Alert.AlertType.ERROR);
                }
            }
        } catch (SQLException e) {
            String mensajeError = "Error de conexión con la base de datos.";
            if (e.getMessage() != null) {
                if (e.getMessage().contains("nombre de usuario") || e.getMessage().contains("tipo de usuario")) {
                    mensajeError = e.getMessage();
                } else if (e.getMessage().contains("Duplicate entry")) {
                    mensajeError = "El correo electrónico ya se encuentra en uso por otra persona.";
                }
            }
            mostrarAlerta("Error", mensajeError, Alert.AlertType.ERROR);
        }
    }

    private String validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (tfMatricula.getText().trim().isEmpty()) {
            errores.append("- La matrícula es obligatoria.\n");
        } else if (tfMatricula.getText().trim().length() != 9) {
            errores.append("- La matrícula debe tener exactamente 9 caracteres.\n");
        }

        if (tfNombres.getText().trim().isEmpty()) {
            errores.append("- El nombre es obligatorio.\n");
        } else if (tfNombres.getText().trim().length() > 45) {
            errores.append("- El nombre no puede exceder los 45 caracteres.\n");
        }

        if (tfApellidoPaterno.getText().trim().isEmpty()) {
            errores.append("- El apellido paterno es obligatorio.\n");
        } else if (tfApellidoPaterno.getText().trim().length() > 45) {
            errores.append("- El apellido paterno no puede exceder los 45 caracteres.\n");
        }
        
        if (tfApellidoMaterno.getText().trim().length() > 45) {
            errores.append("- El apellido materno no puede exceder los 45 caracteres.\n");
        }

        if (tfCorreo.getText().trim().isEmpty()) {
            errores.append("- El correo electrónico es obligatorio.\n");
        } else if (tfCorreo.getText().trim().length() > 45) {
            errores.append("- El correo electrónico no puede exceder los 45 caracteres.\n");
        }

        if (cmbSexo.getValue() == null) {
            errores.append("- Debe seleccionar un sexo.\n");
        }

        if (dpFechaNacimiento.getValue() == null) {
            errores.append("- La fecha de nacimiento es obligatoria.\n");
        }

        if (tfNombreUsuario.getText().trim().isEmpty()) {
            errores.append("- El nombre de usuario es obligatorio.\n");
        } else if (tfNombreUsuario.getText().trim().length() > 45) {
            errores.append("- El nombre de usuario no puede exceder los 45 caracteres.\n");
        }

        if (pfContrasena.getText().trim().isEmpty()) {
            errores.append("- La contraseña es obligatoria.\n");
        }

        return errores.toString();
    }

    private Practicante recolectarDatos() {
        Practicante practicante = new Practicante();
        practicante.setMatricula(tfMatricula.getText().trim());
        practicante.setNombres(tfNombres.getText().trim());
        practicante.setApellidoPaterno(tfApellidoPaterno.getText().trim());
        practicante.setApellidoMaterno(tfApellidoMaterno.getText().trim());
        practicante.setCorreo(tfCorreo.getText().trim());
        practicante.setSexo(cmbSexo.getValue());
        practicante.setActivo(true);

        Usuario usuario = new Usuario();
        usuario.setNombre(tfNombreUsuario.getText().trim());
        usuario.setContrasenia(HasheoContrasenia.hashPassword(pfContrasena.getText().trim()));
        practicante.setUsuario(usuario);

        return practicante;
    }

    private boolean confirmarRegistro() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Registro");
        alert.setHeaderText("¿Está seguro de registrar este practicante?");
        alert.setContentText("El practicante se guardará en el sistema.");

        ButtonType btnConfirmar = new ButtonType("Confirmar");
        ButtonType btnSeguirEditando = new ButtonType("Seguir editando");

        alert.getButtonTypes().setAll(btnConfirmar, btnSeguirEditando);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == btnConfirmar;
    }
    
    private boolean confirmarReactivacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Practicante Inactivo");
        alert.setHeaderText(null);
        alert.setContentText("Ya existe un registro de practicante inactivo con esa matricula, ¿desea reactivar al practicante?");

        ButtonType btnConfirmar = new ButtonType("Confirmar");
        ButtonType btnSeguirEditando = new ButtonType("Seguir editando");

        alert.getButtonTypes().setAll(btnConfirmar, btnSeguirEditando);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == btnConfirmar;
    }

    private void regresarAGestionPracticantes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGestionPracticantes.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Gestión de Practicantes");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al volver a la gestión de practicantes: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
