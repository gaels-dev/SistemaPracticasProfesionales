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
import mx.uv.sistemapracticasprofesionales.modelo.pojo.Practicante;
import mx.uv.sistemapracticasprofesionales.servicio.PracticanteService;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class FXMLGestionPracticantesController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private VBox vboxPracticantes;
    @FXML
    private Button btnRegistrarPracticante;
    @FXML
    private Button btnValidarDocumentos;

    private final PracticanteService practicanteService = new PracticanteService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarPracticantes();
    }    

    private void cargarPracticantes() {
        vboxPracticantes.getChildren().clear();
        try {
            List<Practicante> practicantes = practicanteService.obtenerPracticantesInscritosPeriodoActual();
            
            for (Practicante practicante : practicantes) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLTarjetaPracticante.fxml"));
                Parent tarjeta = loader.load();
                
                FXMLTarjetaPracticanteController controladorTarjeta = loader.getController();
                controladorTarjeta.inicializarPracticante(practicante);
                
                vboxPracticantes.getChildren().add(tarjeta);
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al cargar los practicantes: " + e.getMessage());
            mostrarAlerta("Error", "No se pudieron cargar los practicantes.", Alert.AlertType.ERROR);
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
    private void handleRegistrarPracticante(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLRegistrarPracticante.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnRegistrarPracticante.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Registrar Practicante");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al abrir la vista de registro de practicante: " + e.getMessage());
            mostrarAlerta("Error", "No se pudo abrir la ventana de registro.", Alert.AlertType.ERROR);
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
