package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * FXML Controller class
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Controlador para el menú principal del coordinador.
 */
public class FXMLMenuCoordinadorController implements Initializable {

    @FXML
    private Button btnNotificaciones;
    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnGestionPracticantes;
    @FXML
    private Button btnGestionProyectos;
    @FXML
    private Button btnVerIndicadores;
    @FXML
    private Button btnAsignarProyectos;
    @FXML
    private Button btnGestionOrganizaciones;
    @FXML
    private Label lblNombre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Sesion.getUsuario() != null) {
            lblNombre.setText(Sesion.getUsuario().getNombre());
        }
    }

    @FXML
    private void handleGestionOrganizaciones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGestionOrganizaciones.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnGestionOrganizaciones.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Gestión de Organizaciones");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de gestión de organizaciones: " + e.getMessage());
        }
    }

    @FXML
    private void handleGestionProyectos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGestionProyectos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnGestionProyectos.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Gestión de Proyectos");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de gestión de proyectos: " + e.getMessage());
        }
    }

    @FXML
    private void handleGestionPracticantes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGestionPracticantes.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnGestionPracticantes.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Gestión de Practicantes");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de gestión de practicantes: " + e.getMessage());
        }
    }

    @FXML
    private void handleAsignarProyectos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLAsignarProyecto.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnAsignarProyectos.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Asignar Proyectos");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de asignación de proyectos: " + e.getMessage());
        }
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
