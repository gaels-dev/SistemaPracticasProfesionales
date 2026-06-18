package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.EntregaDocumento;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.servicio.EntregaDocumentoService;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLValidarDocumentosController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Label lblNombrePracticante;
    @FXML
    private Label lblMatriculaPracticante;
    @FXML
    private VBox vboxDocumentos;
    @FXML
    private Button btnRechazarDocumento;
    @FXML
    private Button btnValidarDocumento;

    private Practicante practicante;
    private final EntregaDocumentoService entregaService = new EntregaDocumentoService();
    private FXMLTarjetaDocumentoController tarjetaSeleccionada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnValidarDocumento.setDisable(true);
        btnRechazarDocumento.setDisable(true);
        btnVolver.setOnAction(this::handleVolver);
    }    

    public void inicializarDatos(Practicante practicante) {
        this.practicante = practicante;
        lblNombrePracticante.setText(practicante.getNombres() + " " + practicante.getApellidoPaterno());
        lblMatriculaPracticante.setText("Matrícula: " + practicante.getMatricula());
        cargarDocumentos();
    }

    private void cargarDocumentos() {
        vboxDocumentos.getChildren().clear();
        btnValidarDocumento.setDisable(true);
        btnRechazarDocumento.setDisable(true);
        tarjetaSeleccionada = null;
        
        try {
            List<EntregaDocumento> entregas = entregaService.obtenerDocumentosPendientesPorPracticante(practicante.getIdPracticante());
            if (entregas.isEmpty()) {
                vboxDocumentos.getChildren().add(new Label("No hay documentos pendientes de validación."));
            } else {
                for (EntregaDocumento entrega : entregas) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLTarjetaDocumento.fxml"));
                    Parent tarjeta = loader.load();
                    
                    FXMLTarjetaDocumentoController controlador = loader.getController();
                    controlador.inicializarDatos(entrega, this);
                    
                    vboxDocumentos.getChildren().add(tarjeta);
                }
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al cargar los documentos: " + e.getMessage());
            mostrarAlerta("Error", "No se pudieron cargar los documentos del practicante.", Alert.AlertType.ERROR);
        }
    }

    public void seleccionarDocumento(FXMLTarjetaDocumentoController controlador) {
        if (tarjetaSeleccionada != null) {
            tarjetaSeleccionada.establecerEstiloSeleccionado(false);
        }
        tarjetaSeleccionada = controlador;
        tarjetaSeleccionada.establecerEstiloSeleccionado(true);
        btnValidarDocumento.setDisable(false);
        btnRechazarDocumento.setDisable(false);
    }

    @FXML
    private void handleValidarDocumento(ActionEvent event) {
        if (tarjetaSeleccionada == null) return;
        
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Validación");
        confirmacion.setHeaderText("¿Está seguro de que desea validar este documento?");
        confirmacion.setContentText("El estado cambiará a 'Validado'.");
        
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                boolean exito = entregaService.validarDocumento(tarjetaSeleccionada.getEntrega().getIdEntregaDocumento());
                if (exito) {
                    mostrarAlerta("Documento validado", "Documento validado correctamente.", Alert.AlertType.INFORMATION);
                    cargarDocumentos();
                } else {
                    mostrarAlerta("Error", "No se pudo validar el documento.", Alert.AlertType.ERROR);
                }
            } catch (SQLException e) {
                System.err.println("Error al validar el documento: " + e.getMessage());
                mostrarAlerta("Error", "Ocurrió un error en la base de datos.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleRechazarDocumento(ActionEvent event) {
        if (tarjetaSeleccionada == null) return;
        
        TextInputDialog dialogoMotivo = new TextInputDialog();
        dialogoMotivo.setTitle("Rechazar Documento");
        dialogoMotivo.setHeaderText("Ingrese el motivo de rechazo:");
        dialogoMotivo.setContentText("Motivo:");
        
        Optional<String> motivo = dialogoMotivo.showAndWait();
        if (motivo.isPresent() && !motivo.get().trim().isEmpty()) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Rechazo");
            confirmacion.setHeaderText("¿Está seguro de que desea rechazar este documento?");
            confirmacion.setContentText("El estado cambiará a 'Rechazado'.");
            
            Optional<ButtonType> resultadoConfirmacion = confirmacion.showAndWait();
            if (resultadoConfirmacion.isPresent() && resultadoConfirmacion.get() == ButtonType.OK) {
                try {
                    boolean exito = entregaService.rechazarDocumento(tarjetaSeleccionada.getEntrega().getIdEntregaDocumento(), motivo.get().trim());
                    if (exito) {
                        mostrarAlerta("Documento rechazado", "Documento rechazado correctamente.", Alert.AlertType.INFORMATION);
                        cargarDocumentos();
                    } else {
                        mostrarAlerta("Error", "No se pudo rechazar el documento.", Alert.AlertType.ERROR);
                    }
                } catch (SQLException e) {
                    System.err.println("Error al rechazar el documento: " + e.getMessage());
                    mostrarAlerta("Error", "Ocurrió un error en la base de datos.", Alert.AlertType.ERROR);
                }
            }
        } else if (motivo.isPresent()) {
            mostrarAlerta("Atención", "Debe ingresar un motivo de rechazo.", Alert.AlertType.WARNING);
        }
    }

    private void handleVolver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGestionPracticantes.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Gestión de Practicantes");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al volver a la vista anterior: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
