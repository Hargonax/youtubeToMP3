package es.bifacia.ytmp3.service.excel;

import es.bifacia.ytmp3.model.Song;

import java.util.List;

public interface ExcelService {

    /**
     * Gets the list of songs from the Excel.
     * @return List of songs.
     * @throws Exception
     */
    List<Song> getSongs() throws Exception;

}
