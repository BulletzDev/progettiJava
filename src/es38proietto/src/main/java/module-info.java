module it.proietto {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;

    opens it.proietto to javafx.fxml;
    exports it.proietto;
}
