package edu.cbu.quizproject.presentation.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ScoreScreen {
    private VBox mainContainer;
    private Stage stage;
    private Runnable onRestartClicked;

    public ScoreScreen() {
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        mainContainer = new VBox();
        mainContainer.setSpacing(30);
        mainContainer.setPadding(new Insets(50));
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getStyleClass().add("parchment-bg");
    }
    
    private void setupLayout() {
        Label titleLabel = new Label("🎉 Quiz Complete! 🎉");
        titleLabel.getStyleClass().add("bible-title");
        
        Label scoreLabel = new Label("Your Score");
        scoreLabel.setFont(Font.font("Crimson Text", FontWeight.NORMAL, 18));
        scoreLabel.setStyle("-fx-text-fill: #654321;");
        
        Button restartButton = new Button("🔄 Take Another Quiz");
        restartButton.setFont(Font.font("Source Sans Pro", FontWeight.BOLD, 16));
        restartButton.getStyleClass().addAll("bible-button", "primary-button");
        
        restartButton.setOnAction(e -> {
            if (onRestartClicked != null) {
                onRestartClicked.run();
            }
        });
        
        Button exitButton = new Button("🚪 Exit");
        exitButton.setFont(Font.font("Source Sans Pro", FontWeight.BOLD, 16));
        exitButton.getStyleClass().addAll("bible-button", "danger-button");
        
        exitButton.setOnAction(e -> stage.close());
        
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
            scoreLabel,
            restartButton,
            exitButton,
            bottomSymbol
        );
    }

    public void setOnRestartClicked(Runnable onRestartClicked) {
        this.onRestartClicked = onRestartClicked;
    }
    
    public void show(Stage stage, int score, int totalQuestions) {
        this.stage = stage;
        
        Label scoreLabel = (Label) mainContainer.getChildren().get(1);
        double percentage = (double) score / totalQuestions * 100;
        scoreLabel.setText(String.format("📊 Your Score: %d/%d (%.1f%%)", score, totalQuestions, percentage));
        scoreLabel.getStyleClass().add("score-display");
        
        if (percentage >= 80) {
            scoreLabel.getStyleClass().add("score-excellent");
        } else if (percentage >= 60) {
            scoreLabel.getStyleClass().add("score-good");
        } else {
            scoreLabel.getStyleClass().add("score-poor");
        }
        
        Scene scene = new Scene(mainContainer, 700, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("✝ Bible Quiz - Results ✝");
        stage.show();
    }
} 