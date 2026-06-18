package mx.uv.sistemapracticasprofesionales.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.sistemapracticasprofesionales.modelo.dao.EntregaDocumentoDAO;
import mx.uv.sistemapracticasprofesionales.modelo.pojo.EntregaDocumento;
import mx.uv.sistemapracticasprofesionales.utilidades.Sesion;
import mx.uv.sistemapracticasprofesionales.utilidades.UtilidadesVistas;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  17/06/2026
 * Descripción:     Controlador para el modal de registro de calificación y 
 *                  retroalimentación de una entrega de practicante.
 */
public class FXMLEvaluarEntregaPracticanteController implements Initializable {

    @FXML
    private Label lblNombrePracticante;
    @FXML
    private TextField txfCalificacion;
    @FXML
    private TextArea txaRetroalimentacion;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnRegistrar;

    private EntregaDocumento entrega;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setDatos(EntregaDocumento entrega) {
        this.entrega = entrega;
        lblNombrePracticante.setText(entrega.getPracticante().getNombres() 
                + " " + entrega.getPracticante().getApellidoPaterno());
        
        if (entrega.getCalificacion() != null) {
            txfCalificacion.setText(String.valueOf(entrega.getCalificacion()));
        }
        if (entrega.getRetroalimentacion() != null) {
            txaRetroalimentacion.setText(entrega.getRetroalimentacion());
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        Stage escenario = (Stage) btnCancelar.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void handleRegistrar(ActionEvent event) {
        if (validarCampos()) {
            registrarEvaluacion();
        }
    }

    private boolean validarCampos() {
        String calificacionStr = txfCalificacion.getText().trim();
        String retroalimentacion = txaRetroalimentacion.getText().trim();

        if (calificacionStr.isEmpty() || retroalimentacion.isEmpty()) {
            UtilidadesVistas.mostrarAlerta(
                    "Campos vacíos", 
                    "Por favor, ingrese la calificación y las observaciones.", 
                    Alert.AlertType.WARNING);
            return false;
        }

        try {
            double calificacion = Double.parseDouble(calificacionStr);
            if (calificacion < 0 || calificacion > 10) {
                UtilidadesVistas.mostrarAlerta(
                        "Calificación inválida", 
                        "La calificación debe estar entre 0 y 10.", 
                        Alert.AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            UtilidadesVistas.mostrarAlerta(
                    "Formato inválido", 
                    "La calificación debe ser un número válido.", 
                    Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private void registrarEvaluacion() {
        entrega.setCalificacion(
                Double.parseDouble(txfCalificacion.getText().trim()));
        entrega.setRetroalimentacion(
                txaRetroalimentacion.getText().trim().replaceAll(
                        "\\s{2,}", " "));
        entrega.setProfesorEvaluador(Sesion.getPersonalAcademico());

        EntregaDocumentoDAO dao = new EntregaDocumentoDAO();
        try {
            if (dao.evaluarEntrega(entrega)) {
                UtilidadesVistas.mostrarAlerta(
                        "Evaluación registrada", 
                        "La calificación se ha registrado correctamente.", 
                        Alert.AlertType.INFORMATION);
                Stage escenario = (Stage) btnRegistrar.getScene().getWindow();
                escenario.close();
            } else {
                UtilidadesVistas.mostrarAlerta(
                        "Error", 
                        "No se pudo registrar la evaluación.", 
                        Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            UtilidadesVistas.mostrarAlerta(
                    "Error de BD", 
                    "Ocurrió un error al conectar con la base de datos: " 
                            + e.getMessage(), 
                    Alert.AlertType.ERROR);
        }
    }
}
