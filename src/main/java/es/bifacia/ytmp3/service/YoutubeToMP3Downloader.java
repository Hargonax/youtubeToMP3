package es.bifacia.ytmp3.service;

public interface YoutubeToMP3Downloader {

    /**
     * Transforms a Youtube video to MP3 and downloads it to the indicated folder.
     * @param youtubeURL URL for the Youtube video.
     * @param outputFile Path where we are downloading the MP3 transformed file.
     */
    void downloadYoutubeVideoAsMP3(final String youtubeURL, final String outputFile);

}
