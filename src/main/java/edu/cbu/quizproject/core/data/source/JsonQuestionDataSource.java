package edu.cbu.quizproject.core.data.source;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import edu.cbu.quizproject.core.data.repository.QuestionRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Empty class for now
public class JsonQuestionDataSource {
    private static final Logger LOGGER = Logger.getLogger(JsonQuestionDataSource.class.getName());

    public static List<QuestionRepository> Questions() {
        Gson gson = new Gson();
        String filePath = System.getProperty("user.dir") + "/src/main/resources/bible2.json";
        try (Reader reader = new FileReader(filePath)) {
            Type questionListType = new TypeToken<List<QuestionRepository>>() {}.getType();
            return gson.fromJson(reader, questionListType);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "JSON file not found: " + filePath, e);
        } catch (JsonSyntaxException e) {
            LOGGER.log(Level.SEVERE, "Malformed JSON in file: " + filePath, e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "I/O error reading JSON file: " + filePath, e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error loading questions", e);
        }
        return Collections.emptyList();
    }
} 