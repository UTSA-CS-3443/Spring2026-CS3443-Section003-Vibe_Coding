package edu.utsa.cs3443.HomeHaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/utsa/cs3443/HomeHaven/Main.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("HomeHaven");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
