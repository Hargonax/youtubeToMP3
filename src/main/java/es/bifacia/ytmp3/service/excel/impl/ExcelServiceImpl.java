package es.bifacia.ytmp3.service.excel.impl;

import es.bifacia.ytmp3.model.Song;
import es.bifacia.ytmp3.service.ExecutionResultManager;
import es.bifacia.ytmp3.service.excel.ExcelService;
import es.bifacia.ytmp3.service.impl.MP3MetadataManagerImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    private static final int ARTIST_COLUMN = 0;
    private static final int TITLE_COLUMN = 1;
    private static final int YOUTUBE_URL_COLUMN = 2;
    private static final int YEAR_COLUMN = 3;
    private static final int ALBUM_COLUMN = 4;
    private static final int SONG_START_COLUMN = 5;
    private static final int SONG_END_COLUMN = 6;

    private final Logger logger = LogManager.getLogger(MP3MetadataManagerImpl.class);

    @Value("${mp3.output.folder}")
    private String outputFolder;

    @Value("${songs.file}")
    private String songsFilePath;

    @Autowired
    private ExecutionResultManager resultManager;

    public ExcelServiceImpl() {
        super();
    }

    /**
     * Gets the list of songs from the Excel.
     * @return List of songs.
     * @throws Exception
     */
    public List<Song> getSongs() throws Exception {
        final List<Song> songs = new ArrayList<>();
        final XSSFSheet sheet = getFirstSheet(songsFilePath);
        for (final Row row : sheet) {
            if (row.getRowNum() != 0) {
                if (StringUtils.isEmpty(row.getCell(TITLE_COLUMN).getStringCellValue())) {
                    break;
                }
                final Song song = parseSongRow(row);
                songs.add(song);
            }
        }
        return songs;
    }

    /**
     * Parses the information of a row in the Excel.
     * @param row Row to parse.
     * @return Song object with the information of a song.
     * @throws Exception
     */
    private Song parseSongRow(final Row row) throws Exception {
        final Song song = new Song();
        try {
            final String title = getStringCellValue(row, TITLE_COLUMN);
            final String artist = getStringCellValue(row, ARTIST_COLUMN);
            song.setTitle(title);
            song.setArtist(artist);
            song.setYoutubeURL(getStringCellValue(row, YOUTUBE_URL_COLUMN));
            song.setYear(getStringCellValue(row, YEAR_COLUMN));
            song.setAlbum(getStringCellValue(row, ALBUM_COLUMN));
            song.setStartOfSong(getStringCellValue(row, SONG_START_COLUMN));
            song.setEndOfSong(getStringCellValue(row, SONG_END_COLUMN));
            final String filePath = outputFolder + artist + " - " + title + ".mp3";
            song.setFilePath(filePath);
        } catch (Exception ex) {
            final String message = "Error trying to parse row " + row.getRowNum()  + " from the songs Excel.";
            logger.error(message);
            resultManager.addMessage(message);
            throw ex;
        }
        return song;
    }

    /**
     * Gets the first sheet of an excel file.
     * @param excelFilePath Path to the excel file.
     * @return First sheet of the excel file.
     */
    private XSSFSheet getFirstSheet(final String excelFilePath) {
        final XSSFWorkbook workbook = getWorkbook(excelFilePath);
        return workbook.getSheetAt(0);
    }

    /**
     * Gets a workbook associated to an excel file.
     * @param excelFilePath Path to the excel file.
     * @return Workbook of the excel file.
     */
    public XSSFWorkbook getWorkbook(final String excelFilePath) {
        XSSFWorkbook workbook = null;
        try {
            final File file = ResourceUtils.getFile("classpath:" + excelFilePath);
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
            }
        } catch (Exception ex) {
            final String message = "Error trying to read file " + songsFilePath;
            logger.error(message);
            resultManager.addMessage(message);
        }
        return workbook;
    }

    private String getStringCellValue(final Row row, final int columnNumber) {
        String value = null;
        try {
            DataFormatter formatter = new DataFormatter();
            value = formatter.formatCellValue(row.getCell(columnNumber));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

}
