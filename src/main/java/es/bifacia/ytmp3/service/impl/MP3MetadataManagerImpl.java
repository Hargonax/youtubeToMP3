package es.bifacia.ytmp3.service.impl;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.Mp3File;
import es.bifacia.ytmp3.model.Song;
import es.bifacia.ytmp3.service.ExecutionResultManager;
import es.bifacia.ytmp3.service.MP3MetadataManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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
    public void updateMP3Metadata(final Song song) {
        try {
            if (!StringUtils.isEmpty(song.getFilePath())) {
                final String message = "We couldn't update MP3 " + song.getName() + " metadata because there is no path indicated for the song.";
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
                id3v1Tag.setTitle(song.getName());
                if (!StringUtils.isEmpty(song.getArtist())) {
                    id3v1Tag.setArtist(song.getArtist());
                }
                if (!StringUtils.isEmpty(song.getAlbum())) {
                    id3v1Tag.setAlbum(song.getAlbum());
                }
                if (!StringUtils.isEmpty(song.getYear())) {
                    id3v1Tag.setYear(song.getYear());
                }
                mp3file.save(song.getFilePath());
            }
        } catch (Exception ex) {
            final String message = "Error updating " + song.getName() + ". " + ex.getMessage();
            logger.error(message);
            resultManager.addMessage(message);
        }
    }
}
