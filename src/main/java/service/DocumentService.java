package service;

import lombok.RequiredArgsConstructor;
import model.Document;
import storage.DocumentStorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DocumentService {

    private final DocumentStorage documentStorage;
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public List<Document> addDocumentsFromFile(String file) {
        List<String> sentences = readFile(new File(file));
        List<Document> documents = convertToDocuments(sentences);

        documents.forEach(documentStorage::save);
        return documents;
    }

    private List<String> readFile(File file) {
        try (BufferedReader readTextFile = new BufferedReader(new FileReader(file))) {
            List<String> sentences = new ArrayList<>();
            String row;

            while ((row = readTextFile.readLine()) != null) {
                sentences.add(row);
            }
            return sentences;

        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
            return null;
        }
    }

    public List<Document> convertToDocuments(List<String> sentences) {
        return sentences.stream()
                .map(s -> new Document(idCounter.getAndIncrement(), s))
                .collect(Collectors.toList());
    }

    public int getDocumentCount() {
        return documentStorage.size();
    }

    public Document findDocument(int docID) {
        return documentStorage.findById(docID);
    }
}
