package edu.cbu.quizproject.presentation.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class WelcomeScreen {
    private VBox mainContainer;
    private Stage stage;
    private Runnable onPlayClicked;
    private Runnable onResumeClicked;
    private Button resumeButton;
    private boolean hasRestorableState;

    public WelcomeScreen(boolean hasRestorableState) {
        this.hasRestorableState = hasRestorableState;
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        mainContainer = new VBox();
        mainContainer.setSpacing(40);
        mainContainer.setPadding(new Insets(60));
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getStyleClass().add("parchment-bg");
    }

    private void setupLayout() {
        Label titleLabel = new Label("✝ Bible Quiz ✝");
        titleLabel.getStyleClass().add("bible-title");
        titleLabel.setTextAlignment(TextAlignment.CENTER);

        Label subtitleLabel = new Label("Test Your Knowledge of the Holy Scriptures");
        subtitleLabel.getStyleClass().add("bible-subtitle");
        subtitleLabel.setTextAlignment(TextAlignment.CENTER);

        Label instructionsLabel = new Label(
            "✝ 15 random Bible questions\n" +
            "⏰ 30 seconds per question\n" +
            "📖 Answer all questions to complete the quiz\n" +
            "✨ Get immediate feedback on your answers"
        );
        instructionsLabel.setFont(Font.font("Crimson Text", FontWeight.NORMAL, 16));
        instructionsLabel.getStyleClass().add("decorative-border");
        instructionsLabel.setPadding(new Insets(25));
        instructionsLabel.setTextAlignment(TextAlignment.CENTER);

        Button playButton = new Button("🎯 Start Quiz");
        playButton.setFont(Font.font("Source Sans Pro", FontWeight.BOLD, 18));
        playButton.getStyleClass().addAll("bible-button", "primary-button");

        playButton.setOnAction(e -> {
            if (onPlayClicked != null) {
                onPlayClicked.run();
            }
        });

        resumeButton = new Button("🔄 Resume Quiz");
        resumeButton.setFont(Font.font("Source Sans Pro", FontWeight.BOLD, 18));
        resumeButton.getStyleClass().addAll("bible-button", "secondary-button");
        resumeButton.setOnAction(e -> {
            if (onResumeClicked != null) {
                onResumeClicked.run();
            }
        });

        HBox buttonContainer = new HBox(20); // 20 is the spacing between buttons
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().add(playButton);

        if (hasRestorableState) {
            buttonContainer.getChildren().add(resumeButton);
        }

        // Add decorative religious symbols
        Label topSymbol = new Label("✝");
        topSymbol.getStyleClass().add("religious-symbol");
        topSymbol.setAlignment(Pos.CENTER);
        
        Label bottomSymbol = new Label("✝");
        bottomSymbol.getStyleClass().add("religious-symbol");
        bottomSymbol.setAlignment(Pos.CENTER);
        
        mainContainer.getChildren().addAll(
            topSymbol,
            titleLabel,
            subtitleLabel,
            instructionsLabel,
            buttonContainer,
            bottomSymbol
        );
    }

    public void show(Stage stage) {
        this.stage = stage;
        Scene scene = new Scene(mainContainer, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("✝ Bible Quiz - Welcome ✝");
        stage.show();
    }

    public void setOnPlayClicked(Runnable callback) {
        this.onPlayClicked = callback;
    }

    public void setOnResumeClicked(Runnable callback) {
        this.onResumeClicked = callback;
    }
}
