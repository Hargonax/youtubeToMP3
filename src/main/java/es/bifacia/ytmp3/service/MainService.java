package es.bifacia.ytmp3.service;

public interface MainService {

    /**
     * Runs the application.
     * First gets the list of songs from the Excel file.
     * Then, it does the same process for every song: checks if the song file already exists, transforms and downloads it from the Youtube video and finally it updates the metadata of the file with the information provided in the Excel file.
     * @throws Exception
     */
    void runApplication() throws Exception;
}
