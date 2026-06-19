package mx.uv.sistemapracticasprofesionales;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 * Autor: Gael Samei Amores Rivas
 * Fecha creación: 12/06/2026
 * Descripción: Clase principal desde donde se inicia toda la aplicación
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent raiz = FXMLLoader.load(getClass().getResource("/fxml/FXMLInicioSesion.fxml"));

            Scene escena = new Scene(raiz);
            primaryStage.setScene(escena);
            primaryStage.setTitle("Iniciar sesión - Sistema Practicas Profesionales");
            primaryStage.setResizable(true);
            primaryStage.sizeToScene();
            primaryStage.show();
            primaryStage.centerOnScreen();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}