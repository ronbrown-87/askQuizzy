package edu.cbu.quizproject.presentation.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class QuizProgressComponent {
    private VBox progressContainer;
    private ProgressBar progressBar;
    private Label questionLabel;
    private Label answeredLabel;
    
    public QuizProgressComponent() {
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        progressBar = new ProgressBar();
        progressBar.setProgress(0.2); 
        progressBar.setPrefHeight(12); 
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.getStyleClass().add("progress-bar");
        
        questionLabel = new Label("Question 2 of 5");
        questionLabel.setFont(Font.font("Source Sans Pro", FontWeight.NORMAL, 14));
        questionLabel.setStyle("-fx-text-fill: #8b4513;");
        
        answeredLabel = new Label("Answered: 1/5");
        answeredLabel.setFont(Font.font("Source Sans Pro", FontWeight.NORMAL, 14));
        answeredLabel.setStyle("-fx-text-fill: #8b4513;");
    }
    
    private void setupLayout() {
        progressContainer = new VBox();
        progressContainer.setSpacing(12);
        progressContainer.setPadding(new Insets(20, 25, 20, 25));
        
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        topRow.getChildren().addAll(questionLabel, spacer, answeredLabel);
        
        progressContainer.getChildren().addAll(topRow, progressBar);
    }
    
    private void setupStyles() {
        progressContainer.getStyleClass().add("decorative-border");
    }
    
    public VBox getNode() {
        return progressContainer;
    }
    
    public void updateProgress(int current, int total, int answered) {
        double progress = (double) current / total;
        progressBar.setProgress(progress);
        questionLabel.setText("Question " + current + " of " + total);
        answeredLabel.setText("Answered: " + answered + "/" + total);
    }
}