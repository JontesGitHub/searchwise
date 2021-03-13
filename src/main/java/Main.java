import controller.InputController;
import model.Term;
import org.apache.commons.math3.util.Precision;
import service.DocumentService;
import service.IndexService;
import service.TermService;
import storage.DocumentStorage;
import storage.IndexStorage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
//        int rawCount = 1;
//        int documentWordCount = 8;
//
//        int termInDocs = 3;
//        int totalDocs = 5;
//
//
//        double TF = (double) rawCount / documentWordCount;
//        System.out.println(TF);
//        double IDF = Math.log((double) totalDocs / termInDocs);
//        System.out.println(IDF);
//        System.out.println(Precision.round(TF*IDF, 3));

        DocumentStorage documentStorage = new DocumentStorage(new HashMap<>());
        DocumentService documentService = new DocumentService(documentStorage);

        TermService termService = new TermService();

        IndexStorage indexStorage = new IndexStorage(new HashMap<>());
        IndexService indexService = new IndexService(termService, documentService, indexStorage);

        InputController controller = new InputController(documentService, indexService);

        controller.addDocumentsFromFile("inputFile.txt");
        controller.searchTerm("is");
    }
}
