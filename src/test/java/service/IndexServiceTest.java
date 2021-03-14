package service;

import model.Document;
import model.SearchResult;
import model.Term;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import storage.IndexStorage;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Mock
    Utils utils;

    IndexService indexService;

    @BeforeEach
    public void init() {
        indexService = new IndexService(termService, documentService, utils, indexStorage);
    }

    @DisplayName("Should save as an inverted index correctly")
    @Test
    void saveInvertedIndexFromDocuments() {
        when(termService.stem(anyString())).thenReturn("mock mock");
        when(termService.getTerms(anyString())).thenReturn(Arrays.asList(new Term("one"), new Term("two")));

        indexService.saveInvertedIndexFromDocuments(Arrays.asList(new Document(1, "mock data text.")));

        verify(indexStorage, times(2)).save(anyString(), anyInt());
    }

    @DisplayName("Should return correct search result")
    @Test
    void searchTerm() {
        double idf = 0.5;
        double tf = 1.0;
        double tfidf = tf * idf;
        Document expectedDocument = new Document(1, "mockTerm");
        SearchResult expected = new SearchResult(tfidf, expectedDocument);

        TreeSet<Integer> postings = new TreeSet<>();
        postings.add(1);
        when(indexStorage.getPostingsByTerm(anyString()))
                .thenReturn(postings);
        when(documentService.findDocument(anyInt()))
                .thenReturn(expectedDocument);
        when(utils.calcIDF(anyDouble(), anyDouble()))
                .thenReturn(0.5);
        when(termService.getTerms(anyString()))
                .thenReturn(Arrays.asList(new Term("mockTerm")));
        when(utils.calcTF(anyString(), anyList()))
                .thenReturn(1.0);
        when(utils.calcTFIDF(anyDouble(), anyDouble()))
                .thenReturn(tfidf);

        List<SearchResult> resultList = indexService.searchTerm("mockTerm");


        assertEquals(expected.getTfidf(), resultList.get(0).getTfidf());
        assertEquals(expected.getDocument().getId(), resultList.get(0).getDocument().getId());
        assertEquals(expected.getDocument().getText(), resultList.get(0).getDocument().getText());
    }

    @DisplayName("Should return null")
    @Test
    void searchTerm_not_found() {
        TreeSet<Integer> emptyPostings = new TreeSet<>();
        when(indexStorage.getPostingsByTerm(anyString())).thenReturn(emptyPostings).thenReturn(null);

        List<SearchResult> result1 = indexService.searchTerm("mockTerm");
        List<SearchResult> result2 = indexService.searchTerm("mockTerm");

        List<SearchResult> expected = null;

        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }
}