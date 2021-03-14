package service;

import lombok.RequiredArgsConstructor;
import model.Document;
import model.SearchResult;
import model.Term;
import storage.IndexStorage;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class IndexService {

    private final TermService termService;
    private final DocumentService documentService;
    private final Utils utils;

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

    public List<SearchResult> searchTerm(String term) {
        SortedSet<Integer> postings = indexStorage.getPostingsByTerm(term);
        if (postings == null || postings.isEmpty()) {
            return null;
        }

        List<Document> documents = getDocumentsByIds(postings);
        return getSortedSearchResult(term, documents);
    }

    private List<SearchResult> getSortedSearchResult(String term, List<Document> documents) {
        double idf = utils.calcIDF(documentService.getDocumentCount(), documents.size());
        return documents.stream()
                .map(document -> convertToSearchResult(term, idf, document))
                .sorted(Comparator.comparingDouble(SearchResult::getTfidf))
                .collect(Collectors.toList());
    }

    private SearchResult convertToSearchResult(String term, double idf, Document document) {
        List<Term> terms = termService.getTerms(document.getText());
        return new SearchResult(
                utils.calcTFIDF(utils.calcTF(term, terms), idf),
                document
        );
    }

    private List<Document> getDocumentsByIds(SortedSet<Integer> postings) {
        return postings.stream()
                .map(documentService::findDocument)
                .collect(Collectors.toList());
    }
}
