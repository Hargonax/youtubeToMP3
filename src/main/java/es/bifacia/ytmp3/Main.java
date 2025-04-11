package es.bifacia.ytmp3;

import es.bifacia.ytmp3.service.impl.YoutubeToMP3DownloaderImpl;

public class Main {

    public static void main(String[] args) {
        final YoutubeToMP3DownloaderImpl converter = new YoutubeToMP3DownloaderImpl();
        converter.downloadYoutubeVideoAsMP3("https://www.youtube.com/watch?v=qxTpvA-pUG0", "/Users/alejandro.calle/projects/test/workspace/youtubeToMP3/outputFiles/loreena.mp3");
    }

}
