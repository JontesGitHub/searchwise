package service;

import lombok.RequiredArgsConstructor;
import model.Document;
import model.SearchResponse;
import model.Term;
import storage.IndexStorage;

import java.util.*;
import java.util.stream.Collectors;

import static service.Utils.*;

@RequiredArgsConstructor
public class IndexService {

    private final TermService termService;
    private final DocumentService documentService;

    private final IndexStorage indexStorage;

    public void saveInvertedIndexFromDocuments(List<Document> documentBatch) {
        documentBatch.forEach(doc -> {
            String stemmed = termService.stem(doc.getText());
            List<Term> terms = termService.getTerms(stemmed);
            terms.stream()
                    .distinct()
                    .forEach(term -> indexStorage.save(term.getText(), doc.getId()));
        });
    }

    public List<SearchResponse> searchTerm(String term) {
        SortedSet<Integer> postings = indexStorage.getPostingsByTerm(term);
        if (postings == null || postings.isEmpty()) {
            return null;
        }

        List<Document> documents = getDocumentsByIds(postings);
        return getSortedTFIDF(term, documents);
    }

    public List<SearchResponse> getSortedTFIDF(String term, List<Document> documents) {
        double idf = calcIDF(documentService.getDocumentCount(), documents.size());
        return documents.stream()
                .map(document -> convertToSearchResponse(term, idf, document))
                .sorted(Comparator.comparingDouble(SearchResponse::getTfidf))
                .collect(Collectors.toList());
    }

    private SearchResponse convertToSearchResponse(String term, double idf, Document document) {
        List<Term> terms = termService.getTerms(document.getText());
        return new SearchResponse(
                calcTFIDF(calcTF(term, terms), idf),
                document
        );
    }

    private List<Document> getDocumentsByIds(SortedSet<Integer> postings) {
        return postings.stream()
                .map(documentService::findDocument)
                .collect(Collectors.toList());
    }
}
