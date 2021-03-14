import controller.InputController;
import service.DocumentService;
import service.IndexService;
import service.TermService;
import service.Utils;
import storage.DocumentStorage;
import storage.IndexStorage;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
//        int rawCount = 3;
//        int documentWordCount = 4;
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
        IndexService indexService = new IndexService(termService, documentService, new Utils(), indexStorage);

        InputController controller = new InputController(documentService, indexService);

        InputReader inputReader = new InputReader(controller);

        inputReader.start();
    }
}
