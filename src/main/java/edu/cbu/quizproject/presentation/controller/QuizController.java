package edu.cbu.quizproject.presentation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import edu.cbu.quizproject.core.model.Question;
import edu.cbu.quizproject.core.model.QuizState;
import edu.cbu.quizproject.core.service.QuizService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

public class QuizController {
    private int currentQuestionIndex = 1; 
    private int totalQuestions = 15;
    private final QuizService quizService;
    private List<Question> questions;
    private int[] userAnswers;
    private int[] correctAnswers;
    private int score = 0;
    private boolean quizEnded = false;
    private Timeline timer;
    private int timeRemaining = 30; 
    private Runnable onTimerUpdate;
    private Runnable onQuizEnd;
    private boolean hasRestorableState = false;
    private Runnable onStateRestored;
    private boolean timerPaused = false;


    public QuizController() {
        userAnswers = new int[totalQuestions];
        correctAnswers = new int[totalQuestions];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
        this.quizService = new QuizService();
        restoreQuizState();
        if (!hasRestorableState) {
            loadBibleQuestions();
        }
        startTimer();
    }
    
    private void loadBibleQuestions() {
        questions = quizService.getRandomQuestions(totalQuestions);
        
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String answer = q.getAnswer();
            switch (answer) {
                case "option_a": correctAnswers[i] = 0; break;
                case "option_b": correctAnswers[i] = 1; break;
                case "option_c": correctAnswers[i] = 2; break;
                case "option_d": correctAnswers[i] = 3; break;
                default: correctAnswers[i] = 0;
            }
        }
    }

    public void persistQuizState() {
        if(quizEnded){
            return;
        }
        QuizState state = new QuizState();
        state.setCurrentQuestionIndex(currentQuestionIndex);
        state.setSelectedAnswers(Arrays.stream(userAnswers).boxed().collect(Collectors.toList()));
        state.setScore(score);
        state.setQuestions(questions);
        quizService.saveQuizState(state);
    }

    public boolean hasRestorableState() {
        return hasRestorableState;
    }

    private void restoreQuizState() {
        QuizState state = quizService.loadQuizState();
        if (state != null) {
            currentQuestionIndex = state.getCurrentQuestionIndex();
            userAnswers = state.getSelectedAnswers().stream().mapToInt(i -> i).toArray();
            score = state.getScore();
            questions = state.getQuestions();

            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                String answer = q.getAnswer();
                switch (answer) {
                    case "option_a": correctAnswers[i] = 0; break;
                    case "option_b": correctAnswers[i] = 1; break;
                    case "option_c": correctAnswers[i] = 2; break;
                    case "option_d": correctAnswers[i] = 3; break;
                    default: correctAnswers[i] = 0;
                }
            }

            hasRestorableState = true;
            if (onStateRestored != null) {
                Platform.runLater(onStateRestored);
            }
        } else {
            hasRestorableState = false;
        }
    }
    
    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!timerPaused) {
                timeRemaining--;
                if (onTimerUpdate != null) {
                    Platform.runLater(() -> onTimerUpdate.run());
                }
                
                if (timeRemaining <= 0) {
                    endQuiz();
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
    
    public void answerQuestion(int answerIndex) {
        if (quizEnded) return;

        int questionIndex = currentQuestionIndex - 1;
        boolean wasCorrect = userAnswers[questionIndex] != -1 && userAnswers[questionIndex] == correctAnswers[questionIndex];
        userAnswers[questionIndex] = answerIndex;
        boolean isCorrect = answerIndex == correctAnswers[questionIndex];

        if (isCorrect && !wasCorrect) {
            score++;
        } else if (!isCorrect && wasCorrect) {
            score--;
        }

        // Pause timer after answering
        timerPaused = true;

        if (currentQuestionIndex == totalQuestions) {
            endQuiz();
        }
    }
    
    private void endQuiz() {
        quizEnded = true;
        stopTimer();
        quizService.deleteQuizState();
        if (onQuizEnd != null) {
            Platform.runLater(() -> onQuizEnd.run());
        }
    }
    
    public void nextQuestion() {
        if (currentQuestionIndex < totalQuestions && !quizEnded) {
            currentQuestionIndex++;
            resetTimer();
            // Resume timer when moving to next question
            timerPaused = false;
        }
    }
    
    public void previousQuestion() {
        if (currentQuestionIndex > 1 && !quizEnded) {
            currentQuestionIndex--;
            resetTimer();
            // Resume timer when moving to previous question
            timerPaused = false;
        }
    }
    
    public void goToQuestion(int questionNumber) {
        if (questionNumber >= 1 && questionNumber <= totalQuestions && !quizEnded) {
            currentQuestionIndex = questionNumber;
            resetTimer();
            // Resume timer when jumping to any question
            timerPaused = false;
        }
    }
    
    private void resetTimer() {
        timeRemaining = 30;
    }
    
    public void exitQuiz() {
        persistQuizState();
    }

    public void setOnStateRestored(Runnable callback) {
        this.onStateRestored = callback;
    }


    public Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex - 1);
    }
    
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }
    
    public int getTotalQuestions() {
        return totalQuestions;
    }
    
    public int getAnsweredCount() {
        int count = 0;
        for (int answer : userAnswers) {
            if (answer != -1) {
                count++;
            }
        }
        return count;
    }
    
    public boolean hasPrevious() {
        return currentQuestionIndex > 1;
    }
    
    public boolean hasNext() {
        return currentQuestionIndex < totalQuestions;
    }
    
    public void setAnswer(int questionIndex, int answerIndex) {
        if (questionIndex >= 0 && questionIndex < totalQuestions) {
            userAnswers[questionIndex] = answerIndex;
        }
    }
    
    public int getAnswer(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < totalQuestions) {
            return userAnswers[questionIndex];
        }
        return -1;
    }
    
    public int getTimeRemaining() {
        return timeRemaining;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean isQuizEnded() {
        return quizEnded;
    }
    
    public boolean isAnswerCorrect(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < totalQuestions) {
            int userAnswer = userAnswers[questionIndex];
            return userAnswer != -1 && userAnswer == correctAnswers[questionIndex];
        }
        return false;
    }
    
    public int getCorrectAnswer(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < totalQuestions) {
            return correctAnswers[questionIndex];
        }
        return -1;
    }
    
    public void setOnTimerUpdate(Runnable callback) {
        this.onTimerUpdate = callback;
    }
    
    public void setOnQuizEnd(Runnable callback) {
        this.onQuizEnd = callback;
    }
}