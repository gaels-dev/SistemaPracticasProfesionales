package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Notificacion;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.servicio.NotificacionService;
import mx.uv.sistemapracticasprofesionales.servicio.PracticanteService;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * FXML Controller class
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Controlador para el menú principal del practicante.
 */
public class FXMLMenuPracticanteController implements Initializable {

    @FXML
    private Button btnNotificaciones;
    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnSubirDocumentos;
    @FXML
    private Button btnConsultarProyecto;
    @FXML
    private Button btnReportes;
    @FXML
    private Label lblNombre;

    private final PracticanteService practicanteService = 
            new PracticanteService();
    private final NotificacionService notificacionService = 
            new NotificacionService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Sesion.getUsuario() != null) {
            lblNombre.setText(Sesion.getUsuario().getNombre());
            revisarNotificaciones();
        }
    }    

    private void revisarNotificaciones() {
        Platform.runLater(() -> {
            try {
                Practicante practicante = practicanteService.buscarPorIdUsuario(
                        Sesion.getUsuario().getIdUsuario());
                if (practicante != null) {
                    List<Notificacion> notificaciones = 
                        notificacionService.obtenerNotificacionesPorPracticante(
                                practicante.getIdPracticante());
                    for (Notificacion notificacion : notificaciones) {
                        UtilidadesVistas.mostrarAlerta("Nueva notificacion", 
                                notificacion.getMensaje(), 
                                Alert.AlertType.INFORMATION);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener notificaciones: " 
                        + e.getMessage());
            }
        });
    }

    @FXML
    private void handleSubirDocumentos(ActionEvent event) {
        Stage escenario = (Stage) lblNombre.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLSeleccionarDocumento.fxml", 
                "Subir Documentos");
    }

    @FXML
    private void handleReportes(ActionEvent event) {
        Stage escenario = (Stage) lblNombre.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLSeleccionarDocumento.fxml", 
                "Subir Actividades");
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
