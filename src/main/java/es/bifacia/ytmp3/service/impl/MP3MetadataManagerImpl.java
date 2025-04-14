package es.bifacia.ytmp3.service.impl;

import com.mpatric.mp3agic.*;
import es.bifacia.ytmp3.model.Song;
import es.bifacia.ytmp3.service.ExecutionResultManager;
import es.bifacia.ytmp3.service.MP3MetadataManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MP3MetadataManagerImpl implements MP3MetadataManager {
    private final Logger logger = LogManager.getLogger(MP3MetadataManagerImpl.class);

    @Autowired
    private ExecutionResultManager resultManager;

    public MP3MetadataManagerImpl() {
        super();
    }

    /**
     * Updates the MP3 metadata with the information we have of the song.
     * @param song Song to update.
     */
    public void updateID3V1MP3Metadata(final Song song) {
        try {
            if (StringUtils.isEmpty(song.getFilePath())) {
                final String message = "We couldn't update MP3 " + song.getTitle() + " metadata because there is no path indicated for the song.";
                logger.warn(message);
                resultManager.addMessage(message);
            } else {
                final Mp3File mp3file = new Mp3File(song.getFilePath());
                ID3v1 id3v1Tag;
                if (mp3file.hasId3v1Tag()) {
                    id3v1Tag =  mp3file.getId3v1Tag();
                } else {
                    // mp3 does not have an ID3v1 tag, let's create one..
                    id3v1Tag = new ID3v1Tag();
                    mp3file.setId3v1Tag(id3v1Tag);
                }
                id3v1Tag.setTitle(song.getTitle());
                if (!StringUtils.isEmpty(song.getArtist())) {
                    id3v1Tag.setArtist(song.getArtist());
                }
                if (!StringUtils.isEmpty(song.getAlbum())) {
                    id3v1Tag.setAlbum(song.getAlbum());
                }
                if (!StringUtils.isEmpty(song.getYear())) {
                    id3v1Tag.setYear(song.getYear());
                }
                final String auxFilePath = song.getFilePath() + "mp3agic";
                mp3file.save(auxFilePath);
                new File(song.getFilePath()).delete();
                Path source = Paths.get(auxFilePath);
                Files.move(source, source.resolveSibling(song.getArtist() + " - " + song.getTitle() + ".mp3"));
            }
        } catch (Exception ex) {
            final String message = "Error updating " + song.getTitle() + ". " + ex.getMessage();
            logger.error(message);
            resultManager.addMessage(message);
        }
    }

    /**
     * Updates the MP3 metadata with the information we have of the song.
     * @param song Song to update.
     */
    public void updateMP3Metadata(final Song song) {
        try {
            if (StringUtils.isEmpty(song.getFilePath())) {
                final String message = "We couldn't update MP3 " + song.getTitle() + " metadata because there is no path indicated for the song.";
                logger.warn(message);
                resultManager.addMessage(message);
            } else {
                final Mp3File mp3file = new Mp3File(song.getFilePath());
                ID3v2 id3v2Tag;
                if (mp3file.hasId3v1Tag()) {
                    id3v2Tag =  mp3file.getId3v2Tag();
                } else {
                    // mp3 does not have an ID3v1 tag, let's create one..
                    id3v2Tag = new ID3v24Tag();
                    mp3file.setId3v1Tag(id3v2Tag);
                }
                id3v2Tag.setTitle(song.getTitle());
                if (!StringUtils.isEmpty(song.getArtist())) {
                    id3v2Tag.setArtist(song.getArtist());
                }
                if (!StringUtils.isEmpty(song.getAlbum())) {
                    id3v2Tag.setAlbum(song.getAlbum());
                }
                if (!StringUtils.isEmpty(song.getYear())) {
                    id3v2Tag.setYear(song.getYear());
                }
                final String auxFilePath = song.getFilePath() + "mp3agic";
                mp3file.save(auxFilePath);
                new File(song.getFilePath()).delete();
                Path source = Paths.get(auxFilePath);
                Files.move(source, source.resolveSibling(song.getArtist() + " - " + song.getTitle() + ".mp3"));
            }
        } catch (Exception ex) {
            final String message = "Error updating " + song.getTitle() + ". " + ex.getMessage();
            logger.error(message);
            resultManager.addMessage(message);
        }
    }
}
