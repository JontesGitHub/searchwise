package storage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexStorageTest {

    @DisplayName("Should get postings correct")
    @Test
    void getPostingsByTerm() {
        SortedSet<Integer> postingsMock = new TreeSet<>();
        postingsMock.add(3);
        postingsMock.add(7);
        postingsMock.add(4);
        String termMock = "termMock";
        Map<String, SortedSet<Integer>> mockDB = new HashMap<>();
        mockDB.put(termMock, postingsMock);

        final SortedSet<Integer> expectedPostings = mockDB.get(termMock);

        IndexStorage storage = new IndexStorage(mockDB);

        final SortedSet<Integer> result = storage.getPostingsByTerm(termMock);

        assertEquals(expectedPostings, result);
    }

    @DisplayName("Should save and add a new docID to existing Term Key in DB")
    @Test
    void save_existing() {
        SortedSet<Integer> postingsMock = new TreeSet<>();
        postingsMock.add(3);
        String termMock = "termMock";
        Map<String, SortedSet<Integer>> mockDB = new HashMap<>();
        mockDB.put(termMock, postingsMock);

        int expectedSizeAfterTest = mockDB.get(termMock).size() + 1;

        IndexStorage storage = new IndexStorage(mockDB);

        storage.save(termMock, 7);

        assertEquals(expectedSizeAfterTest, mockDB.get(termMock).size());
    }

    @DisplayName("Should save and add a new docID to non existing Term Key in DB")
    @Test
    void save_non_existing() {
        Map<String, SortedSet<Integer>> mockDB = new HashMap<>();

        String termMock = "termMock";
        IndexStorage storage = new IndexStorage(mockDB);

        storage.save(termMock, 7);

        assertEquals(1, mockDB.get(termMock).size());
    }
}