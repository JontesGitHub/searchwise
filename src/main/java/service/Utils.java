package service;

import model.Term;
import org.apache.commons.math3.util.Precision;

import java.util.List;

public class Utils {

    // Makes also a round to not get a long decimal number
    public static double calcTFIDF(double tf, double idf) {
        return Precision.round(tf * idf, 3);
    }

    public static double calcIDF(double totalDocs, double termInDocs) {
        return Math.log(totalDocs / termInDocs);
    }

    public static double calcTF(String term, List<Term> terms) {
        long rawCount = getRawCount(term, terms);
        int documentWordCount = terms.size();
        return (double) rawCount / documentWordCount;
    }

     private static long getRawCount(String term, List<Term> terms) {
        return terms.stream().filter(t -> t.getText().equals(term)).count();
    }
}
