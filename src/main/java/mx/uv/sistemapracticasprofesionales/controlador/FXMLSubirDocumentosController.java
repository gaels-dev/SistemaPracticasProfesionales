package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.EntregaDocumento;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.SolicitudDocumento;
import mx.uv.sistemapracticasprofesionales.servicio.EntregaDocumentoService;
import mx.uv.sistemapracticasprofesionales.servicio.PracticanteService;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Controlador para la gestión de entrega de documentos 
 *                  o actividades.
 */
public class FXMLSubirDocumentosController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private VBox vboxDocumentos;
    @FXML
    private VBox vboxReportes;
    @FXML
    private TextField txtRutaArchivo;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnSubirReporte;
    @FXML
    private Label lblMiEntrega;
    @FXML
    private Button btnCancelarEntrega;
    @FXML
    private VBox vboxDropArea;

    private SolicitudDocumento solicitud;
    private File archivoSeleccionado;
    private final EntregaDocumentoService entregaService = 
            new EntregaDocumentoService();
    private final PracticanteService practicanteService = 
            new PracticanteService();
    private EntregaDocumento entregaExistente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    public void inicializarContexto(SolicitudDocumento solicitud) {
        this.solicitud = solicitud;
        if (lblMiEntrega != null) {
            lblMiEntrega.setText("Mi Entrega: " 
                    + solicitud.getDocumento().getNombreDocumento());
        }
        cargarEntregaExistente();
    }

    private void cargarEntregaExistente() {
        try {
            Practicante practicante = 
                    practicanteService.buscarPorIdUsuario(
                            Sesion.getUsuario().getIdUsuario());
            entregaExistente = 
                    entregaService.buscarEntrega(
                            practicante.getIdPracticante(), 
                            solicitud.getSolicitudDocumento());
            
            if (entregaExistente != null) {
                btnSubirReporte.setDisable(true);
                btnCancelarEntrega.setVisible(true);
                btnCancelarEntrega.setManaged(true);
                txtRutaArchivo.setText("Archivo ya entregado (" 
                        + entregaExistente.getEstado() + ")");
                vboxDropArea.setDisable(true);
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/fxml/FXMLTarjetaSubirDocumento.fxml"));
                Parent tarjeta = loader.load();
                FXMLTarjetaSubirDocumentoController controladorTarjeta = 
                        loader.getController();
                byte[] archivoSubido = entregaService.obtenerArchivo(
                        entregaExistente.getIdEntregaDocumento());
                controladorTarjeta.inicializarTarjetaEntrega(
                        solicitud, entregaExistente, archivoSubido);
                
                vboxReportes.getChildren().add(tarjeta);
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al buscar entrega: " + e.getMessage());
        }
    }

    @FXML
    private void handleExaminar(MouseEvent event) {
        if (entregaExistente != null) return;
        
        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccionar archivo");
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Documentos", "*.pdf", "*.doc", "*.docx"));

        File archivo = selector.showOpenDialog(
                btnSubirReporte.getScene().getWindow());

        if (archivo != null) {
            if (archivo.length() > 10 * 1024 * 1024) {
                UtilidadesVistas.mostrarAlerta(
                        "Error", 
                        "El archivo excede los 10MB.", 
                        Alert.AlertType.ERROR);
                return;
            }
            archivoSeleccionado = archivo;
            txtRutaArchivo.setText(archivo.getAbsolutePath());
        }
    }
    
    @FXML
    private void handleCancelarEntrega(ActionEvent event) {
        try {
            Practicante practicante = practicanteService.buscarPorIdUsuario(
                    Sesion.getUsuario().getIdUsuario());
            if (entregaService.cancelarEntrega(practicante.getIdPracticante(), 
                    solicitud.getSolicitudDocumento())) {
                UtilidadesVistas.mostrarAlerta(
                        "Éxito", 
                        "Entrega cancelada correctamente.", 
                        Alert.AlertType.INFORMATION);
                archivoSeleccionado = null;
                txtRutaArchivo.clear();
                btnSubirReporte.setDisable(false);
                btnCancelarEntrega.setVisible(false);
                btnCancelarEntrega.setManaged(false);
                vboxDropArea.setDisable(false);
                entregaExistente = null;
                vboxReportes.getChildren().clear();
            }
        } catch (SQLException e) {
            UtilidadesVistas.mostrarAlerta(
                    "Error", 
                    "No se pudo cancelar la entrega.", 
                    Alert.AlertType.ERROR);
        }
    }

    private String obtenerExtension(File archivo) {
        String nombre = archivo.getName();
        int i = nombre.lastIndexOf('.');
        return (i > 0) ? nombre.substring(i + 1) : "";
    }

    @FXML
    private void handleSubirReporte(ActionEvent event) {
        if (archivoSeleccionado == null) {
            UtilidadesVistas.mostrarAlerta(
                    "Advertencia", 
                    "Debe seleccionar un archivo primero.", 
                    Alert.AlertType.WARNING);
            return;
        }

        try {
            Practicante practicante = practicanteService.buscarPorIdUsuario(
                    Sesion.getUsuario().getIdUsuario());

            EntregaDocumento entrega = new EntregaDocumento();
            entrega.setPracticante(practicante);
            entrega.setSolicitudDocumento(solicitud);
            entrega.setFechaEntrega(new Date());
            entrega.setExtension(obtenerExtension(archivoSeleccionado));
            entrega.setNombreArchivo(archivoSeleccionado.getName());

            try (FileInputStream FlujoEntradaArchivo = new FileInputStream(
                    archivoSeleccionado)) {
                byte[] archivoBytes = new byte[
                        (int) archivoSeleccionado.length()];
                FlujoEntradaArchivo.read(archivoBytes);
                entrega.setArchivoEntregado(archivoBytes);
            }

            if (entregaService.registrarEntrega(entrega)) {
                UtilidadesVistas.mostrarAlerta(
                        "Éxito", 
                        "Documento subido correctamente.", 
                        Alert.AlertType.INFORMATION);
                handleVolver(event);
            } else {
                UtilidadesVistas.mostrarAlerta(
                        "Error", 
                        "No se pudo subir el documento.", 
                        Alert.AlertType.ERROR);
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error en subida: " + e.getMessage());
            UtilidadesVistas.mostrarAlerta(
                    "Error", 
                    "Ocurrió un error inesperado al subir el archivo.", 
                    Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void handleVolver(ActionEvent event) {
        String tituloRegreso = "Subir Actividades";
        if (solicitud != null && 
            solicitud.getDocumento().getTipoDocumento().equalsIgnoreCase(
                    "Administrativo")) {
            tituloRegreso = "Subir Documentos";
        }

        Stage escenario = (Stage) btnVolver.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLSeleccionarDocumento.fxml", 
                tituloRegreso);
    }
    
    @FXML
    private void handleCancelar(ActionEvent event) {
        archivoSeleccionado = null;
        txtRutaArchivo.clear();
        handleVolver(event);
    }
}

