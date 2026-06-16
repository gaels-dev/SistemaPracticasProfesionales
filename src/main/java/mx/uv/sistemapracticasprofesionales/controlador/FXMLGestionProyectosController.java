package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Proyecto;
import mx.uv.sistemapracticasprofesionales.servicio.ProyectoService;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 2026-06-15
 * Descripción: Controlador para la vista de gestión de proyectos.
 */
public class FXMLGestionProyectosController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Button btnNuevoProyecto;
    @FXML
    private VBox vboxProyectos;

    private final ProyectoService proyectoService = new ProyectoService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarProyectos();
    }

    private void cargarProyectos() {
        vboxProyectos.getChildren().clear();
        try {
            List<Proyecto> proyectos = proyectoService.obtenerTodosProyectosActivos();
            
            for (Proyecto proyecto : proyectos) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLTarjetaProyecto.fxml"));
                Parent tarjeta = loader.load();
                
                FXMLTarjetaProyectoController controladorTarjeta = loader.getController();
                controladorTarjeta.inicializarProyecto(proyecto);
                
                vboxProyectos.getChildren().add(tarjeta);
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al cargar los proyectos: " + e.getMessage());
            mostrarAlerta("Error", "No se pudieron cargar los proyectos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleVolver(ActionEvent event) {
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

    @FXML
    private void handleNuevoProyecto(ActionEvent event) {
        try {
            if (proyectoService.obtenerPeriodoActual() == null) {
                mostrarAlerta("Advertencia", "No existe un periodo abierto en el sistema. No podrá registrar proyectos.", Alert.AlertType.WARNING);
                return;
            }
            if (proyectoService.obtenerOrganizacionesActivas().isEmpty()) {
                mostrarAlerta("Información", "No hay organizaciones vinculadas registradas. Debe registrar una primero.", Alert.AlertType.WARNING);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLRegistrarProyecto.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Registrar Nuevo Proyecto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            cargarProyectos();
        } catch (IOException e) {
            System.err.println("Error al abrir la vista de registro de proyecto: " + e.getMessage());
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo validar la información del sistema, inténtelo más tarde.", Alert.AlertType.ERROR);
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
