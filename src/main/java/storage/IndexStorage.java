package storage;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import model.Term;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Log
@AllArgsConstructor
public class IndexStorage {
    private final Map<String, SortedSet<Integer>> invertedIndex;

    public SortedSet<Integer> getPostingsByTerm(String term) {
        return invertedIndex.get(term);
    }

    public void save(String term, int docID) {
        if (invertedIndex.get(term) == null) {
            SortedSet<Integer> postings = new TreeSet<>();
            postings.add(docID);
            invertedIndex.put(term, postings);
        } else {
            SortedSet<Integer> postings = getPostingsByTerm(term);
            postings.add(docID);
            invertedIndex.put(term, postings);
        }
        log.info(String.format("Added docID: %d, to Term: %s\n", docID, term));
    }
}
