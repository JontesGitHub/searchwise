package service;

import model.Term;

import java.util.ArrayList;
import java.util.List;

public class TermService {

    public List<Term> getTerms(String input) {
        String[] words = input.split(" ");
        return convertToTerms(words);
    }

    private List<Term> convertToTerms(String[] input) {
        List<Term> terms = new ArrayList<>();
        for (String word : input) {
            terms.add(new Term(word.toLowerCase()));
        }
        return terms;
    }

    public String stem(String input) {
        String lastChar = input.substring(input.length() - 1);
        if (lastChar.equals(".") || lastChar.equals("?") || lastChar.equals("!")) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }
}
