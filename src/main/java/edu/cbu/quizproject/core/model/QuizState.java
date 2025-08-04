package edu.cbu.quizproject.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizState {
    @Expose
    @SerializedName("currentQuestionIndex")
    public int currentQuestionIndex;

    @Expose
    @SerializedName("selectedAnswers")
    public List<Integer> selectedAnswers;

    @Expose
    @SerializedName("score")
    public int score;

    @Expose
    @SerializedName("questions")
    public List<Question> questions;

    // Getters and setters
    public int getCurrentQuestionIndex() { return currentQuestionIndex; }
    public void setCurrentQuestionIndex(int idx) { this.currentQuestionIndex = idx; }

    public List<Integer> getSelectedAnswers() { return selectedAnswers; }
    public void setSelectedAnswers(List<Integer> answers) { this.selectedAnswers = answers; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public List<Question> getQuestions() { return questions; }

    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
