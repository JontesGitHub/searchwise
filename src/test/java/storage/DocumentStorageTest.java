package storage;

import model.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentStorageTest {

    @DisplayName("Should save correctly to DB")
    @Test
    void save() {
        Map<Integer, Document> mockDB = new HashMap<>();
        int expectedSizeAfterTest = mockDB.size() + 1;
        DocumentStorage storage = new DocumentStorage(mockDB);

        int docID = 1;
        Document mockData = new Document(docID, "mock mock mock");

        storage.save(mockData);

        assertEquals(expectedSizeAfterTest, mockDB.size());
    }

    @DisplayName("Should find the correct Document")
    @Test
    void findById() {
        int docID = 1;
        Document expected = new Document(docID, "mock mock mock");
        Map<Integer, Document> mockDB = new HashMap<>();
        mockDB.put(docID, expected);

        DocumentStorage storage = new DocumentStorage(mockDB);

        Document result = storage.findById(docID);

        assertEquals(expected, result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getText(), result.getText());
    }

    @DisplayName("Should give the correctly size of DB")
    @Test
    void size() {
        int docID = 1;
        Document mockData = new Document(docID, "mock mock mock");
        Map<Integer, Document> mockDB = new HashMap<>();
        mockDB.put(docID, mockData);

        int expectedSizeAfterTest = mockDB.size();

        DocumentStorage storage = new DocumentStorage(mockDB);

        final int result = storage.size();

        assertEquals(expectedSizeAfterTest, result);
    }
}