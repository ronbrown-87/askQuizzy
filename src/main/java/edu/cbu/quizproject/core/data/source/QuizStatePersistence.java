package edu.cbu.quizproject.core.data.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cbu.quizproject.core.model.QuizState;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QuizStatePersistence {
    private static final String STATE_FILE = System.getProperty("user.dir") + "/src/main/resources/quiz_state.json";

    public static void saveState(QuizState state) throws IOException {
        System.out.println("QuizStatePersistence: Saving to file: " + STATE_FILE);

        File file = new File(STATE_FILE);
        file.getParentFile().mkdirs();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String json = gson.toJson(state);

        try (FileWriter writer = new FileWriter(STATE_FILE)) {
            writer.write(json);
            writer.flush();
        }

        System.out.println("QuizStatePersistence: File written successfully");

        File savedFile = new File(STATE_FILE);
        System.out.println("QuizStatePersistence: File exists: " + savedFile.exists());
        System.out.println("QuizStatePersistence: File size: " + savedFile.length() + " bytes");
    }

    public static QuizState loadState() throws IOException {
        try (FileReader reader = new FileReader(STATE_FILE)) {
            return new Gson().fromJson(reader, QuizState.class);
        }
    }

    public static boolean hasSavedState() {
        File file = new File(STATE_FILE);
        return file.exists() && file.length() > 0;
    }

    public static void deleteState() {
        File file = new File(STATE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
