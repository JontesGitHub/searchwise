package service;

import model.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.DocumentStorage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {
    @Mock
    DocumentStorage documentStorage;

    DocumentService documentService;

    @BeforeEach
    public void init() {
        documentService = new DocumentService(documentStorage);
    }

    @Test
    void addDocumentsFromFile() {
// TODO: test

    }

    @Test
    void convertToDocuments() {
// TODO: test
    }

    @DisplayName("Should find correct Document")
    @Test
    void findDocument() {
        int id = 1;
        Document expected = new Document(id, "mock");
        when(documentStorage.findById(anyInt())).thenReturn(expected);

        Document result = documentService.findDocument(id);

        assertEquals(expected, result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getText(), result.getText());
    }
}