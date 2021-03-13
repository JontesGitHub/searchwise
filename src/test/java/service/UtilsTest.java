package service;

import lombok.AllArgsConstructor;
import model.Term;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @DisplayName("Should calculate the TF-IDF correct")
    @Test
    void calcTFIDF() {
        @AllArgsConstructor
        class TestCase {
            final double tf;
            final double idf;
            final double expected;
        }
        List<TestCase> testCases = Arrays.asList(
                new TestCase(0.45, 1.1, 0.495),
                new TestCase(0.666, 0.7, 0.466),
                new TestCase(0.09, 1.0, 0.09)
        );
        for (TestCase tc : testCases) {
            final double result = Utils.calcTFIDF(tc.tf, tc.idf);
            assertEquals(result, tc.expected);
        }
    }

    @DisplayName("Should calculate the Inverse Document Frequency correct")
    @Test
    void calcIDF() {
        @AllArgsConstructor
        class TestCase {
            final double totalDocs;
            final double termInDocs;
            final double expected;
        }
        List<TestCase> testCases = Arrays.asList(
                new TestCase(10.0, 5.0, Math.log(2)),
                new TestCase(126.0, 21.0, Math.log(6)),
                new TestCase(143.0, 11.0, Math.log(13))
        );
        for (TestCase tc : testCases) {
            final double result = Utils.calcIDF(tc.totalDocs, tc.termInDocs);
            assertEquals(result, tc.expected);
        }
    }

    @DisplayName("Should calculate the Term Frequency correct")
    @Test
    void calcTF() {
        @AllArgsConstructor
        class TestCase {
            final String term;
            final List<Term> allTerms;
            final double expected;
        }
        List<TestCase> testCases = Arrays.asList(
                new TestCase("same", Arrays.asList(new Term("same"), new Term("not")), 0.5),
                new TestCase("same", Arrays.asList(new Term("same"), new Term("not"), new Term("not"), new Term("not")), 0.25),
                new TestCase("same", Arrays.asList(new Term("same"), new Term("same"), new Term("same"), new Term("not")), 0.75)
        );
        for (TestCase tc : testCases) {
            final double result = Utils.calcTF(tc.term, tc.allTerms);
            assertEquals(result, tc.expected);
        }
    }
}