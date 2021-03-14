import controller.Controller;
import service.DocumentService;
import service.IndexService;
import service.TermService;
import service.Utils;
import storage.DocumentStorage;
import storage.IndexStorage;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        /*
         * Manual Dependency Injection,
         * Before calling start()
         */
        DocumentStorage documentStorage = new DocumentStorage(new HashMap<>());
        DocumentService documentService = new DocumentService(documentStorage);

        TermService termService = new TermService();

        IndexStorage indexStorage = new IndexStorage(new HashMap<>());
        IndexService indexService = new IndexService(termService, documentService, new Utils(), indexStorage);

        Controller controller = new Controller(documentService, indexService);

        InputHandler inputHandler = new InputHandler(controller);

        inputHandler.start();
    }
}
