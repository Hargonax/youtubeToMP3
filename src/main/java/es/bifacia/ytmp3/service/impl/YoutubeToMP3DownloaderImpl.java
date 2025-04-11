package es.bifacia.ytmp3.service.impl;

import es.bifacia.ytmp3.service.YoutubeToMP3Downloader;
import org.springframework.stereotype.Service;

import java.io.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
public class YoutubeToMP3DownloaderImpl implements YoutubeToMP3Downloader {
    private static final String YT_DLP_PATH = "/Users/alejandro.calle/.local/pipx/venvs/yt-dlp/bin/yt-dlp";

    /**
     * Transforms a Youtube video to MP3 and downloads it to the indicated folder.
     * @param youtubeURL URL for the Youtube video.
     * @param outputFile Path where we are downloading the MP3 transformed file.
     */
    public void downloadYoutubeVideoAsMP3(final String youtubeURL, final String outputFile) {
        String outputTemplate = "output/%(title)s.%(ext)s"; // Guarda en carpeta 'output/'

        final ProcessBuilder processBuilder = new ProcessBuilder(
                YT_DLP_PATH,
                "-x",                         // extracts audio
                "--audio-format", "mp3",     // converts it to mp3
                "-o", outputFile,        // name of the file
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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
