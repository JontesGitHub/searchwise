package storage;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import model.Document;

import java.util.Map;

@Log
@AllArgsConstructor
public class DocumentStorage {
    private final Map<Integer, Document> documents;

    public void save(Document document) {
        documents.put(document.getId(), document);
        log.info(String.format("Saved Document with ID: %s\n", document.getId()));
    }

    public Document findById(int id) {
        return documents.get(id);
    }

    public int size() {
        return documents.size();
    }
}
