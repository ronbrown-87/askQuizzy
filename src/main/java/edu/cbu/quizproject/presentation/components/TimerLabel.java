package edu.cbu.quizproject.presentation.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class TimerLabel {
    private Label timerLabel;
    private HBox timerContainer;
    private Timeline blinkTimeline;
    private int timeRemaining = 30;
    
    public TimerLabel() {
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        timerLabel = new Label("30");
        timerLabel.getStyleClass().add("timer-label");
        
        blinkTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> timerLabel.setVisible(false)),
            new KeyFrame(Duration.seconds(1.0), e -> timerLabel.setVisible(true))
        );
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);
    }
    
    private void setupLayout() {
        timerContainer = new HBox();
        timerContainer.setSpacing(5);
        timerContainer.setPadding(new Insets(5, 10, 5, 10));
        timerContainer.setAlignment(javafx.geometry.Pos.CENTER);
        timerContainer.getStyleClass().add("timer-label");
        
        Label iconLabel = new Label("⏰");
        iconLabel.setFont(Font.font("System", FontWeight.NORMAL, 16));
        iconLabel.setStyle("-fx-text-fill: #8b4513;");
        
        timerContainer.getChildren().addAll(iconLabel, timerLabel);
    }
    
    private void setupStyles() {
        // Styles are now handled by CSS classes
    }
    
    public void updateTimer(int seconds) {
        this.timeRemaining = seconds;
        timerLabel.setText(String.valueOf(seconds));
        
        if (seconds <= 10) {
            timerLabel.getStyleClass().clear();
            timerLabel.getStyleClass().addAll("timer-label", "timer-warning");
            if (!blinkTimeline.getStatus().equals(javafx.animation.Animation.Status.RUNNING)) {
                blinkTimeline.play();
            }
        } else if (seconds <= 20) {
            timerLabel.getStyleClass().clear();
            timerLabel.getStyleClass().add("timer-label");
            blinkTimeline.stop();
            timerLabel.setVisible(true);
        } else {
            timerLabel.getStyleClass().clear();
            timerLabel.getStyleClass().add("timer-label");
            blinkTimeline.stop();
            timerLabel.setVisible(true);
        }
    }
    
    public void stopBlinking() {
        blinkTimeline.stop();
        timerLabel.setVisible(true);
    }
    
    public HBox getNode() {
        return timerContainer;
    }
} 