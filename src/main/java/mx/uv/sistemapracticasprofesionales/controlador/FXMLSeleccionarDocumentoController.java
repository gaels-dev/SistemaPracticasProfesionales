package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;
import mx.uv.sistemapracticasprofesionales.servicio.PracticanteService;
import mx.uv.sistemapracticasprofesionales.servicio.SolicitudDocumentoService;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Controlador para la selección del tipo de documento o 
 *                  actividad a subir.
 */
public class FXMLSeleccionarDocumentoController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private VBox vboxDocumentos;

    private final SolicitudDocumentoService solicitudService = 
            new SolicitudDocumentoService();
    private final PracticanteService practicanteService = 
            new PracticanteService();
    private String[] tiposPermitidos;
    @FXML
    private Label lblTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Stage escenario = (Stage) btnVolver.getScene().getWindow();
            String titulo = escenario.getTitle();
            
            if (titulo.contains("Documentos")) {
                tiposPermitidos = new String[]{"Administrativo"};
                lblTitulo.setText("Subida de documentos");
            } else {
                tiposPermitidos = new String[]{"Reporte", "Evidencia"};
                lblTitulo.setText("Subida de reportes/evidencias");
            }
            cargarSolicitudes();
        });
    }

    private void cargarSolicitudes() {
        vboxDocumentos.getChildren().clear();
        try {
            Practicante practicante = practicanteService.buscarPorIdUsuario(
                    Sesion.getUsuario().getIdUsuario());
            if (practicante != null) {
                List<SolicitudDocumento> solicitudes = 
                    solicitudService.obtenerSolicitudesPorPracticanteYTipo(
                        practicante.getIdPracticante(), tiposPermitidos
                    );
                
                for (SolicitudDocumento solicitud : solicitudes) {
                    FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                            "/fxml/FXMLTarjetaSubirDocumento.fxml"));
                    Parent tarjeta = cargador.load();
                    
                    FXMLTarjetaSubirDocumentoController controladorTarjeta = 
                            cargador.getController();
                    controladorTarjeta.inicializarTarjeta(solicitud);
                    controladorTarjeta.setOnActionSubir(event -> 
                            handleIrASubir(solicitud));
                    
                    vboxDocumentos.getChildren().add(tarjeta);
                }
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al cargar solicitudes: " 
                    + e.getMessage());
            UtilidadesVistas.mostrarAlerta("Error", 
                    "No se pudieron cargar los documentos disponibles.", 
                    Alert.AlertType.ERROR);
        }
    }

    private void handleIrASubir(SolicitudDocumento solicitud) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                    "/fxml/FXMLSubirDocumentos.fxml"));
            Parent raiz = cargador.load();
            
            FXMLSubirDocumentosController controladorSubida = 
                    cargador.getController();
            controladorSubida.inicializarContexto(solicitud);
            
            Stage escenario = (Stage) btnVolver.getScene().getWindow();
            Scene escena = new Scene(raiz);
            escenario.setScene(escena);
            escenario.setTitle("Gestionar Entrega");
            escenario.show();
            
        } catch (IOException e) {
            System.err.println("Error al navegar a la vista de subida: " 
                    + e.getMessage());
            UtilidadesVistas.mostrarAlerta("Error", 
                    "No se pudo abrir la ventana de subida.", 
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        Stage escenario = (Stage) btnVolver.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLMenuPracticante.fxml", 
                "Menú Practicante");
    }
}
