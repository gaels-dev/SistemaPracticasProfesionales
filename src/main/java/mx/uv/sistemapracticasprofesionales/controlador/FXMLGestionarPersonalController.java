package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;
import mx.uv.sistemapracticasprofesionales.servicio.PersonalAcademicoService;
import mx.uv.sistemapracticasprofesionales.utilidades.HasheoContrasenia;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  16/06/2026
 * Descripción:     Controlador para la vista Gestionar Personal que permite
 *                  agregar o modificar a un usuario de personal académico.
 */
public class FXMLGestionarPersonalController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtNumPersonal;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasenia;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    
    private String rolActual;
    private final PersonalAcademicoService personalService = 
            new PersonalAcademicoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Stage escenario = (Stage) lblTitulo.getScene().getWindow();
            String titulo = escenario.getTitle();
            
            if (titulo.contains("Coordinador")) {
                rolActual = "Coordinador";
                lblTitulo.setText("Registrar Coordinador");
            } else {
                rolActual = "Profesor";
                lblTitulo.setText("Registrar Profesor");
            }
        });
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        String errores = validarCampos();
        if (errores.isEmpty()) {
            PersonalAcademico personal = recolectarDatos();
            try {
                if (personalService.existePersonalPorNumeroYRol(
                        personal.getNoPersonal(), rolActual)) {
                    UtilidadesVistas.mostrarAlerta("Número de Personal "
                            + "Duplicado", 
                            "Ya existe un " + rolActual + 
                                    " con el mismo número de personal.", 
                            Alert.AlertType.WARNING);
                    return;
                }

                if (personalService.registrarPersonal(personal, rolActual)) {
                    UtilidadesVistas.mostrarAlerta("Registro Exitoso", 
                            "El " + rolActual + 
                                    " ha sido registrado correctamente.", 
                            Alert.AlertType.INFORMATION);
                    cerrarVentana();
                } else {
                    UtilidadesVistas.mostrarAlerta("Error", 
                            "No se pudo realizar el registro.", 
                            Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                UtilidadesVistas.mostrarAlerta("Error", e.getMessage(), 
                        Alert.AlertType.ERROR);
            }
        } else {
            UtilidadesVistas.mostrarAlerta("Campos Inválidos", errores, 
                    Alert.AlertType.WARNING);
        }
    }

    private String validarCampos() {
        StringBuilder errores = new StringBuilder();
        
        validarNombre(txtNombres.getText().trim(), "Nombre(s)", errores);
        validarNombre(txtApellidoPaterno.getText().trim(), 
                "Apellido Paterno", errores);
        validarNombre(txtApellidoMaterno.getText().trim(), 
                "Apellido Materno", errores);

        if (txtNumPersonal.getText().trim().isEmpty()) {
            errores.append("- Número de personal es obligatorio.\n");
        }
        
        String correo = txtCorreo.getText().trim();
        if (correo.isEmpty()) {
            errores.append("- Correo electrónico es obligatorio.\n");
        } else if (!correo.matches("^[\\w.-]+@uv\\.mx$")) {
            errores.append("- El correo electrónico debe pertenecer al "
                    + "dominio @uv.mx\n");
        }

        if (txtUsuario.getText().trim().isEmpty()) {
            errores.append("- Nombre de usuario es obligatorio.\n");
        }
        
        if (txtContrasenia.getText().trim().isEmpty()) {
            errores.append("- Contraseña es obligatoria.\n");
        } else if (txtContrasenia.getText().length() < 6) {
            errores.append("- La contraseña debe tener al menos "
                    + "6 caracteres.\n");
        }
        
        return errores.toString();
    }

    private void validarNombre(String texto, String nombreCampo, 
            StringBuilder errores) {
        if (texto.isEmpty()) {
            errores.append("- ").append(nombreCampo).append(""
                    + " es obligatorio.\n");
        } else if (texto.length() < 3) {
            errores.append("- ").append(nombreCampo).append(
                    " debe tener al menos 3 letras.\n");
        } else if (!texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            errores.append("- ").append(nombreCampo)
                .append(" solo debe contener letras y espacios.\n");
        } else if (texto.toLowerCase().matches(".*(.)\\1{2,}.*")) {
            errores.append("- ").append(nombreCampo)
                .append(" no puede tener un mismo carácter repetido 3 o más "
                        + "veces consecutivas.\n");
        }
    }

    private PersonalAcademico recolectarDatos() {
        PersonalAcademico personal = new PersonalAcademico();
        personal.setNoPersonal(txtNumPersonal.getText().trim());
        personal.setNombres(txtNombres.getText().trim());
        personal.setApellidoPaterno(txtApellidoPaterno.getText().trim());
        personal.setApellidoMaterno(txtApellidoMaterno.getText().trim());
        personal.setActivo(true);
        
        Usuario usuario = new Usuario();
        usuario.setNombre(txtUsuario.getText().trim());
        usuario.setContrasenia(HasheoContrasenia.hashPassword(
                txtContrasenia.getText().trim()));
        usuario.setActivo(true);
        
        personal.setUsuario(usuario);
        
        return personal;
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) lblTitulo.getScene().getWindow();
        escenario.close();
    }
}
