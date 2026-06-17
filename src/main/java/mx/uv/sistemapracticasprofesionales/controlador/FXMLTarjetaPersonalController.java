package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico;

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
    @FXML
    private Button btnEliminar;

    private PersonalAcademico personal;

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
    }
}
