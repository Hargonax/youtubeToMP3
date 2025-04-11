package es.bifacia.ytmp3.service.impl;

import es.bifacia.ytmp3.service.ExecutionResultManager;
import org.springframework.stereotype.Service;

@Service
public abstract class ExecutionResultManagerImpl implements ExecutionResultManager {
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


}
