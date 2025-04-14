package es.bifacia.ytmp3.service.impl;

import es.bifacia.ytmp3.service.ExecutionResultManager;
import es.bifacia.ytmp3.service.MP3Manager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MP3ManagerImpl implements MP3Manager {
    private final Logger logger = LogManager.getLogger(MP3ManagerImpl.class);

    @Value("${temp.file}")
    private String tempFile;

    @Autowired
    private ExecutionResultManager resultManager;

    public MP3ManagerImpl() {
        super();
    }

    /**
     * Trims the duration of an MP3.
     * @param filePath Path of the file to trim.
     * @param startOfTrim Start in the original song for the new MP3.
     * @param endOfTrim End in the original song for the new MP3.
     */
    public void trimSong(final String filePath, String startOfTrim, final String endOfTrim) {
        try {
            if (StringUtils.isEmpty(startOfTrim)) {
                startOfTrim = "00:00:00";
            }
            ProcessBuilder builder = new ProcessBuilder(
                    "ffmpeg",
                    "-i", filePath,
                    "-ss", startOfTrim,       // comienzo del recorte (30s)
                    "-to", endOfTrim,         // fin del recorte (1m)
                    "-c", "copy",             // sin recompresión
                    tempFile
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();

            // Leer salida del proceso
            try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                int exitCode = process.waitFor();
                System.out.println("Proceso terminado con código: " + exitCode);
                final File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                final String songName = filePath.substring(filePath.lastIndexOf("/") + 1);
                Path source = Paths.get(tempFile);
                Files.move(source, source.resolveSibling(songName));
            }
        } catch (Exception ex) {
            final String message = "Error trying to trim song " + filePath + ". " + ex.getMessage();
            resultManager.addMessage(message);
            logger.error(message);
        } finally {
            final File file = new File(tempFile);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
