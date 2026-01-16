package es.bifacia.ytmp3.service;

import es.bifacia.ytmp3.model.Song;

import java.util.List;

public interface CSVParser {

    /**
     * Parses a list of songs from a CSV file.
     * @return List of songs contained in the file.
     */
    List<Song> getSongs();
}
