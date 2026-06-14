package mx.uv.sistemapracticasprofesionales;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
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