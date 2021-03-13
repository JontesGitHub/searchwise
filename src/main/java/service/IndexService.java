package service;

import lombok.RequiredArgsConstructor;
import model.Document;
import model.Term;
import storage.IndexStorage;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
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

    public Map<Document, Double> getDocumentsWithTFIDF(String term) {
        SortedSet<Integer> postings = indexStorage.getPostingsByTerm(term);
        List<Document> documents = getDocumentsByIds(postings);

        double idf = calcIDF(documentService.getDocumentCount(), postings.size());

        return documents.stream()
                .collect(Collectors.toMap(
                        document -> document,
                        document -> {
                            final List<Term> terms = termService.getTerms(document.getText());
                            return calcTFIDF(calcTF(term, terms), idf);
                        }
                ));
        // TODO: Make it sorted also
    }

    private List<Document> getDocumentsByIds(SortedSet<Integer> postings) {
        return postings.stream()
                .map(documentService::findDocument)
                .collect(Collectors.toList());
    }
}
