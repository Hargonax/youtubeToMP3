package es.bifacia.ytmp3.service.impl;

import es.bifacia.ytmp3.service.ExecutionResultManager;
import es.bifacia.ytmp3.service.YoutubeToMP3Downloader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;

@Service
public class YoutubeToMP3DownloaderImpl implements YoutubeToMP3Downloader {
    private final Logger logger = LogManager.getLogger(YoutubeToMP3DownloaderImpl.class);

    @Value("${yt.dlp.path}")
    private String ytDlpPath;

    @Autowired
    private ExecutionResultManager resultManager;

    /**
     * Transforms a Youtube video to MP3 and downloads it to the indicated folder.
     * @param youtubeURL URL for the Youtube video.
     * @param outputFile Path where we are downloading the MP3 transformed file.
     */
    public void downloadYoutubeVideoAsMP3(final String youtubeURL, final String outputFile) {
        final ProcessBuilder processBuilder = new ProcessBuilder(
                ytDlpPath,
                "-x",                         // extracts audio
                "--audio-format", "mp3",      // converts it to mp3
                "-o", outputFile,            // name of the file
                youtubeURL
        );
        try {
            Process process = processBuilder.start();
            // Mostrar salida del proceso
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Proceso terminado con código: " + exitCode);
        } catch (Exception ex) {
            final String message = "Error trying to download song " + youtubeURL + ". " + ex.getMessage();
            resultManager.addMessage(message);
            logger.error(message);
        }
    }
}
