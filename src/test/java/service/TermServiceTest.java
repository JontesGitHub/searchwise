package service;

import lombok.AllArgsConstructor;
import model.Term;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TermServiceTest {

    @Test
    void getTerms() {
        @AllArgsConstructor
        class TestCase {
            final String input;
            final List<Term> expected;
        }
        List<TestCase> testCases = Arrays.asList(
                new TestCase("hello world how are you today",
                        Arrays.asList(new Term("hello"), new Term("world"), new Term("how"),
                                new Term("are"), new Term("you"), new Term("today"))),

                new TestCase("This is Dennis talking in my tests",
                        Arrays.asList(new Term("this"), new Term("is"), new Term("dennis"),
                                new Term("talking"), new Term("in"), new Term("my"), new Term("tests"))),

                new TestCase("This is a SHORT text",
                        Arrays.asList(new Term("this"), new Term("is"), new Term("a"),
                                new Term("short"), new Term("text")))
        );
        TermService service = new TermService();
        for (TestCase tc : testCases) {
            final List<Term> result = service.getTerms(tc.input);
            assertTrue(result.containsAll(tc.expected));
        }
    }

    @DisplayName("Should remove lastChar if needed")
    @Test
    void stem() {
        @AllArgsConstructor
        class TestCase {
            final String input;
            final String expected;
        }
        String returnSame = "same";
        List<TestCase> testCases = Arrays.asList(
                new TestCase(returnSame, returnSame),
                new TestCase("hello world!", "hello world"),
                new TestCase("today is a good day.", "today is a good day"),
                new TestCase("mock this?", "mock this")
        );
        TermService service = new TermService();
        for (TestCase tc : testCases) {
            final String result = service.stem(tc.input);
            assertEquals(tc.expected, result);
        }
    }
}