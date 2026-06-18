package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ExperienciaEducativa;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * FXML Controller class
 * Autor: Oscar Turrent Peña
 * Fecha creación: 17/06/2026
 * Descripción: Controlador para la tarjeta de Experiencia Educativa.
 */
public class FXMLTarjetaExperienciaEducativaController implements Initializable {

    @FXML
    private Label lblNombreEE;
    @FXML
    private Label lblSeccion;
    @FXML
    private Label lblPeriodo;
    @FXML
    private VBox vboxTarjeta;

    private ExperienciaEducativa ee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setDatos(ExperienciaEducativa ee) {
        this.ee = ee;
        if (ee != null) {
            // Forzar color oscuro para asegurar visibilidad
            lblNombreEE.setStyle("-fx-text-fill: #333333;");
            lblSeccion.setStyle("-fx-text-fill: #555555;");
            lblPeriodo.setStyle("-fx-text-fill: #555555;");

            lblNombreEE.setText(ee.getNombre());
            lblSeccion.setText("Sección: " + ee.getSeccion());
            if (ee.getPeriodo() != null) {
                lblPeriodo.setText("Periodo: " + ee.getPeriodo().getNombre());
            } else {
                lblPeriodo.setText("Periodo: Sin asignar");
            }
        }
    }

    @FXML
    private void handleSeleccionarEE(MouseEvent event) {
        if (ee != null) {
            Sesion.setEeSeleccionada(ee);
            Stage escenario = (Stage) vboxTarjeta.getScene().getWindow();
            UtilidadesVistas.cargarVista(escenario, 
                    "/fxml/FXMLMenuProfesorEESeleccionada.fxml", 
                    "Menú de EE - " + ee.getNombre());
        }
    }
}
