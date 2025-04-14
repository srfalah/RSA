module com.example.rsa {
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.controlsfx.controls;

    opens com.example.rsa to javafx.fxml;
    exports com.example.rsa;
}