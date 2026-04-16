module edu.utsa.cs3443.HomeHaven {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.utsa.cs3443.HomeHaven to javafx.fxml;
    opens edu.utsa.cs3443.HomeHaven.controller to javafx.fxml;
    exports edu.utsa.cs3443.HomeHaven;
    exports edu.utsa.cs3443.HomeHaven.model;
    opens edu.utsa.cs3443.HomeHaven.model to javafx.fxml;
}