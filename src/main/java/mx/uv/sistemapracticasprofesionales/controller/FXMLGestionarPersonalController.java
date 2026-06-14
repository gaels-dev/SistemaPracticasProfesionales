package mx.uv.sistemapracticasprofesionales.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLGestionarPersonalController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNumPersonal;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
