package storage;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

@Log
@AllArgsConstructor
public class IndexStorage {
    private Map<String, SortedSet<Integer>> invertedIndex;

//    private void save(String term, SortedSet<Integer> postings) {
//        invertedIndex.put(term, postings);
//    }

    public SortedSet<Integer> getPostingsByTerm(String term) {
//        log.info(String.format("Fetching Postings for Term: %s", term));
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
