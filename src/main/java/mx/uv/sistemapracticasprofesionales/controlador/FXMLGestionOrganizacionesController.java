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
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.dao.OrganizacionVinculadaDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.OrganizacionVinculada;

/**
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 17/06/2026
 * Descripción: Controlador para la gestión de organizaciones vinculadas.
 */
public class FXMLGestionOrganizacionesController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Button btnNuevaOrganizacion;
    @FXML
    private VBox vboxOrganizaciones;

    private OrganizacionVinculadaDAO organizacionDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        organizacionDAO = new OrganizacionVinculadaDAO();
        cargarOrganizaciones();
    }

    private void cargarOrganizaciones() {
        vboxOrganizaciones.getChildren().clear();
        try {
            List<OrganizacionVinculada> organizaciones = organizacionDAO.obtenerOrganizacionesActivas();
            for (OrganizacionVinculada org : organizaciones) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLTarjetaOrganizacion.fxml"));
                VBox tarjeta = loader.load();
                FXMLTarjetaOrganizacionController controller = loader.getController();
                controller.setOrganizacion(org);
                vboxOrganizaciones.getChildren().add(tarjeta);
            }
        } catch (SQLException | IOException e) {
            mostrarAlerta("Error", "No se pudieron cargar las organizaciones: " + e.getMessage(), Alert.AlertType.ERROR);
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
            mostrarAlerta("Error", "Error al volver al menú principal.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleNuevaOrganizacion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLRegistrarOrganizacion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Registrar Nueva Organización");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.showAndWait();
            cargarOrganizaciones();
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al cargar el formulario de registro: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
