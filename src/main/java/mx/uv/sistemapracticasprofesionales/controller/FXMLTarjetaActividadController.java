package mx.uv.sistemapracticasprofesionales.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLTarjetaActividadController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
