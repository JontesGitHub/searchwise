package service;

import lombok.RequiredArgsConstructor;
import model.Document;
import model.Term;
import storage.IndexStorage;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class IndexService {

    private final TermService termService;
    private final DocumentService documentService;

    private final IndexStorage indexStorage;

    public void addInvertedIndexFromDocuments(List<Document> documentBatch) {
//        Map<Term, List<UUID>> indexBatch = Collections.emptyMap();

        documentBatch.forEach(doc -> {
            String stemmed = termService.stem(doc.getText());
            Set<Term> terms = termService.getTerms(stemmed).stream().distinct().collect(Collectors.toSet());
            terms.forEach(term -> indexStorage.save(term.getText(), doc.getId()));
        });

//        terms.forEach(term -> {
//            SortedSet<Integer> postings = indexStorage.getPostingsByTerm(term.getText());
//            if (postings == null) {
//                SortedSet<Integer> newPostings = new TreeSet<>();
//                postings.add(documentId);
//                indexStorage.save(term.getText(), newPostings);
//            }
//        });
    }

    public Map<Document, Double> getTFIDF(String term) {
        SortedSet<Integer> postings = indexStorage.getPostingsByTerm(term);
        List<Document> documents = getDocumentsByIds(postings);

        double idf = getIdf(documentService.getDocumentCount(), postings.size());

        return documents.stream()
                .collect(Collectors.toMap(
                        document -> document,
                        document -> getTF(term, document) * idf
                ));
    }

    private List<Document> getDocumentsByIds(SortedSet<Integer> postings) {
        return postings.stream()
                .map(documentService::findDocument)
                .collect(Collectors.toList());
    }

    private double getIdf(double totalDocs, double termInDocs) {
        return Math.log(totalDocs / termInDocs);
    }

    private double getTF(String term, Document document) {
        List<Term> terms = termService.getTerms(document.getText());
        int rawCount = (int) terms.stream().filter(t -> t.getText().equals(term)).count();
        int documentWordCount = terms.size();
        System.out.println("rawC: " + rawCount + " wordC: " + documentWordCount);
        return (double) rawCount / documentWordCount;
    }
}
