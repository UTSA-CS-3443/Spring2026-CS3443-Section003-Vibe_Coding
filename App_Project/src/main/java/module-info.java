module edu.utsa.cs3443.app_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.utsa.cs3443.app_project to javafx.fxml;
    exports edu.utsa.cs3443.app_project;
    exports edu.utsa.cs3443.app_project.model;
    opens edu.utsa.cs3443.app_project.model to javafx.fxml;
}