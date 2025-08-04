package edu.cbu.quizproject.presentation.view;

import edu.cbu.quizproject.presentation.components.QuizHeaderComponent;
import edu.cbu.quizproject.presentation.components.QuizNavigationComponent;
import edu.cbu.quizproject.presentation.components.QuizProgressComponent;
import edu.cbu.quizproject.presentation.components.QuizQuestionComponent;
import edu.cbu.quizproject.presentation.controller.QuizController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuizScreen {
    private QuizController controller;
    private VBox mainContainer;
    private QuizHeaderComponent headerComponent;
    private QuizProgressComponent progressComponent;
    private QuizQuestionComponent questionComponent;
    private QuizNavigationComponent navigationComponent;
    private Stage stage;
    
    public QuizScreen(QuizController controller) {
        this.controller = controller;
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        headerComponent = new QuizHeaderComponent();
        progressComponent = new QuizProgressComponent();
        questionComponent = new QuizQuestionComponent();
        navigationComponent = new QuizNavigationComponent();
        
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        controller.setOnTimerUpdate(() -> {
            headerComponent.updateTimer(controller.getTimeRemaining());
        });
        
        controller.setOnQuizEnd(() -> {
            showScoreScreen();
        });
        
        navigationComponent.setOnPreviousClicked(() -> {
            controller.previousQuestion();
            refreshDisplay();
        });
        
        navigationComponent.setOnNextClicked(() -> {
            controller.nextQuestion();
            refreshDisplay();
        });
        
        navigationComponent.setOnQuestionNumberClicked((questionNum) -> {
            controller.goToQuestion(questionNum);
            refreshDisplay();
        });
        
        questionComponent.setOnAnswerSelected((answerIndex) -> {
            int currentQuestionIndex = controller.getCurrentQuestionIndex() - 1;
            int correctAnswer = controller.getCorrectAnswer(currentQuestionIndex);
            questionComponent.showAnswerFeedback(answerIndex, correctAnswer);
            
            new Thread(() -> {
                try {
                    Thread.sleep(1500); 
                    controller.answerQuestion(answerIndex);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        });
        
    }
    
    private void refreshDisplay() {
        if (controller.isQuizEnded()) {
            showScoreScreen();
            return;
        }
        
        updateProgress(controller.getCurrentQuestionIndex(), controller.getTotalQuestions(), controller.getAnsweredCount());
        
        var currentQuestion = controller.getCurrentQuestion();
        updateQuestion(currentQuestion, controller.getAnswer(controller.getCurrentQuestionIndex() - 1));
        
        updateNavigation(controller.getCurrentQuestionIndex(), controller.getTotalQuestions(), 
                        controller.hasPrevious(), controller.hasNext());
        
        questionComponent.updateQuestionNumber(controller.getCurrentQuestionIndex());
    }
    
    private void setupLayout() {
        mainContainer = new VBox();
        mainContainer.setSpacing(0);
        mainContainer.setPadding(new Insets(0));
        mainContainer.getStyleClass().add("parchment-bg");
        
        mainContainer.getChildren().addAll(
            headerComponent.getNode(),
            progressComponent.getNode(),
            createContentArea(),
            navigationComponent.getNode()
        );
    }
    
    private Region createContentArea() {
        VBox contentArea = new VBox();
        contentArea.setSpacing(30);
        contentArea.setPadding(new Insets(40, 50, 40, 50));
        contentArea.getStyleClass().add("question-container");
        contentArea.setAlignment(Pos.TOP_LEFT);
        
        contentArea.getChildren().add(questionComponent.getNode());
        
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        
        return contentArea;
    }
    
    public Scene createScene() {
        Scene scene = new Scene(mainContainer, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;
    }
    
    public void show(Stage stage) {
        this.stage = stage;
        stage.setScene(createScene());
        stage.setTitle("✝ Bible Quiz - Test Your Knowledge ✝");
        stage.show();
        
        refreshDisplay();
    }
    
    public void updateProgress(int current, int total, int answered) {
        progressComponent.updateProgress(current, total, answered);
    }
    
    public void updateQuestion(edu.cbu.quizproject.core.model.Question question, int selectedOption) {
        questionComponent.updateQuestion(question, selectedOption);
    }
    
    public void updateNavigation(int currentQuestion, int totalQuestions, boolean hasPrevious, boolean hasNext) {
        navigationComponent.updateNavigation(currentQuestion, totalQuestions, hasPrevious, hasNext);
    }
    
    private void showScoreScreen() {
        ScoreScreen scoreScreen = new ScoreScreen();
        scoreScreen.show(stage, controller.getScore(), controller.getTotalQuestions());
    }
}