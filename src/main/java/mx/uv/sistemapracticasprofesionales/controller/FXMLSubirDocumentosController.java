package mx.uv.sistemapracticasprofesionales.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLSubirDocumentosController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private VBox vboxDocumentos;
    @FXML
    private VBox vboxReportes;
    @FXML
    private TextField txtRutaArchivo;
    @FXML
    private Button btnExaminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnSubirReporte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
