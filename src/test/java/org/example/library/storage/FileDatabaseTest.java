package org.example.library.storage;

import org.json.JSONObject;
import org.json.JSONArray;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FileDatabase class.
 *
 * Ensures that loading, saving, and resetting the JSON database
 * work correctly without affecting the main production file.
 *
 * This test uses a separate test JSON file (test_library.json).
 */
class FileDatabaseTest {

    private static final String TEST_PATH =
            System.getProperty("user.dir") + "/test_library.json";

    @BeforeEach
    void setUp() {
        FileDatabase.useTestDatabase();
        FileDatabase.reset();
    }

    @AfterEach
    void cleanUp() throws Exception {
        Files.deleteIfExists(Paths.get(TEST_PATH));
    }

    @Test
    void testLoadCreatesFileIfMissing() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_PATH));

        JSONObject db = FileDatabase.load();

        assertNotNull(db);
        assertTrue(db.has("users"));
        assertTrue(db.has("books"));
        assertTrue(db.has("loans"));
    }

    @Test
    void testSaveWritesCorrectData() {
        JSONObject json = new JSONObject();
        json.put("users", new JSONArray());
        json.put("books", new JSONArray());
        json.put("loans", new JSONArray());
        json.put("testValue", 123);

        FileDatabase.save(json);

        JSONObject loaded = FileDatabase.load();

        assertEquals(123, loaded.getInt("testValue"));
    }

    @Test
    void testResetClearsDatabase() {
        JSONObject json = new JSONObject();
        json.put("users", new JSONArray().put("X"));
        json.put("books", new JSONArray().put("Y"));
        json.put("loans", new JSONArray().put("Z"));

        FileDatabase.save(json);

        FileDatabase.reset();
        JSONObject resetJson = FileDatabase.load();

        assertEquals(0, resetJson.getJSONArray("users").length());
        assertEquals(0, resetJson.getJSONArray("books").length());
        assertEquals(0, resetJson.getJSONArray("loans").length());
    }
}
