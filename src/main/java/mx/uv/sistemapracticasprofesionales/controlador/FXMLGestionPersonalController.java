package mx.uv.sistemapracticasprofesionales.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.PersonalAcademico;
import mx.uv.sistemapracticasprofesionales.servicio.PersonalAcademicoService;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  16/06/2026
 * Descripción:     Controlador para la vista Gestión de Personal 
 *                  (Coordinadores/Profesores)
 */
public class FXMLGestionPersonalController implements Initializable {

    @FXML
    private Button btnVolver;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblSubtitulo;
    @FXML
    private Label lblListaTitulo;
    @FXML
    private VBox vboxPersonal;
    @FXML
    private Button btnAgregar;

    private final PersonalAcademicoService personalService = 
            new PersonalAcademicoService();
    private String rolActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Stage escenario = (Stage) lblTitulo.getScene().getWindow();
            String titulo = escenario.getTitle();
            
            if (titulo.contains("Coordinadores")) {
                rolActual = "Coordinador";
                configurarVista("Gestión de Coordinadores", 
                        "Administre los registros de coordinadores", 
                        "Lista de Coordinadores", 
                        "Agregar nuevo coordinador");
            } else {
                rolActual = "Profesor";
                configurarVista("Gestión de Profesores", 
                        "Administre los registros de profesores", 
                        "Lista de Profesores",
                        "Agregar nuevo profesor");
            }
            cargarPersonal();
        });
    }

    private void configurarVista(String titulo, String subtitulo, 
            String listaTitulo, String textoBotonAgregar) {
        lblTitulo.setText(titulo);
        lblSubtitulo.setText(subtitulo);
        lblListaTitulo.setText(listaTitulo);
        btnAgregar.setText(textoBotonAgregar);
    }

    private void cargarPersonal() {
        vboxPersonal.getChildren().clear();
        try {
            List<PersonalAcademico> listaPersonal = 
                    personalService.obtenerPersonalPorRol(rolActual);
            
            for (PersonalAcademico personal : listaPersonal) {
                FXMLLoader cargador = new FXMLLoader(getClass().getResource(
                        "/fxml/FXMLTarjetaPersonal.fxml"));
                Parent tarjeta = cargador.load();
                
                FXMLTarjetaPersonalController 
                        controladorTarjeta = cargador.getController();
                controladorTarjeta.inicializarPersonal(personal);
                
                vboxPersonal.getChildren().add(tarjeta);
            }
        } catch (SQLException | IOException e) {
            System.err.println("Error al cargar el personal: " 
                    + e.getMessage());
            UtilidadesVistas.mostrarAlerta("Error", 
                    "No se pudo cargar la información del personal.", 
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        Stage escenario = (Stage) btnVolver.getScene().getWindow();
        UtilidadesVistas.cargarVista(escenario, 
                "/fxml/FXMLMenuAdministrador.fxml", 
                "Menu Administrador");
    }

    @FXML
    private void handleAgregar(ActionEvent event) {
        Stage escenario = (Stage) lblTitulo.getScene().getWindow();
        UtilidadesVistas.cargarVistaModal(escenario, 
                "/fxml/FXMLGestionarPersonal.fxml",
                "Agregar nuevo " + rolActual);
        cargarPersonal();
    }
}
