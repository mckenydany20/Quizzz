module com.example.quizz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.quizz to javafx.fxml;
    exports com.example.quizz;
}