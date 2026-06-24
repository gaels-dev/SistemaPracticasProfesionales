package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.EntregaDocumento;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  16/06/2026
 * Descripción:     Controlador para la tarjeta de selección de documento a 
 *                  subir o visualizar entrega.
 */
public class FXMLTarjetaSubirDocumentoController implements Initializable {

    @FXML
    private Label lblNombreDocumento;
    @FXML
    private Label lblEstado;
    @FXML
    private Label lblCalificacion;
    @FXML
    private Button btnDescargar;
    @FXML
    private Button btnSubir;
    @FXML
    private HBox hboxAcciones;
    
    private SolicitudDocumento solicitud;
    private EntregaDocumento entrega;
    private byte[] datosArchivo;
    private boolean esModoEntrega = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void inicializarTarjeta(SolicitudDocumento solicitud) {
        this.solicitud = solicitud;
        this.datosArchivo = solicitud.getDocumento().getFormato();
        lblNombreDocumento.setText(
                solicitud.getDocumento().getNombreDocumento());
        lblEstado.setText(
                "Tipo: " + solicitud.getDocumento().getTipoDocumento() 
                + " | Límite: " + solicitud.getFechaLimite());
        
        if (solicitud.getDocumento().getTipoDocumento().equalsIgnoreCase(
                "Administrativo")) {
            btnDescargar.setVisible(true);
            btnDescargar.setManaged(true);
            btnDescargar.setText("Descargar formato");
        }
    }

    public void inicializarTarjetaEntrega(SolicitudDocumento solicitud, 
            EntregaDocumento entrega, byte[] archivoEntrega) {
        this.solicitud = solicitud;
        this.entrega = entrega;
        this.datosArchivo = archivoEntrega;
        this.esModoEntrega = true;
        lblNombreDocumento.setText(
                solicitud.getDocumento().getNombreDocumento());
        
        if (entrega.getEstado().equals("Evaluado")) {
            lblEstado.setText("Estado: Evaluado");
            lblEstado.setStyle("-fx-text-fill: #008000;");
            if (entrega.getCalificacion() != null) {
                lblCalificacion.setText("Calificación: " + entrega.getCalificacion());
                lblCalificacion.setVisible(true);
                lblCalificacion.setManaged(true);
            }
        } else {
            lblEstado.setText("Estado: " + entrega.getEstado());
        }
        
        btnSubir.setVisible(false);
        btnSubir.setManaged(false);
        
        btnDescargar.setVisible(true);
        btnDescargar.setManaged(true);
        btnDescargar.setText("Descargar entrega");
    }

    @FXML
    private void handleDescargar(ActionEvent event) {
        if (datosArchivo == null) {
            UtilidadesVistas.mostrarAlerta(
                    "Información", 
                    "No hay archivo disponible para descargar.", 
                    Alert.AlertType.INFORMATION);
            return;
        }

        FileChooser selector = new FileChooser();
        String extension = esModoEntrega ? 
                           (entrega.getExtension() != 
                                null ? entrega.getExtension() : "") : 
                           (solicitud.getDocumento().getExtension() != 
                        null ? solicitud.getDocumento().getExtension() : "pdf");
        
        String nombreArchivo;
        if (esModoEntrega && entrega.getNombreArchivo() != null) {
            nombreArchivo = entrega.getNombreArchivo();
        } else {
            String nombreBase = esModoEntrega ? "Entrega_" : "Formato_";
            nombreArchivo = nombreBase + 
                solicitud.getDocumento().getNombreDocumento() + 
                (extension.isEmpty() ? "" : "." + extension);
        }
        
        selector.setTitle("Guardar Archivo");
        selector.setInitialFileName(nombreArchivo);
        if (!extension.isEmpty()) {
            selector.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "Archivo " + extension.toUpperCase(), "*." + extension));
        }
        
        File archivo = 
                selector.showSaveDialog(btnDescargar.getScene().getWindow());
        if (archivo != null) {
            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                fos.write(datosArchivo);
                UtilidadesVistas.mostrarAlerta(
                        "Descarga Exitosa", 
                        "El archivo se ha guardado correctamente.", 
                        Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                UtilidadesVistas.mostrarAlerta(
                        "Error", 
                        "No se pudo guardar el archivo.", 
                        Alert.AlertType.ERROR);
            }
        }
    }
    
    public void setOnActionSubir(
            javafx.event.EventHandler<ActionEvent> eventHandler) {
        btnSubir.setOnAction(eventHandler);
    }
}
