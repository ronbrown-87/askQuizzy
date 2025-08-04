package edu.cbu.quizproject.app;

import edu.cbu.quizproject.core.service.QuizService;
import edu.cbu.quizproject.presentation.controller.QuizController;
import javafx.application.Application;
import javafx.stage.Stage;

import edu.cbu.quizproject.presentation.view.QuizScreen;
import edu.cbu.quizproject.presentation.view.WelcomeScreen;

public class QuizApp extends Application {
    private QuizController quizController;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        this.quizController = new QuizController();

        primaryStage.setOnCloseRequest(event -> {
            if (!quizController.isQuizEnded()) {
                quizController.persistQuizState();
            }
        });

        WelcomeScreen welcomeScreen = new WelcomeScreen(quizController.hasRestorableState());
        welcomeScreen.setOnPlayClicked(() -> {
            new QuizService().deleteQuizState();
            this.quizController = new QuizController();
            QuizScreen quizScreen = new QuizScreen(this.quizController);
            quizScreen.show(primaryStage);
        });
        welcomeScreen.setOnResumeClicked(() -> {
            QuizScreen quizScreen = new QuizScreen(this.quizController); // use restored state
            quizScreen.show(primaryStage);
        });
        welcomeScreen.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
