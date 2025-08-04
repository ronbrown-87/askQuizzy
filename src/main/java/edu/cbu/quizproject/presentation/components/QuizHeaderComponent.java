package edu.cbu.quizproject.presentation.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class QuizHeaderComponent {
    private HBox headerContainer;
    private Label titleLabel;
    private TimerLabel timerLabel;
    private Runnable onExitClicked;
    
    public QuizHeaderComponent() {
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        titleLabel = new Label("✝ Bible Quiz ✝");
        titleLabel.getStyleClass().add("bible-title");
        
        timerLabel = new TimerLabel();
    }
    
    private void setupLayout() {
        headerContainer = new HBox();
        headerContainer.setSpacing(15);
        headerContainer.setPadding(new Insets(15, 25, 15, 25));
        headerContainer.setAlignment(Pos.CENTER_LEFT);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        headerContainer.getChildren().addAll(titleLabel, spacer, timerLabel.getNode());
    }
    
    private void setupStyles() {
        headerContainer.getStyleClass().add("decorative-border");
    }
    
    public void setOnExitClicked(Runnable callback) {
        this.onExitClicked = callback;
    }
    
    public HBox getNode() {
        return headerContainer;
    }
    
    public void updateTimer(int seconds) {
        timerLabel.updateTimer(seconds);
    }
}