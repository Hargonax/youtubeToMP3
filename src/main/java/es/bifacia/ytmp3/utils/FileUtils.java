package es.bifacia.ytmp3.utils;

import java.io.File;
import java.io.PrintWriter;

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

    /**
     * Creates a file from a String content.
     * @param content Content to save in the file.
     * @param outputFilePath Path for the file.
     * @throws Exception
     */
    public static void stringToFile(final String content, final String outputFilePath) throws Exception {
        try (final PrintWriter out = new PrintWriter(outputFilePath)) {
            out.println(content);
        }
    }
}
