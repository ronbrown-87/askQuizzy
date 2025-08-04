package edu.cbu.quizproject.core.data.repository;

import edu.cbu.quizproject.core.model.Question;
import edu.cbu.quizproject.core.data.source.JsonQuestionDataSource;

import java.util.ArrayList;
import java.util.List;

// Empty class for now
public class QuestionRepository {
    public List<Question> loadQuestions() {
        List<QuestionRepository> rawList = JsonQuestionDataSource.Questions();
        List<Question> convertedList = new ArrayList<>();
        for (QuestionRepository repo : rawList) {
            convertedList.add(repo.toQuestion());
        }
        return convertedList;
    }

    public Question toQuestion() {
        Question q = new Question();
        q.setQuestion(this.question);
        q.setOption_a(this.option_a);
        q.setOption_b(this.option_b);
        q.setOption_c(this.option_c);
        q.setOption_d(this.option_d);
        q.setAnswer(this.answer);
        return q;
    }

    private String question;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private String answer;

}