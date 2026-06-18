package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.EntregaDocumento;
import mx.uv.sistemapracticasprofesionales.servicio.EntregaDocumentoService;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLTarjetaDocumentoController implements Initializable {

    @FXML
    private HBox rootHBox;
    @FXML
    private Label lblTipoDocumento;
    @FXML
    private Label lblInfoSubida;
    @FXML
    private Label lblEstado;
    @FXML
    private Button btnVer;

    private EntregaDocumento entrega;
    private FXMLValidarDocumentosController controladorPadre;
    private final EntregaDocumentoService entregaService = new EntregaDocumentoService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnVer.setOnAction(this::handleDescargar);
    }    

    public void inicializarDatos(EntregaDocumento entrega, FXMLValidarDocumentosController controladorPadre) {
        this.entrega = entrega;
        this.controladorPadre = controladorPadre;
        
        lblTipoDocumento.setText(entrega.getSolicitudDocumento().getDocumento().getNombreDocumento());
        String info = "Subido: " + (entrega.getFechaEntrega() != null ? entrega.getFechaEntrega().toString() : "N/A");
        if (entrega.getNombreArchivo() != null) {
            info += " - " + entrega.getNombreArchivo();
        }
        lblInfoSubida.setText(info);
        
        lblEstado.setText(entrega.getEstado());
        configurarEstiloEstado(entrega.getEstado());
    }

    private void configurarEstiloEstado(String estado) {
        switch (estado) {
            case "Validado":
                lblEstado.setStyle("-fx-background-color: #008000; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 5 10 5 10;");
                break;
            case "Rechazado":
                lblEstado.setStyle("-fx-background-color: #CC0000; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 5 10 5 10;");
                break;
            case "Pendiente de validacion":
                lblEstado.setStyle("-fx-background-color: #FF8C00; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 5 10 5 10;");
                break;
            default:
                lblEstado.setStyle("-fx-background-color: #888888; -fx-text-fill: white; -fx-background-radius: 8; -fx-padding: 5 10 5 10;");
                break;
        }
    }

    @FXML
    private void handleClicTarjeta(MouseEvent event) {
        if (controladorPadre != null) {
            controladorPadre.seleccionarDocumento(this);
        }
    }

    private void handleDescargar(ActionEvent event) {
        try {
            byte[] archivo = entregaService.obtenerArchivo(entrega.getIdEntregaDocumento());
            if (archivo != null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar Documento");
                fileChooser.setInitialFileName(entrega.getNombreArchivo() != null ? entrega.getNombreArchivo() : "documento");
                
                if (entrega.getExtension() != null) {
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos " + entrega.getExtension(), "*" + entrega.getExtension()));
                }
                
                File destination = fileChooser.showSaveDialog(btnVer.getScene().getWindow());
                if (destination != null) {
                    try (FileOutputStream fos = new FileOutputStream(destination)) {
                        fos.write(archivo);
                        mostrarAlerta("Éxito", "Documento descargado correctamente.", Alert.AlertType.INFORMATION);
                    }
                }
            } else {
                mostrarAlerta("Error", "No se encontró el archivo en la base de datos.", Alert.AlertType.ERROR);
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al descargar el archivo: " + e.getMessage());
            mostrarAlerta("Error", "No se pudo descargar el documento.", Alert.AlertType.ERROR);
        }
    }

    public void establecerEstiloSeleccionado(boolean seleccionado) {
        if (seleccionado) {
            rootHBox.setStyle("-fx-background-color: #E0E0E0; -fx-background-radius: 12; -fx-padding: 15; -fx-border-color: #222222; -fx-border-radius: 12;");
        } else {
            rootHBox.setStyle("-fx-background-color: #F5EFEF; -fx-background-radius: 12; -fx-padding: 15;");
        }
    }

    public EntregaDocumento getEntrega() {
        return entrega;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
