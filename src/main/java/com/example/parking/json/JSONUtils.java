package com.example.parking.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JSONUtils {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())  // Register serializer
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()) // Register deserializer
            .setPrettyPrinting()
            .create();
    private static final String DATA_DIRECTORY = "src/com/example/parking/data/";

    private static void ensureFileExists(String fileName) throws IOException {
        File file = new File(DATA_DIRECTORY + fileName);
        if (!file.exists()) {
            file.createNewFile();
            try (Writer writer = new FileWriter(file)) {
                writer.write("[]"); // Write empty JSON array
            }
        }
    }

    // Save an ArrayList to a file
    public static <T> void saveToFile(ArrayList<T> list, String fileName) {
        try {
            ensureFileExists(fileName);
            try (Writer writer = new FileWriter(DATA_DIRECTORY + fileName)) {
                gson.toJson(list, writer);
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load an ArrayList from a file
    public static <T> ArrayList<T> loadFromFile(String fileName, Type typeOfT) {
        try {
            ensureFileExists(fileName);
            try (Reader reader = new FileReader(DATA_DIRECTORY + fileName)) {
                return gson.fromJson(reader, typeOfT);
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty ArrayList if there's an error
        }
    }
}
