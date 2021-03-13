package service;

import model.Document;
import model.Term;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.IndexStorage;

import java.util.Arrays;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexServiceTest {

    @Mock
    TermService termService;
    @Mock
    DocumentService documentService;
    @Mock
    IndexStorage indexStorage;

    IndexService indexService;

    @BeforeEach
    public void init() {
        indexService = new IndexService(termService, documentService, indexStorage);
    }

    @DisplayName("Should save as an inverted index correctly")
    @Test
    void saveInvertedIndexFromDocuments() {
        when(termService.stem(anyString())).thenReturn("mock mock");
        when(termService.getTerms(anyString())).thenReturn(Arrays.asList(new Term("one"), new Term("two")));

        indexService.saveInvertedIndexFromDocuments(Arrays.asList(new Document(1, "mock data text.")));

        verify(indexStorage, times(2)).save(anyString(), anyInt());
    }

    @Disabled
    @Test
    void getDocumentsWithTFIDF() {
//        TreeSet<Integer> postings = new TreeSet<>();
//        postings.add(1);
//        when(indexStorage.getPostingsByTerm(anyString())).thenReturn(postings);
//        when(documentService.findDocument(anyInt())).thenReturn(new Document(1, "mock mock mock"));

    }
}