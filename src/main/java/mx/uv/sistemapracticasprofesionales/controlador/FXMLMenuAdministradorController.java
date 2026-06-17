package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * FXML Controller class
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Controlador para el menú principal del administrador.
 */
public class FXMLMenuAdministradorController implements Initializable {

    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnGestionCoordinadores;
    @FXML
    private Button btnGestionProfesores;
    @FXML
    private Label lblNombre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Sesion.getUsuario() != null) {
            lblNombre.setText(Sesion.getUsuario().getNombre());
        }
    }    

    @FXML
    private void handleGestionCoordinadores(ActionEvent event) {
        Stage escenario = (Stage) lblNombre.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLGestionPersonal.fxml", 
                "Gestion de Coordinadores");
    }

    @FXML
    private void handleGestionProfesores(ActionEvent event) {
        Stage escenario = (Stage) lblNombre.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLGestionPersonal.fxml", 
                "Gestion de Profesores");
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
