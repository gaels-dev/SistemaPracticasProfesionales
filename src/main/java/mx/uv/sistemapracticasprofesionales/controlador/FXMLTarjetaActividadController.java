package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Controlador para la tarjeta que muestra la información
 *                  resumida de una actividad.
 */
public class FXMLTarjetaActividadController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Button btnEvaluar;

    private SolicitudDocumento actividad;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setDatos(SolicitudDocumento actividad) {
        this.actividad = actividad;
        lblTitulo.setText(actividad.getDocumento().getNombreDocumento());
        lblFecha.setText("Fecha límite: " + 
                actividad.getFechaLimite().toString());
        lblDescripcion.setText(actividad.getDocumento().getTipoDocumento());
    }

    @FXML
    private void handleEvaluar(ActionEvent event) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                    "/fxml/FXMLEvaluarActividad.fxml"));
            Parent raiz = cargador.load();
            FXMLEvaluarActividadController controlador = 
                    cargador.getController();
            controlador.setDatosActividad(actividad);

            Stage escenario = (Stage) btnEvaluar.getScene().getWindow();
            escenario.setScene(new Scene(raiz));
            escenario.setTitle("Evaluar Actividad");
            escenario.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
