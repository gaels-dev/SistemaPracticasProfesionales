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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.dao.ExperienciaEducativaDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.ExperienciaEducativa;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * FXML Controller class
 * Autor: Oscar Turrent Peña
 * Fecha creación: 17/06/2026
 * Descripción: Controlador para el menú de selección de Experiencias Educativas
 */
public class FXMLMenuProfesorController implements Initializable {

    @FXML
    private Label lblNombre;
    @FXML
    private FlowPane fpContenedorEE;

    private final ExperienciaEducativaDAO eeDAO = new ExperienciaEducativaDAO();
    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Sesion.getUsuario() != null) {
            lblNombre.setText(Sesion.getUsuario().getNombre());
            cargarExperienciasEducativas();
        }
    }

    private void cargarExperienciasEducativas() {
        if (Sesion.getPersonalAcademico() == null) return;
        
        try {
            List<ExperienciaEducativa> listaEE = eeDAO.obtenerExperienciasPorProfesor(
                    Sesion.getPersonalAcademico().getIdPersonalAcademico());
            
            fpContenedorEE.getChildren().clear();
            for (ExperienciaEducativa ee : listaEE) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/fxml/FXMLTarjetaExperienciaEducativa.fxml"));
                Parent tarjeta = loader.load();
                FXMLTarjetaExperienciaEducativaController controlador = 
                        loader.getController();
                if (controlador != null) {
                    controlador.setDatos(ee);
                } else {
                    System.err.println("Error: No se pudo obtener el "
                            + "controlador de la tarjeta para " 
                            + ee.getNombre());
                }
                fpContenedorEE.getChildren().add(tarjeta);
            }
        } catch (SQLException | IOException e) {
            UtilidadesVistas.mostrarAlerta(
                    "Error", 
                    "No se pudieron cargar las experiencias educativas.", 
                    Alert.AlertType.ERROR);
            System.err.println("Error: " + e.getMessage());
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
