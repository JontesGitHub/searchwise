package service;

import model.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.DocumentStorage;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
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

    @DisplayName("Should save and return correctly")
    @Test
    void addDocumentsFromFile() throws IOException {
        String testFile = "src/test/java/testDocument.txt";
        Document expected = new Document(1, "This is a mock text!");

        List<Document> resultList = documentService.addDocumentsFromFile(testFile);

        verify(documentStorage).save(any(Document.class));

        assertEquals(expected.getId(), resultList.get(0).getId());
        assertEquals(expected.getText(), resultList.get(0).getText());
    }

    @DisplayName("Should throw when file dont exist")
    @Test
    void addDocumentsFromFile_throw_error() {
        String testFile = "non_existing_file.txt";
        assertThrows(IOException.class, () -> documentService.addDocumentsFromFile(testFile));
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