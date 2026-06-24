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
        PersonalAcademico personal = recolectarDatos();
        if (validarCampos(personal)) {
            try {
                if (personalService.existePersonalPorNumeroYRol(
                        personal.getNoPersonal(), rolActual)) {
                    UtilidadesVistas.mostrarAlerta("Número de Personal Duplicado", 
                            "Ya existe un " + rolActual + " con el mismo número de personal.", 
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
                String mensajeError = e.getMessage();
                if (mensajeError != null && mensajeError.contains("Duplicate entry")) {
                    if (mensajeError.contains("correo_unico")) {
                        mensajeError = "El correo electrónico ya se encuentra registrado.";
                    } else if (mensajeError.contains("uq_personal_no_personal")) {
                        mensajeError = "El número de personal ya se encuentra registrado.";
                    }
                }
                UtilidadesVistas.mostrarAlerta("Error", mensajeError, 
                        Alert.AlertType.ERROR);
            }
        }
    }
    
    private boolean validarCampos(PersonalAcademico personal) {
        String camposVacios = validarCamposVacios();
        if (camposVacios.isEmpty()) {
            String errores = personalService.validarFormato(personal);
            if (txtContrasenia.getText().length() < 5) {
                errores += "-La contraseña debe tener al menos 5 caracteres.\n";
            }
            if (errores.isEmpty()) {
                return true;
            } else {
                UtilidadesVistas.mostrarAlerta("Formato de datos invalido", 
                        errores, 
                        Alert.AlertType.WARNING);
            }
        } else {
            UtilidadesVistas.mostrarAlerta("Campos Vacíos", camposVacios, 
                    Alert.AlertType.WARNING);
        }
        return false;
    }

    private String validarCamposVacios() {
        StringBuilder camposVacios = new StringBuilder();
        
        if (txtNombres.getText().trim().isEmpty()) {
            camposVacios.append("- Nombres es obligatorio.\n");
        }
        if (txtApellidoPaterno.getText().trim().isEmpty()) {
            camposVacios.append("- Apellido paterno es obligatorio.\n");
        }
        if (txtApellidoMaterno.getText().trim().isEmpty()) {
            camposVacios.append("- Apellido materno es obligatorio.\n");
        }
        if (txtNumPersonal.getText().trim().isEmpty()) {
            camposVacios.append("- Número de personal es obligatorio.\n");
        }
        String correo = txtCorreo.getText().trim();
        if (correo.isEmpty()) {
            camposVacios.append("- Correo electrónico es obligatorio.\n");
        } 
        if (txtContrasenia.getText().trim().isEmpty()) {
            camposVacios.append("- Contraseña es obligatoria.\n");
        } 

        return camposVacios.toString();
    }

    private String limpiarTexto(String texto) {
        if (texto == null) return "";
        return texto.trim().replaceAll("\\s{2,}", " ");
    }

    private PersonalAcademico recolectarDatos() {
        PersonalAcademico personal = new PersonalAcademico();
        personal.setNoPersonal(txtNumPersonal.getText().trim());
        personal.setNombres(limpiarTexto(txtNombres.getText()));
        personal.setApellidoPaterno(limpiarTexto(txtApellidoPaterno.getText()));
        personal.setApellidoMaterno(limpiarTexto(txtApellidoMaterno.getText()));
        personal.setCorreo(txtCorreo.getText().trim());
        if (rolActual.equals("Coordinador")) {
            personal.setActivo(false);
        } else {
            personal.setActivo(true);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(txtCorreo.getText().trim());
        usuario.setContrasenia(HasheoContrasenia.hashContrasenia(
                txtContrasenia.getText().trim()));
        if (rolActual.equals("Coordinador")) {
            usuario.setActivo(false);
        } else {
            usuario.setActivo(true);
        }

        personal.setUsuario(usuario);

        return personal;
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) lblTitulo.getScene().getWindow();
        escenario.close();
    }
}
