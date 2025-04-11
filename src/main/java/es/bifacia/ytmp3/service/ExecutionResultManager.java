package es.bifacia.ytmp3.service;

public interface ExecutionResultManager {

    /**
     * Adds a message to be stored in the results file.
     * @param message Message to store.
     */
    void addMessage(final String message);

    /**
     * Creates the file with all the message obtained during the execution of the application.
     * @throws Exception
     */
    void createExecutionResultFile() throws Exception;

}
