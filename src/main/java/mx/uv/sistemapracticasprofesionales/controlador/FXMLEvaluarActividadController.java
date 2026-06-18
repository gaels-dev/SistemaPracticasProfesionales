package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.dao.EntregaDocumentoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.EntregaDocumento;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor: Oscar Turrent Peña Fecha creación: 17/06/2026 Descripción: Controlador
 * para la vista de evaluación de una actividad específica, permitiendo
 * seleccionar practicantes y ver entregas.
 */
public class FXMLEvaluarActividadController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Label lblNombreActividad;
    @FXML
    private Label lblTituloPanel;
    @FXML
    private ComboBox<EntregaDocumento> cbPracticantes;
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

    private SolicitudDocumento actividad;
    private ObservableList<EntregaDocumento> entregas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarVisibilidadDinamica();
        configurarSeleccionPracticante();
    }

    public void setDatosActividad(SolicitudDocumento actividad) {
        this.actividad = actividad;
        lblNombreActividad.setText(
                actividad.getDocumento().getNombreDocumento());
        lblTituloPanel.setText(actividad.getDocumento().getNombreDocumento());
        lblFechaLimite.setText("Fecha límite: "
                + actividad.getFechaLimite().toString());
        lblTipoEntrega.setText(actividad.getDocumento().getTipoDocumento());
        cargarEntregas();
    }

    private void cargarEntregas() {
        EntregaDocumentoDAO dao = new EntregaDocumentoDAO();
        try {
            List<EntregaDocumento> lista
                    = dao.obtenerEntregasPorSolicitud(
                            actividad.getSolicitudDocumento());
            entregas = FXCollections.observableArrayList(lista);
            cbPracticantes.setItems(entregas);
        } catch (SQLException e) {
            UtilidadesVistas.mostrarAlerta(
                    "Error de BD",
                    "No se pudieron cargar las entregas: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void configurarVisibilidadDinamica() {
        cbPracticantes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    boolean visible = (newValue != null);
                    vboxDetallesReporte.setVisible(visible);
                    vboxDetallesReporte.setManaged(visible);
                });
    }

    private void configurarSeleccionPracticante() {
        cbPracticantes.setConverter(
                new javafx.util.StringConverter<EntregaDocumento>() {
            @Override
            public String toString(EntregaDocumento object) {
                return (object == null) ? ""
                        : object.getPracticante().getNombres() + " "
                        + object.getPracticante().getApellidoPaterno();
            }

            @Override
            public EntregaDocumento fromString(String string) {
                return null;
            }
        });

        cbPracticantes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getIdEntregaDocumento() != null) {
                    lblFechaEntrega.setText("Entregado: "
                            + newValue.getFechaEntrega().toString());
                    btnDescargar.setDisable(false);
                    btnCalificar.setDisable(false);
                } else {
                    lblFechaEntrega.setText(
                            "Aún no ha entregado este documento");
                    btnDescargar.setDisable(true);
                    btnCalificar.setDisable(true);
                }
            }
        });
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        Stage escenario = (Stage) btnVolver.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario,
                "/fxml/FXMLActividades.fxml", "Actividades");
    }

    @FXML
    private void handleDescargar(ActionEvent event) {
        EntregaDocumento seleccionado
                = cbPracticantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null
                && seleccionado.getIdEntregaDocumento() != null) {
            EntregaDocumentoDAO dao = new EntregaDocumentoDAO();
            try {
                byte[] archivo = dao.obtenerArchivoEntregado(
                        seleccionado.getIdEntregaDocumento());
                if (archivo != null) {
                    FileChooser selectorArchivo = new FileChooser();
                    selectorArchivo.setTitle("Guardar entrega");

                    String nombreArchivo = seleccionado.getNombreArchivo();
                    if (nombreArchivo == null || nombreArchivo.isEmpty()) {
                        nombreArchivo = "entrega_"
                                + seleccionado.getPracticante().getMatricula();
                        if (seleccionado.getExtension() != null) {
                            nombreArchivo += seleccionado.getExtension();
                        }
                    }

                    selectorArchivo.setInitialFileName(nombreArchivo);
                    File destino = selectorArchivo.showSaveDialog(
                            btnDescargar.getScene().getWindow());
                    if (destino != null) {
                        try (FileOutputStream flujoSalidaArchivos
                                = new FileOutputStream(destino)) {
                            flujoSalidaArchivos.write(archivo);
                            UtilidadesVistas.mostrarAlerta(
                                    "Descarga completada",
                                    "El archivo se ha guardado exitosamente.",
                                    Alert.AlertType.INFORMATION);
                        }
                    }
                } else {
                    UtilidadesVistas.mostrarAlerta(
                            "Sin archivo", 
                            "No se encontró el archivo de la entrega.", 
                            Alert.AlertType.WARNING);
                }
            } catch (SQLException | IOException e) {
                UtilidadesVistas.mostrarAlerta(
                        "Error", 
                        "No se pudo descargar el archivo: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleCalificar(ActionEvent event) {
        EntregaDocumento seleccionado = 
                cbPracticantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                        "/fxml/FXMLEvaluarEntregaPracticante.fxml"));
                Parent raiz = cargador.load();
                FXMLEvaluarEntregaPracticanteController controlador = 
                        cargador.getController();
                controlador.setDatos(seleccionado);

                Stage ventanaModal = new Stage();
                ventanaModal.setScene(new Scene(raiz));
                ventanaModal.setTitle("Registrar Calificación");
                ventanaModal.initModality(Modality.APPLICATION_MODAL);
                ventanaModal.initOwner(btnCalificar.getScene().getWindow());
                ventanaModal.showAndWait();
                
                cargarEntregas();
            } catch (IOException e) {
                UtilidadesVistas.mostrarAlerta(
                        "Error", 
                        "No se pudo abrir la ventana de evaluación: " 
                                + e.getMessage(), 
                        Alert.AlertType.ERROR);
            }
        }
    }
}
