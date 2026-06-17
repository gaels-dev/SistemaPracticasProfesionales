/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.sistemapracticasprofesionales.utilidades;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Autor:           Oscar Turrent Peña
 * Fecha creación:  16/06/2026
 * Descripción:     Clase de utilidades para las funciones de las vistas,
 *                  incluye cargar vistas.
 */
public class UtilidadesVistas {
    
    public static void cargarVista(Stage escenario, String rutaFXML, 
            String nombreVentana) {
        try {
            FXMLLoader cargador = new FXMLLoader(
                    UtilidadesVistas.class.getResource(rutaFXML));
            Parent raiz = cargador.load();
            Scene escena = new Scene(raiz);
            escenario.setScene(escena);
            escenario.setTitle(nombreVentana);
            escenario.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista " + nombreVentana 
                    + ": " + e.getMessage());
        }
    }
    
    public static void cargarVistaModal(Stage escenarioPadre, String rutaFXML, 
            String nombreVentana) {
        try {
            FXMLLoader cargador = new FXMLLoader(
            UtilidadesVistas.class.getResource(rutaFXML)
        );
        Parent raiz = cargador.load();
        Scene escena = new Scene(raiz);
        Stage ventanaModal = new Stage();
        ventanaModal.setScene(escena);
        ventanaModal.setTitle(nombreVentana);
        ventanaModal.initModality(Modality.APPLICATION_MODAL);
        ventanaModal.initOwner(escenarioPadre);
        ventanaModal.showAndWait();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista " + nombreVentana 
                    + ": " + e.getMessage());
        }
    }
    
    public static void mostrarAlerta(String titulo, String mensaje, 
            Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
