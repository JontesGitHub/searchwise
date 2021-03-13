package controller;

import lombok.RequiredArgsConstructor;
import model.Document;
import service.DocumentService;
import service.IndexService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class InputController {
    private final DocumentService documentService;
    private final IndexService indexService;

    public void searchTerm(String term) {
        Map<Document, Double> result = indexService.getDocumentsWithTFIDF(term);
        // make it sorted in tf-idf
        System.out.println(result.toString());
        for (Map.Entry<Document, Double> entry : result.entrySet()) {
            System.out.println(entry.getKey().getText() + ":" + entry.getValue().toString());
        }
        // TODO: return correct format
    }

    public void addDocumentsFromFile(String fileName) {
        List<Document> documents = documentService.addDocumentsFromFile(fileName);
        indexService.saveInvertedIndexFromDocuments(documents);
    }
}
