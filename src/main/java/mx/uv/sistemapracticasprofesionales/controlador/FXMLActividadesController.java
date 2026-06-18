package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.dao.SolicitudDocumentoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ExperienciaEducativa;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Controlador para la vista de lista de actividades de una
 *                  experiencia educativa.
 */
public class FXMLActividadesController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private VBox vboxActividades;
    @FXML
    private Button btnAgregarActividad;

    private ExperienciaEducativa eeSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.eeSeleccionada = Sesion.getEeSeleccionada();
        if (eeSeleccionada != null) {
            cargarActividades();
        }
    }

    private void cargarActividades() {
        vboxActividades.getChildren().clear();
        SolicitudDocumentoDAO dao = new SolicitudDocumentoDAO();
        try {
            List<SolicitudDocumento> listaActividades = 
                    dao.obtenerSolicitudesPorExperiencia(
                            eeSeleccionada.getIdExperienciaEducativa());
            if (listaActividades.isEmpty()) {
                UtilidadesVistas.mostrarAlerta(
                        "Sin actividades", 
                        "No hay actividades registradas para esta "
                                + "experiencia educativa.", 
                        Alert.AlertType.INFORMATION);
            } else {
                for (SolicitudDocumento actividad : listaActividades) {
                    vboxActividades.getChildren().add(crearTarjetaActividad(
                            actividad));
                }
            }
        } catch (SQLException e) {
            UtilidadesVistas.mostrarAlerta(
                    "Error de BD", 
                    "No se pudieron cargar las actividades: " + e.getMessage(), 
                    Alert.AlertType.ERROR);
        }
    }

    private Node crearTarjetaActividad(SolicitudDocumento actividad) {
        Node tarjeta = null;
        try {
            FXMLLoader cargador = new FXMLLoader(
                    getClass().getResource("/fxml/FXMLTarjetaActividad.fxml"));
            tarjeta = cargador.load();
            FXMLTarjetaActividadController controlador = 
                    cargador.getController();
            controlador.setDatos(actividad);
        } catch (Exception e) {
            System.err.println("Error al cargar tarjeta: " + e.getMessage());
        }
        return tarjeta;
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        Stage escenario = (Stage) btnVolver.getScene().getWindow();
        UtilidadesVistas.cargarVista(
                escenario, 
                "/fxml/FXMLMenuProfesorEESeleccionada.fxml", 
                "Menú Profesor - EE Seleccionada");
    }

}
