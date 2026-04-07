package edu.utsa.cs3443.app_project;

import edu.utsa.cs3443.app_project.model.DataStore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DataStore.loadSampleData();  // load data before anything shows

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/edu/utsa/cs3443/app_project/main.fxml")
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
