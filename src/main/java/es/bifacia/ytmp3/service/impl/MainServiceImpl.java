package es.bifacia.ytmp3.service.impl;

import es.bifacia.ytmp3.model.Song;
import es.bifacia.ytmp3.service.ExecutionResultManager;
import es.bifacia.ytmp3.service.MP3MetadataManager;
import es.bifacia.ytmp3.service.MainService;
import es.bifacia.ytmp3.service.YoutubeToMP3Downloader;
import es.bifacia.ytmp3.service.excel.ExcelService;
import es.bifacia.ytmp3.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {

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
            final List<Song> songs = excelService.getSongs();
            if (songs == null || songs.isEmpty()) {
                final String message = "No songs were retrieved from the Excel page.";
                resultManager.addMessage(message);
            }
            songs.forEach(s -> {
                if (!FileUtils.fileExists(s.getFilePath())) {
                    if (!StringUtils.isEmpty(s.getYoutubeURL())) {
                        mp3Downloader.downloadYoutubeVideoAsMP3(s.getYoutubeURL(), s.getFilePath());
                    } else {
                        final String message = "No Youtube URL for song " + s.getTitle() + ".";
                        resultManager.addMessage(message);
                    }
                }
                metadataManager.updateMP3Metadata(s);
            });
        } finally {
            resultManager.createExecutionResultFile();
        }
    }

}
