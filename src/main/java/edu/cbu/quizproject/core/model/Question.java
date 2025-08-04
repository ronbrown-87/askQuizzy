package edu.cbu.quizproject.core.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {
    @Expose
    @SerializedName("question")
    private String question;

    @Expose
    @SerializedName("option_a")
    private String option_a;

    @Expose
    @SerializedName("option_b")
    private String option_b;

    @Expose
    @SerializedName("option_c")
    private String option_c;

    @Expose
    @SerializedName("option_d")
    private String option_d;

    @Expose
    @SerializedName("answer")
    private String answer;

    // Getters and Setters
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getOption_a() { return option_a; }
    public void setOption_a(String option_a) { this.option_a = option_a; }

    public String getOption_b() { return option_b; }
    public void setOption_b(String option_b) { this.option_b = option_b; }

    public String getOption_c() { return option_c; }
    public void setOption_c(String option_c) { this.option_c = option_c; }

    public String getOption_d() { return option_d; }
    public void setOption_d(String option_d) { this.option_d = option_d; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    @Override
    public String toString() {
        return  "Q: " + question + "\n" +
                "A. " + option_a + "\n" +
                "B. " + option_b + "\n" +
                "C. " + option_c + "\n" +
                "D. " + option_d + "\n" +
                "Correct Answer: " + answer + "\n";
    }

}
