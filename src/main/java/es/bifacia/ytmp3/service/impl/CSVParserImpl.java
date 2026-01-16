package es.bifacia.ytmp3.service.impl;

import es.bifacia.ytmp3.model.Song;
import es.bifacia.ytmp3.service.CSVParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVParserImpl implements CSVParser {

    @Value("${songs.csv.file}")
    private String songsFilePath;

    @Value("${mp3.output.folder}")
    private String outputFolder;

    private static final int ARTIST_COLUMN = 0;
    private static final int TITLE_COLUMN = 1;
    private static final int YOUTUBE_URL_COLUMN = 2;
    private static final int YEAR_COLUMN = 3;
    private static final int ALBUM_COLUMN = 4;
    private static final int START_COLUMN = 5;
    private static final int END_COLUMN = 6;
    private static final String COMMA_DELIMITER = ",";

    private final Logger logger = LogManager.getLogger(CSVParserImpl.class);

    public CSVParserImpl() {
        super();
    }

    /**
     * Parses a list of songs from a CSV file.
     * @return List of songs contained in the file.
     */
    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        try {
            final File file = ResourceUtils.getFile("classpath:" + songsFilePath);
            try (final BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                int counter = 1;
                line = br.readLine();
                while ((line = br.readLine()) != null) {
                    final String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    songs.add(parseSong(values, counter));
                    counter++;
                }
            }
        } catch (Exception ex) {
            logger.error("Error reading CSV file. {}", ex.getMessage());
        }
        return songs;
    }

    private Song parseSong(final String[] values, final int rowNumber) {
        final Song song = new Song();
        try {
            final String artist = values[ARTIST_COLUMN].replaceAll("\"", "");
            song.setArtist(artist);
            final String title = values[TITLE_COLUMN].replaceAll("\"", "");
            song.setTitle(title);
            final String filePath = outputFolder + artist + " - " + title + ".mp3";
            song.setFilePath(filePath);
            song.setYoutubeURL(values[YOUTUBE_URL_COLUMN]);
            song.setYear(values[YEAR_COLUMN]);
            song.setAlbum(values[ALBUM_COLUMN]);
            song.setStartOfSong(values[START_COLUMN]);
            song.setEndOfSong(values[END_COLUMN]);
        } catch (Exception ex) {
            final String message = "Error trying to parse row " + rowNumber  + " from the songs Excel.";
            logger.error(message);
        }
        return song;
    }

}
