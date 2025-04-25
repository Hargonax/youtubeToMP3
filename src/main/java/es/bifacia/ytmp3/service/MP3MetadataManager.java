package es.bifacia.ytmp3.service;

import es.bifacia.ytmp3.model.Song;

public interface MP3MetadataManager {

    /**
     * Updates the MP3 metadata with the information we have of the song.
     * @param song Song to update.
     */
    void updateMP3Metadata(final Song song);

    /**
     * Updates the MP3 metadata with the information we have of the song.
     * @param song Song to update.
     */
    void updateID3V1MP3Metadata(final Song song);
}
