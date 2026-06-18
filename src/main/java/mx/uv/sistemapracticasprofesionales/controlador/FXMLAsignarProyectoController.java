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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.AsignacionProyecto;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Proyecto;
import mx.uv.sistemapracticasprofesionales.servicio.AsignacionProyectoService;
import mx.uv.sistemapracticasprofesionales.servicio.PracticanteService;
import mx.uv.sistemapracticasprofesionales.servicio.ProyectoService;

/**
 * FXML Controller class
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Controlador para la vista de asignación de proyecto
 */
public class FXMLAsignarProyectoController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private VBox vboxPracticantes;
    @FXML
    private VBox vboxProyectos;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnRegistrar;

    private final PracticanteService practicanteService = new PracticanteService();
    private final ProyectoService proyectoService = new ProyectoService();
    private final AsignacionProyectoService asignacionService = new AsignacionProyectoService();
    
    private Practicante practicanteSeleccionado = null;
    private Proyecto proyectoSeleccionado = null;
    
    private javafx.scene.layout.HBox tarjetaPracticanteSeleccionada = null;
    private VBox tarjetaProyectoSeleccionada = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnRegistrar.setDisable(true);
        cargarPracticantesSinProyecto();
        cargarProyectosConCupo();
    }    

    private void cargarPracticantesSinProyecto() {
        vboxPracticantes.getChildren().clear();
        try {
            List<Practicante> practicantes = practicanteService.obtenerPracticantesDisponiblesParaProyecto(); 
            
            for (Practicante practicante : practicantes) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLTarjetaPracticante.fxml"));
                javafx.scene.layout.HBox root = loader.load();
                
                FXMLTarjetaPracticanteController controlador = loader.getController();
                controlador.inicializarPracticante(practicante);
                controlador.ocultarBotonesAccion();
                
                root.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 5; -fx-padding: 10; -fx-cursor: hand;");
                
                root.setOnMouseClicked(event -> {
                    if (tarjetaPracticanteSeleccionada != null) {
                        tarjetaPracticanteSeleccionada.setStyle("-fx-background-color: #F8F8F8; -fx-background-radius: 5; -fx-padding: 10; -fx-cursor: hand;");
                    }
                    practicanteSeleccionado = practicante;
                    tarjetaPracticanteSeleccionada = root;
                    root.setStyle("-fx-background-color: #D6E0F0; -fx-background-radius: 5; -fx-padding: 10; -fx-cursor: hand; -fx-border-color: #4A90E2; -fx-border-radius: 5;");
                    validarSeleccion();
                });
                
                vboxPracticantes.getChildren().add(root);
            }
        } catch (SQLException | IOException e) {
            mostrarAlerta("Error", "No se pudieron cargar los practicantes.", Alert.AlertType.ERROR);
        }
    }

    private void cargarProyectosConCupo() {
        vboxProyectos.getChildren().clear();
        try {
            List<Proyecto> proyectos = proyectoService.obtenerProyectosActivosConCupo();
            
            for (Proyecto proyecto : proyectos) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLTarjetaProyecto.fxml"));
                VBox tarjeta = loader.load();
                
                FXMLTarjetaProyectoController controlador = loader.getController();
                controlador.inicializarProyecto(proyecto);
                controlador.ocultarBotonesAccion();
                
                tarjeta.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #CCCCCC; -fx-border-radius: 10; -fx-cursor: hand;");
                
                tarjeta.setOnMouseClicked(event -> {
                    if (tarjetaProyectoSeleccionada != null) {
                        tarjetaProyectoSeleccionada.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #CCCCCC; -fx-border-radius: 10; -fx-cursor: hand;");
                    }
                    proyectoSeleccionado = proyecto;
                    tarjetaProyectoSeleccionada = tarjeta;
                    tarjeta.setStyle("-fx-background-color: #D6E0F0; -fx-background-radius: 10; -fx-border-color: #4A90E2; -fx-border-radius: 10; -fx-cursor: hand;");
                    validarSeleccion();
                });
                
                vboxProyectos.getChildren().add(tarjeta);
            }
        } catch (SQLException | IOException e) {
            mostrarAlerta("Error", "No se pudieron cargar los proyectos.", Alert.AlertType.ERROR);
        }
    }

    private void validarSeleccion() {
        btnRegistrar.setDisable(practicanteSeleccionado == null || proyectoSeleccionado == null);
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Asignación");
        alert.setHeaderText("Asignar proyecto a practicante");
        alert.setContentText("¿Desea asignar el proyecto '" + proyectoSeleccionado.getNombre() + 
                             "' al practicante '" + practicanteSeleccionado.getNombres() + " " + practicanteSeleccionado.getApellidoPaterno() + "'?");

        ButtonType btnConfirmar = new ButtonType("Confirmar asignación");
        ButtonType btnCancelarDiag = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(btnConfirmar, btnCancelarDiag);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == btnConfirmar) {
            registrarAsignacion();
        }
    }
    
    private void registrarAsignacion() {
        AsignacionProyecto asignacion = new AsignacionProyecto();
        asignacion.setPracticante(practicanteSeleccionado);
        asignacion.setProyecto(proyectoSeleccionado);
        
        try {
            if (asignacionService.registrarAsignacion(asignacion)) {
                mostrarAlerta("Éxito", "Asignación registrada correctamente.", Alert.AlertType.INFORMATION);
                regresarAMenu();
            } else {
                mostrarAlerta("Error", "No se pudo registrar la asignación.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error de conexión: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAMenu();
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        regresarAMenu();
    }

    private void regresarAMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLMenuCoordinador.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menú Coordinador");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al volver al menú principal: " + e.getMessage());
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
