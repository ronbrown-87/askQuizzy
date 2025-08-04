package edu.cbu.quizproject.presentation.components;

import java.util.function.Consumer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class QuizQuestionComponent {
    private VBox questionContainer;
    private HBox questionHeader;
    private Label questionTypeLabel;
    private Label questionNumberLabel;
    private Label questionTextLabel;
    private VBox optionsContainer;
    private ToggleGroup optionsGroup;
    private RadioButton[] optionButtons;
    private Consumer<Integer> onAnswerSelected;
    private boolean answerSubmitted = false;
    private Timeline blinkTimeline;
    
    public QuizQuestionComponent() {
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        questionTypeLabel = new Label("✝ Bible Quiz ✝");
        questionTypeLabel.getStyleClass().add("question-header");
        
        questionNumberLabel = new Label("Question 1");
        questionNumberLabel.getStyleClass().add("question-header");
        
        questionTextLabel = new Label("Loading question...");
        questionTextLabel.getStyleClass().add("question-text");
        questionTextLabel.setWrapText(true);
        
        optionsGroup = new ToggleGroup();
        optionButtons = new RadioButton[4];
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new RadioButton("Option " + (i + 1));
            optionButtons[i].setToggleGroup(optionsGroup);
            optionButtons[i].getStyleClass().add("option-button");
            
            final int optionIndex = i;
            optionButtons[i].setOnAction(e -> {
                if (!answerSubmitted) {
                    handleAnswerSelection(optionIndex);
                }
            });
        }
        
        optionsContainer = new VBox();
        optionsContainer.setSpacing(20);
        optionsContainer.getChildren().addAll(optionButtons);
        
        // Removed blinkTimeline since we don't want flashing anymore 
    }
    
    private void setupLayout() {
        questionContainer = new VBox();
        questionContainer.setSpacing(25);
        
        questionHeader = new HBox();
        questionHeader.setAlignment(Pos.CENTER_LEFT);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        questionHeader.getChildren().addAll(questionTypeLabel, spacer, questionNumberLabel);
        
        questionContainer.getChildren().addAll(
            questionHeader,
            questionTextLabel,
            optionsContainer
        );
    }
    
    private void setupStyles() {
        for (RadioButton button : optionButtons) {
            button.getStyleClass().clear();
            button.getStyleClass().add("option-button");
            
            button.setOnMouseEntered(e -> {
                if (!answerSubmitted) {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("option-button");
                }
            });
            
            button.setOnMouseExited(e -> {
                if (!answerSubmitted) {
                    setOptionStyles();
                }
            });
        }
    }
    
    private void setOptionStyles() {
        for (RadioButton button : optionButtons) {
            button.getStyleClass().clear();
            button.getStyleClass().add("option-button");
        }
    }
    
    // Removed setIncorrectBlinkStyle method since we don't want flashing anymore
    
    private void handleAnswerSelection(int optionIndex) {
        answerSubmitted = true;
        
        for (RadioButton button : optionButtons) {
            button.setDisable(true);
        }
        
        if (onAnswerSelected != null) {
            onAnswerSelected.accept(optionIndex);
        }
    }
    
    public VBox getNode() {
        return questionContainer;
    }
    
    public void updateQuestion(edu.cbu.quizproject.core.model.Question question, int selectedOption) {
        questionTextLabel.setText(question.getQuestion());
        
        optionsContainer.getChildren().clear();
        optionsGroup.getToggles().clear();
        
        String[] options = {
            question.getOption_a(),
            question.getOption_b(),
            question.getOption_c(),
            question.getOption_d()
        };
        
        optionButtons = new RadioButton[options.length];
        for (int i = 0; i < options.length; i++) {
            optionButtons[i] = new RadioButton(options[i]);
            optionButtons[i].setToggleGroup(optionsGroup);
            optionButtons[i].getStyleClass().add("option-button");
            optionButtons[i].setDisable(false);
            
            final int optionIndex = i;
            optionButtons[i].setOnAction(e -> {
                if (!answerSubmitted) {
                    handleAnswerSelection(optionIndex);
                }
            });
        }
        
        optionsContainer.getChildren().addAll(optionButtons);
        setupStyles();
        
        answerSubmitted = false;
        if (selectedOption >= 0 && selectedOption < optionButtons.length) {
            optionButtons[selectedOption].setSelected(true);
        }
    }
    
    public void showAnswerFeedback(int selectedAnswer, int correctAnswer) {
        answerSubmitted = true;
        
        for (int i = 0; i < optionButtons.length; i++) {
            RadioButton button = optionButtons[i];
            button.setDisable(true);
            
            if (i == selectedAnswer && i == correctAnswer) {
                button.getStyleClass().clear();
                button.getStyleClass().add("correct-answer");
            } else if (i == selectedAnswer && i != correctAnswer) {
                button.getStyleClass().clear();
                button.getStyleClass().add("incorrect-answer");
                // Removed blinkTimeline.play() to stop flashing
            } else if (i == correctAnswer) {
                button.getStyleClass().clear();
                button.getStyleClass().add("correct-answer");
            }
        }
    }
    
    public int getSelectedOption() {
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                return i;
            }
        }
        return -1; 
    }
    
    public void updateQuestionNumber(int questionNumber) {
        questionNumberLabel.setText("Question " + questionNumber);
    }
    
    public void setOnAnswerSelected(Consumer<Integer> callback) {
        this.onAnswerSelected = callback;
    }
}