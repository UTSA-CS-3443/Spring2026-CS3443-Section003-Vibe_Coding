module edu.utsa.cs3443.homeheaven {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.utsa.cs3443.homeheaven to javafx.fxml;
    opens edu.utsa.cs3443.homeheaven.model to javafx.base;

    exports edu.utsa.cs3443.homeheaven;
}
