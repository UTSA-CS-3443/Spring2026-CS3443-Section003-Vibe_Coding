package edu.utsa.cs3443.HomeHaven.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnAssets;
    @FXML
    private Button btnReminders;

    private boolean darkMode = false;

    @FXML
    public void initialize() {
        loadScreen("dashboard");
        setActiveButton(btnDashboard);
    }

    @FXML
    public void showDashboard() {
        loadScreen("dashboard");
        setActiveButton(btnDashboard);
    }

    @FXML
    public void showAssets() {
        loadScreen("assets");
        setActiveButton(btnAssets);
    }

    @FXML
    public void showReminders() {
        loadScreen("reminders");
        setActiveButton(btnReminders);
    }

    @FXML
    public void toggleDarkMode() {
        darkMode = !darkMode;
        String stylesheet = getClass().getResource(
                "/edu/utsa/cs3443/HomeHaven/" +
                        (darkMode ? "dark.css" : "light.css")
        ).toExternalForm();

        contentArea.getScene().getStylesheets().clear();
        contentArea.getScene().getStylesheets().add(stylesheet);
    }

    private void loadScreen(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/edu/utsa/cs3443/HomeHaven/" + name + ".fxml"
                    )
            );

            Parent screen = loader.load();

            // 🔥 AUTO REFRESH DASHBOARD
            Object controller = loader.getController();
            if (controller instanceof DashboardController) {
                ((DashboardController) controller).refresh();
            }

            AnchorPane.setTopAnchor(screen, 0.0);
            AnchorPane.setBottomAnchor(screen, 0.0);
            AnchorPane.setLeftAnchor(screen, 0.0);
            AnchorPane.setRightAnchor(screen, 0.0);

            contentArea.getChildren().setAll(screen);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setActiveButton(Button active) {
        for (Button btn : new Button[]{btnDashboard, btnAssets, btnReminders}) {
            btn.getStyleClass().remove("nav-active");
        }
        active.getStyleClass().add("nav-active");
    }
}