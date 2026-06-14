package mx.uv.sistemapracticasprofesionales.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLEvaluarActividadController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Label lblNombreActividad;
    @FXML
    private Label lblTituloPanel;
    @FXML
    private ComboBox<String> cbPracticantes;
    @FXML
    private VBox vboxDetallesReporte;
    @FXML
    private Label lblTipoEntrega;
    @FXML
    private Label lblFechaEntrega;
    @FXML
    private Label lblFechaLimite;
    @FXML
    private Button btnDescargar;
    @FXML
    private Button btnCalificar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarVisibilidadDinamica();
    }

    private void configurarVisibilidadDinamica() {
        cbPracticantes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                vboxDetallesReporte.setVisible(true);
                vboxDetallesReporte.setManaged(true);
            } else {
                vboxDetallesReporte.setVisible(false);
                vboxDetallesReporte.setManaged(false);
            }
        });
    }
}
