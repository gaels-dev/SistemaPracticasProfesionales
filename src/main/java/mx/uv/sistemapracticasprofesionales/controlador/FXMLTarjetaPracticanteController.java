package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLTarjetaPracticanteController implements Initializable {

    @FXML
    private Label lblNombre;
    @FXML
    private Label lblMatricula;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    private Practicante practicante;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarPracticante(Practicante practicante) {
        this.practicante = practicante;
        
        String nombreCompleto = practicante.getNombres() + " " + 
                                practicante.getApellidoPaterno() + " " + 
                                (practicante.getApellidoMaterno() != null ? practicante.getApellidoMaterno() : "");
        lblNombre.setText(nombreCompleto.trim());
        lblMatricula.setText("Matrícula: " + practicante.getMatricula());
    }
}
