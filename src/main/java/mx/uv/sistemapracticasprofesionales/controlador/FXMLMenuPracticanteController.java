package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author oscar
 */
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;

/**
 * FXML Controller class
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 14/06/2026
 * Descripción: Controlador para el menú principal del practicante.
 */
public class FXMLMenuPracticanteController implements Initializable {

    @FXML
    private Button btnNotificaciones;
    @FXML
    private Button btnPerfil;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnSubirDocumentos;
    @FXML
    private Button btnConsultarProyecto;
    @FXML
    private Button btnReportes;
    @FXML
    private Label lblNombre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Sesion.getUsuario() != null) {
            lblNombre.setText(Sesion.getUsuario().getNombre());
        }
    }    
    
}
