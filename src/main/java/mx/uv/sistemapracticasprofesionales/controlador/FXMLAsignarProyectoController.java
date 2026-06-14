package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLAsignarProyectoController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private VBox vboxPracticantes;
    @FXML
    private VBox vboxProyectos;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnRegistrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
