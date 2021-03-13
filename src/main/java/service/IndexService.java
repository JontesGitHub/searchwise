package service;

import lombok.RequiredArgsConstructor;
import model.Document;
import model.Term;
import org.apache.commons.math3.util.Precision;
import storage.IndexStorage;

import java.util.*;
import java.util.stream.Collectors;

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

    public Map<Document, Double> getDocumentsWithTFIDF(String term) {
        SortedSet<Integer> postings = indexStorage.getPostingsByTerm(term);
        List<Document> documents = getDocumentsByIds(postings);

        double idf = Utils.calcIDF(documentService.getDocumentCount(), postings.size());

        return documents.stream()
                .collect(Collectors.toMap(
                        document -> document,
                        document -> {
                            final List<Term> terms = termService.getTerms(document.getText());
                            return Utils.calcTFIDF(Utils.calcTF(term, terms), idf);
                        }
                ));
    }

    private List<Document> getDocumentsByIds(SortedSet<Integer> postings) {
        return postings.stream()
                .map(documentService::findDocument)
                .collect(Collectors.toList());
    }
}
