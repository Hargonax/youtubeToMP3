package es.bifacia.ytmp3.service.impl;

import es.bifacia.ytmp3.service.ExecutionResultManager;
import es.bifacia.ytmp3.utils.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class ExecutionResultManagerImpl implements ExecutionResultManager {
    private final static String RESULT_FILE = "./result.txt";
    private final StringBuilder sb = new StringBuilder();

    public ExecutionResultManagerImpl() {
        super();
    }

    /**
     * Adds a message to be stored in the results file.
     * @param message Message to store.
     */
    public void addMessage(final String message) {
        sb.append(message).append("\n");
    }

    /**
     * Creates the file with all the message obtained during the execution of the application.
     * @throws Exception
     */
    public void createExecutionResultFile() throws Exception {
        FileUtils.stringToFile(sb.toString(), RESULT_FILE);
    }

}
