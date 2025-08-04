// FIXED version
package edu.cbu.quizproject.core.service;

import edu.cbu.quizproject.core.data.repository.QuestionRepository;
import edu.cbu.quizproject.core.model.Question;
import edu.cbu.quizproject.core.model.QuizState;
import edu.cbu.quizproject.core.data.source.QuizStatePersistence;
import java.util.*;

public class QuizService {
    private final QuestionRepository questionRepository;

    public QuizService() {
        this.questionRepository = new QuestionRepository();
    }
    public List<Question> getRandomQuestions(int count) {
        List<Question> allQuestions = questionRepository.loadQuestions();
        List<Question> selected = new ArrayList<>();
        Set<Integer> usedIndexes = new HashSet<>();

        Random rand = new Random();

        while (selected.size() < count) {
            int index = rand.nextInt(allQuestions.size());
            if (!usedIndexes.contains(index)) {
                selected.add(allQuestions.get(index));
                usedIndexes.add(index);
            }
        }
        return selected;
    }

    public void saveQuizState(QuizState state) {
        try {
            QuizStatePersistence.saveState(state);
        } catch (Exception e) {
            System.err.println("QuizService: Failed to save quiz state: " + e.getMessage());
        }
    }

    public QuizState loadQuizState() {
        try {
            QuizState state = QuizStatePersistence.loadState();
            if (state != null && state.getQuestions() != null && !state.getQuestions().isEmpty() && state.getCurrentQuestionIndex() > 0) {
                return state;
            }
            return null;
        } catch (Exception e) {
            System.err.println("QuizService: Failed to load quiz state: " + e.getMessage());
            return null;
        }
    }

    public void deleteQuizState() {
        try {
            QuizStatePersistence.deleteState();
        } catch (Exception e) {
            System.err.println("QuizService: Failed to delete quiz state: " + e.getMessage());
        }
    }
}
