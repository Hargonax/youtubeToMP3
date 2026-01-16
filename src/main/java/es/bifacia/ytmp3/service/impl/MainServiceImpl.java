package es.bifacia.ytmp3.service.impl;

import es.bifacia.ytmp3.model.Song;
import es.bifacia.ytmp3.service.*;
import es.bifacia.ytmp3.service.excel.ExcelService;
import es.bifacia.ytmp3.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private CSVParser csvParser;

    @Autowired
    private MP3Manager mp3Manager;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private YoutubeToMP3Downloader mp3Downloader;

    @Autowired
    private MP3MetadataManager metadataManager;

    @Autowired
    private ExecutionResultManager resultManager;

    public MainServiceImpl() {
        super();
    }

    /**
     * Runs the application.
     * First gets the list of songs from the Excel file.
     * Then, it does the same process for every song: checks if the song file already exists, transforms and downloads it from the Youtube video and finally it updates the metadata of the file with the information provided in the Excel file.
     * @throws Exception
     */
    public void runApplication() throws Exception {
        try {
            final List<Song> songs = csvParser.getSongs();
//            final List<Song> songs = excelService.getSongs();
            if (songs == null || songs.isEmpty()) {
                final String message = "No songs were retrieved from the Excel page.";
                resultManager.addMessage(message);
            }
            assert songs != null;
            songs.forEach(s -> {
                if (!FileUtils.fileExists(s.getFilePath())) {
                    if (!StringUtils.isEmpty(s.getYoutubeURL())) {
                        mp3Downloader.downloadYoutubeVideoAsMP3(s.getYoutubeURL(), s.getFilePath());
                        if (!StringUtils.isEmpty(s.getEndOfSong())) {
                            mp3Manager.trimSong(s.getFilePath(), s.getStartOfSong(), s.getEndOfSong());
                        }
                    } else {
                        final String message = "No Youtube URL for song " + s.getTitle() + ".";
                        resultManager.addMessage(message);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        
                    }
                }
//                metadataManager.updateID3V1MP3Metadata(s);
            });
        } finally {
            resultManager.createExecutionResultFile();
        }
    }

}
