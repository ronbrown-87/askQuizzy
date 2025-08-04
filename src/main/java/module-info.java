module edu.cbu.quizproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires com.google.gson;
    requires org.testng;
    requires junit;
    requires java.logging;

    opens edu.cbu.quizproject.app to javafx.fxml;
    opens edu.cbu.quizproject.presentation.controller to javafx.fxml;
    exports edu.cbu.quizproject.app;
    exports edu.cbu.quizproject.presentation.controller;
//    opens edu.cbu.quizproject.test to junit;
    opens edu.cbu.quizproject.core.data.repository to com.google.gson;
    opens edu.cbu.quizproject.core.data.source to com.google.gson;
    opens edu.cbu.quizproject.core.model to com.google.gson;
}