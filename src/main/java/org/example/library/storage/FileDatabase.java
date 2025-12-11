package org.example.library.storage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * Utility class responsible for loading, saving, and managing
 * the JSON file used as the library's database.
 *
 * <p>This database contains JSON arrays for users, books, and loans.
 * Methods in this class allow switching between a main database and
 * a test database, ensuring safe testing without corrupting real data.</p>
 *
 * @author Dima & Asmaa
 * @version 1.0
 */
public class FileDatabase {




    private static String PATH = System.getProperty("user.dir") + "/library.json";
    /**
     * Switches the database path to the main application database file (library.json).
     * This method is used when the system runs normally outside the testing environment.
     */
    public static void useMainDatabase() {
        PATH = System.getProperty("user.dir") + "/library.json";
    }
    /**
     * Switches the database path to a dedicated test database file (test_library.json).
     * <p>
     * This prevents tests from modifying the real system database.
     * Should be used in all unit tests.
     */
    public static void useTestDatabase() {
        PATH = System.getProperty("user.dir") + "/test_library.json";
    }
    /**
     * Loads the JSON database from disk.
     *
     * <p>If the file does not exist, it automatically creates a new JSON
     * structure containing empty arrays for "users", "books", and "loans".
     * </p>
     *
     * @return the loaded database as a {@link JSONObject}
     * @throws RuntimeException if the file cannot be read
     */

    public static JSONObject load() {
        try {
            if (!Files.exists(Paths.get(PATH))) {
                JSONObject empty = new JSONObject();
                empty.put("users", new JSONArray());
                empty.put("books", new JSONArray());
                empty.put("loans", new JSONArray());
                save(empty);
            }

            String content = Files.readString(Paths.get(PATH));
            return new JSONObject(content);

        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file", e);
        }
    }
    /**
     * Saves the provided JSON object to disk as the current database state.
     *
     * @param json the database object to save
     * @throws RuntimeException if writing to disk fails
     */
    public static void save(JSONObject json) {
        try {
            Files.writeString(Paths.get(PATH), json.toString(4));
        } catch (Exception e) {
            throw new RuntimeException("Error saving JSON file", e);
        }
    }
    /**
     * Clears all database contents and replaces them with empty arrays.
     *
     * <p>Main use-case: preparing a clean environment for unit tests.</p>
     */
    public static void reset() {
        JSONObject empty = new JSONObject();
        empty.put("users", new JSONArray());
        empty.put("books", new JSONArray());
        empty.put("loans", new JSONArray());
        save(empty);
    }
}
