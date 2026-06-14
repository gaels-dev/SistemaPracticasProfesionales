package mx.uv.sistemapracticasprofesionales.controlador;

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
public class FXMLTarjetaProyectoController implements Initializable {

    @FXML
    private Label lblNombreProyecto;
    @FXML
    private Label lblOrganizacion;
    @FXML
    private Label lblAsignados;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblResponsable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
