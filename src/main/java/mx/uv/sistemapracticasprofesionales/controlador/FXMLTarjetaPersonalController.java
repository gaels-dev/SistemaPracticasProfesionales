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

import mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico;
import mx.uv.sistemapracticasprofesionales.servicio.PersonalAcademicoService;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  16/06/2026
 * Descripción:     Controlador para la tarjeta de información del personal 
 *                  académico.
 */
public class FXMLTarjetaPersonalController implements Initializable {

    @FXML
    private Label lblNombre;
    @FXML
    private Button btnEditar;
    private PersonalAcademico personal;
    @FXML
    private Button btnInactivar;
    @FXML
    private Button btnActivar;
    
    private final PersonalAcademicoService personalService = 
            new PersonalAcademicoService();
    private FXMLGestionPersonalController controllerPadre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void inicializarPersonal(PersonalAcademico personal) {
        this.personal = personal;
        
        String nombreCompleto = personal.getNombres() + " " + 
                                personal.getApellidoPaterno() + " " + 
                                (personal.getApellidoMaterno() != null ? 
                                personal.getApellidoMaterno() : "");
        lblNombre.setText(nombreCompleto.trim());

        if (personal.getActivo()) {
            btnActivar.setManaged(false);
            btnActivar.setVisible(false);
        } else {
            btnInactivar.setManaged(false);
            btnInactivar.setVisible(false);
        }
    }
    
    public void setControllerPadre(FXMLGestionPersonalController controllerPadre) {
        this.controllerPadre = controllerPadre;
    }

    @FXML
    private void handleActivar(ActionEvent event) {
        if (personal.getUsuario().getTipoUsuario().getRol().equals("Coordinador")) {
            Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            alertaConfirmacion.setTitle("Confirmar activación");
            alertaConfirmacion.setHeaderText("Activar coordinador");
            alertaConfirmacion.setContentText("Al activar este coordinador se desactivará"
                    + " al coordinador activo actualmente. ¿Deseas continuar?");
        
            Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();
        
            if (resultado.get() == ButtonType.OK) {
                try {
                    if (personalService.activarCoordinador(personal)) {
                        UtilidadesVistas.mostrarAlerta("Activación exitosa", 
                                "El nuevo coordinador fue activado de manera exitosa", 
                                Alert.AlertType.INFORMATION);
                        if (controllerPadre != null) {
                            controllerPadre.recargarVista();
                        }
                    }
                } catch (SQLException e) {
                    UtilidadesVistas.mostrarAlerta("Error", 
                            "Error al activar al " + personal.getUsuario().getTipoUsuario().getRol(),
                            Alert.AlertType.INFORMATION);
                }
            }
        }
    }
}
