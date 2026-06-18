package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;

/**
 * FXML Controller class
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Controlador para la vista de tarjeta de practicante
 *                  donde cargar su información
 */
public class FXMLTarjetaPracticanteController implements Initializable {

    @FXML
    private HBox rootHBox;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblMatricula;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;

    private Practicante practicante;
    private FXMLGestionPracticantesController controladorPadre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void inicializarPracticante(Practicante practicante) {
        this.inicializarPracticante(practicante, null);
    }

    public void inicializarPracticante(Practicante practicante, FXMLGestionPracticantesController controladorPadre) {
        this.practicante = practicante;
        this.controladorPadre = controladorPadre;
        
        String nombreCompleto = practicante.getNombres() + " " + 
                                practicante.getApellidoPaterno() + " " + 
                                (practicante.getApellidoMaterno() != null ? practicante.getApellidoMaterno() : "");
        lblNombre.setText(nombreCompleto.trim());
        lblMatricula.setText("Matrícula: " + practicante.getMatricula());
    }

    @FXML
    private void handleClicTarjeta(javafx.scene.input.MouseEvent event) {
        if (controladorPadre != null) {
            controladorPadre.seleccionarPracticante(this);
        }
    }

    public void establecerEstiloSeleccionado(boolean seleccionado) {
        if (seleccionado) {
            rootHBox.setStyle("-fx-background-color: #E0E0E0; -fx-background-radius: 5; -fx-padding: 10; -fx-border-color: #222222; -fx-border-radius: 5;");
        } else {
            rootHBox.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 5; -fx-padding: 10;");
        }
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void ocultarBotonesAccion() {
        btnEditar.setVisible(false);
        btnEditar.setManaged(false);
        btnEliminar.setVisible(false);
        btnEliminar.setManaged(false);
    }
}
