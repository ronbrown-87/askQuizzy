package edu.cbu.quizproject.presentation.components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class QuizNavigationComponent {
    private HBox navigationContainer;
    private Button previousButton;
    private ScrollPane questionNumbersScrollPane;
    private HBox questionNumbersContainer;
    private Button nextButton;
    private Button[] questionButtons;
    private int totalQuestions = 15;
    
    private Runnable onPreviousClicked;
    private Runnable onNextClicked;
    private Consumer<Integer> onQuestionNumberClicked;
    
    public QuizNavigationComponent() {
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        previousButton = new Button("◀ Previous");
        previousButton.setFont(Font.font("Source Sans Pro", FontWeight.BOLD, 14));
        previousButton.getStyleClass().add("navigation-button");
        
        nextButton = new Button("Next ▶");
        nextButton.setFont(Font.font("Source Sans Pro", FontWeight.BOLD, 14));
        nextButton.getStyleClass().add("navigation-button");
        
        questionNumbersContainer = new HBox();
        questionNumbersContainer.setSpacing(3);
        questionNumbersContainer.setAlignment(Pos.CENTER);
        
        questionButtons = new Button[totalQuestions];
        for (int i = 0; i < totalQuestions; i++) {
            final int questionNum = i + 1;
            questionButtons[i] = new Button(String.valueOf(questionNum));
            questionButtons[i].setFont(Font.font("Source Sans Pro", FontWeight.BOLD, 12));
            questionButtons[i].setPrefSize(35, 35);
            questionButtons[i].getStyleClass().add("navigation-button");
            
            questionButtons[i].setOnAction(e -> {
                if (onQuestionNumberClicked != null) {
                    onQuestionNumberClicked.accept(questionNum);
                }
            });
        }
        
        updateQuestionButtonStates();
        
        questionNumbersContainer.getChildren().addAll(questionButtons);
        
        questionNumbersScrollPane = new ScrollPane(questionNumbersContainer);
        questionNumbersScrollPane.setFitToWidth(true);
        questionNumbersScrollPane.setFitToHeight(true);
        questionNumbersScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        questionNumbersScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        questionNumbersScrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        previousButton.setOnAction(e -> {
            if (onPreviousClicked != null) {
                onPreviousClicked.run();
            }
        });
        
        nextButton.setOnAction(e -> {
            if (onNextClicked != null) {
                onNextClicked.run();
            }
        });
    }
    
    private void setupLayout() {
        navigationContainer = new HBox();
        navigationContainer.setSpacing(15);
        navigationContainer.setPadding(new Insets(20, 25, 25, 25));
        navigationContainer.setAlignment(Pos.CENTER_LEFT);
        
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);
        
        navigationContainer.getChildren().addAll(
            previousButton,
            leftSpacer,
            questionNumbersScrollPane,
            rightSpacer,
            nextButton
        );
    }
    
    private void setupStyles() {
        navigationContainer.getStyleClass().add("decorative-border");
        updateQuestionButtonStates();
    }
    
    private void updateQuestionButtonStates() {
        questionButtons[0].getStyleClass().clear();
        questionButtons[0].getStyleClass().addAll("navigation-button", "primary-button");
        
        for (int i = 1; i < totalQuestions; i++) {
            questionButtons[i].getStyleClass().clear();
            questionButtons[i].getStyleClass().add("navigation-button");
        }
    }
    
    public HBox getNode() {
        return navigationContainer;
    }
    
    public void setOnPreviousClicked(Runnable callback) {
        this.onPreviousClicked = callback;
    }
    
    public void setOnNextClicked(Runnable callback) {
        this.onNextClicked = callback;
    }
    
    public void setOnQuestionNumberClicked(Consumer<Integer> callback) {
        this.onQuestionNumberClicked = callback;
    }
    
    public void updateNavigation(int currentQuestion, int totalQuestions, boolean hasPrevious, boolean hasNext) {
        previousButton.setDisable(!hasPrevious);
        nextButton.setDisable(!hasNext);
        
        for (int i = 0; i < questionButtons.length; i++) {
            questionButtons[i].getStyleClass().clear();
            questionButtons[i].getStyleClass().add("navigation-button");
            
            if (i + 1 == currentQuestion) {
                questionButtons[i].getStyleClass().add("primary-button");
            } else if (i + 1 < currentQuestion) {
                questionButtons[i].getStyleClass().add("secondary-button");
            }
        }
    }
}