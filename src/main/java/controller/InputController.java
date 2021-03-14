package controller;

import lombok.RequiredArgsConstructor;
import model.Document;
import model.SearchResult;
import service.DocumentService;
import service.IndexService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class InputController {
    private final DocumentService documentService;
    private final IndexService indexService;

    public void searchTerm(String term) {
        List<SearchResult> result = indexService.searchTerm(term);
        if (result != null) {
            result.forEach(res ->
                    System.out.printf("Tf-Idf: %.3f, Document: id: %d, text: %s\n",
                            res.getTfidf(),
                            res.getDocument().getId(),
                            res.getDocument().getText()
                    )
            );
        } else {
            System.out.printf("Nothing found for term: %s\n", term);
        }
    }

    public void addDocumentsFromFile(String fileName) {
        try {
            List<Document> documents = documentService.addDocumentsFromFile(fileName);
            indexService.saveInvertedIndexFromDocuments(documents);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
