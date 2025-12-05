module org.example.testjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.base;
    requires java.desktop;

    opens org.example.testjavafx to javafx.fxml;
    exports org.example.testjavafx;
}