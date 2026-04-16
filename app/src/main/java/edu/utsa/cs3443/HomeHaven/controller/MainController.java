package edu.utsa.cs3443.HomeHaven.controller;

import edu.utsa.cs3443.HomeHaven.model.DataStore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private AnchorPane contentArea;

    // Nav buttons — fx:id must match these names in main.fxml
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnAssets;
    @FXML
    private Button btnReminders;

    // Dark mode toggle state
    private boolean darkMode = false;

    @FXML
    public void initialize() {
        DataStore.loadSampleData(); // load data once here instead of Main.java
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

        // Apply to the root scene so ALL screens get it
        contentArea.getScene().getStylesheets().clear();
        contentArea.getScene().getStylesheets().add(stylesheet);
    }

    // ── Private helpers ──────────────────────────────────────────────

    private void loadScreen(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/edu/utsa/cs3443/HomeHaven/" + name + ".fxml"
                    )
            );
            Parent screen = loader.load();

            // Stretch the loaded screen to fill contentArea
            AnchorPane.setTopAnchor(screen, 0.0);
            AnchorPane.setBottomAnchor(screen, 0.0);
            AnchorPane.setLeftAnchor(screen, 0.0);
            AnchorPane.setRightAnchor(screen, 0.0);

            contentArea.getChildren().setAll(screen);

        } catch (Exception e) {
            e.printStackTrace(); // check Run console if a screen won't load
        }
    }

    // Highlights the active nav button so the user knows where they are
    private void setActiveButton(Button active) {
        for (Button btn : new Button[]{btnDashboard, btnAssets, btnReminders}) {
            btn.getStyleClass().remove("nav-active");
        }
        active.getStyleClass().add("nav-active");
    }
}