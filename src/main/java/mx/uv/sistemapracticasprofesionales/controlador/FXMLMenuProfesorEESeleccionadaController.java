package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * FXML Controller class
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Controlador para el menú del profesor con EE seleccionada.
 */
public class FXMLMenuProfesorEESeleccionadaController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Button btnNotificaciones;
    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnPracticantesAsignados;
    @FXML
    private Button btnActividades;
    @FXML
    private Label lblNombre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Sesion.getUsuario() != null) {
            String mensaje = Sesion.getUsuario().getNombre();
            if (Sesion.getEeSeleccionada() != null) {
                mensaje += " - " + Sesion.getEeSeleccionada().getNombre();
            }
            lblNombre.setText(mensaje);
        }
    }

    @FXML
    private void handleActividades(ActionEvent event) {
        Stage escenario = (Stage) lblNombre.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLActividades.fxml", 
                "Actividades - " + Sesion.getEeSeleccionada().getNombre());
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        Stage escenario = (Stage) btnVolver.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLMenuProfesor.fxml", 
                "Menú Principal - Profesor");
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        Sesion.cerrarSesion();
        Stage escenario = (Stage) lblNombre.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLInicioSesion.fxml", 
                "Inicio de Sesión");
    }
}
