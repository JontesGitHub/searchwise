import controller.InputController;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public class InputReader {
    private final String ADD_DOCUMENTS = "-add";
    private final String SEARCH_TERM = "-search";
    private final String EXIT = "-exit";
    private final String HELP = "-help";

    private final String HELPER_TEXT = "SearchWise - Commands:\n" +
            "To ADD a text file containing documents:   -add <FILE_NAME> (default = defaultDocuments.txt)\n" +
            "To SEARCH for a term:                      -search <WORD>\n" +
            "To EXIT the program (cleans in.mem db):    -exit\n" +
            "To see alla commands:                      -help\n";
    private final String UNKNOWN_INPUT = "Unknown command. Try -help to see all commands.\n";
    private final String DEFAULT_DOC_FILE = "defaultDocuments.txt";
    private final String TERM_MISSING_ERROR = "Error: Missing term param.";

    private final InputController inputController;

    public void start() {
        System.out.println("Welcome to SearchWise, A Simple Search Engine");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            String input;
            while ((input = reader.readLine()) != null) {
                operation(input);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void operation(String input) {
        String[] args = input.split(" ", 2);
        switch (args[0]) {
            case ADD_DOCUMENTS:
                String file = args.length < 2 ? DEFAULT_DOC_FILE : args[1];
                inputController.addDocumentsFromFile(file);
                break;

            case SEARCH_TERM:
                if (args.length < 2) {
                    System.out.println(TERM_MISSING_ERROR);
                    break;
                }
                inputController.searchTerm(args[1]);
                break;

            case EXIT:
                System.exit(0);

            case HELP:
                System.out.println(HELPER_TEXT);
                break;

            default:
                System.out.println(UNKNOWN_INPUT);
                break;
        }
    }
}
