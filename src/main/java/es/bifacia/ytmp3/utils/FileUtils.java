package es.bifacia.ytmp3.utils;

import java.io.File;

public abstract class FileUtils {

    public FileUtils() {
        super();
    }

    /**
     * Checks if a song file already exists.
     * @param filePath Path to the file.
     * @return True if the file exists.
     */
    public static boolean fileExists(final String filePath) {
        final File file = new File(filePath);
        return file.exists();
    }
}
