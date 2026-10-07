package com.example.registroempleados;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroEmpleadosApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(RegistroEmpleadosApplication.class.getResource(
                "/com/example/registroempleados/views/empleado-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Registro de empleados");
        stage.setMinWidth(1050);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
