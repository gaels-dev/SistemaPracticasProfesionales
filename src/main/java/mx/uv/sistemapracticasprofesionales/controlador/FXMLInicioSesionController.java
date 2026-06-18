package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.excepciones.ConexionException;
import mx.uv.sistemapracticasprofesionales.excepciones.CredencialesInvalidasException;
import mx.uv.sistemapracticasprofesionales.modelo.dao.PersonalAcademicoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Usuario;
import mx.uv.sistemapracticasprofesionales.servicio.UsuarioService;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;

/**
 * FXML Controller class
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Controlador para la vista de inicio de sesión.
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasenia;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Label lblError;

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ocultarError();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        ocultarError();

        String username = txtUsuario.getText();
        String contrasenia = txtContrasenia.getText();

        if (username.trim().isEmpty() || contrasenia.trim().isEmpty()) {
            mostrarError("Por favor ingresa usuario y contraseña.");
            return;
        }

        try {
            btnIniciarSesion.setDisable(true);

            Usuario usuarioAutenticado = usuarioService.autenticar(username, 
                    contrasenia);
            Sesion.setUsuario(usuarioAutenticado);

            if (usuarioAutenticado.getTipoUsuario().getRol().equals("Profesor") 
                || usuarioAutenticado.getTipoUsuario().getRol().equals(
                        "Coordinador")) {
                PersonalAcademicoDAO personalDAO = new PersonalAcademicoDAO();
                PersonalAcademico personal = 
                        personalDAO.obtenerPersonalAcademicoPorIdUsuario(
                        usuarioAutenticado.getIdUsuario());
                Sesion.setPersonalAcademico(personal);
            }

            redireccionarPorRol(usuarioAutenticado);

        } catch (CredencialesInvalidasException e) {
            mostrarError("Usuario y/o contraseña incorrecta.");
        } catch (SQLException e) {
            mostrarError("Error al procesar la información. "
                    + "Intente de nuevo más tarde.");
        } catch (ConexionException e) {
            mostrarError("No se pudo conectar a la base de datos.");
        } catch (IOException e) {
            mostrarError("Error al abrir el sistema.");
        } finally {
            btnIniciarSesion.setDisable(false);
        }
    }

    private void redireccionarPorRol(Usuario usuario) throws IOException {
        String fxmlPath = "";
        String title = "Sistema de Prácticas Profesionales";

        switch (usuario.getTipoUsuario().getRol()) {
            case "Administrador":
                fxmlPath = "/fxml/FXMLMenuAdministrador.fxml";
                title += " - Administrador";
                break;
            case "Coordinador":
                fxmlPath = "/fxml/FXMLMenuCoordinador.fxml";
                title += " - Coordinador";
                break;
            case "Profesor":
                fxmlPath = "/fxml/FXMLMenuProfesor.fxml";
                title += " - Profesor";
                break;
            case "Practicante":
                fxmlPath = "/fxml/FXMLMenuPracticante.fxml";
                title += " - Practicante";
                break;
            default:
                mostrarError("Rol de usuario no reconocido.");
                return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
        lblError.setManaged(true);
    }

    private void ocultarError() {
        lblError.setVisible(false);
        lblError.setManaged(false);
    }

    @FXML
    private void handleCerrarApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
