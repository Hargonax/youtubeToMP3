package es.bifacia.ytmp3.service;

public interface MP3Manager {

    /**
     * Trims the duration of an MP3.
     * @param filePath Path of the file to trim.
     * @param startOfTrim Start in the original song for the new MP3.
     * @param endOfTrim End in the original song for the new MP3.
     */
    void trimSong(final String filePath, String startOfTrim, final String endOfTrim);

}
