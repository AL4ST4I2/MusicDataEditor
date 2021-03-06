package com.denis.musicdataeditor.mxmwrapper;




import com.myjmxm.entity.album.Album;
import com.myjmxm.entity.artist.Artist;
import com.myjmxm.entity.genre.MusicGenre;
import com.myjmxm.entity.genre.MusicGenreList;
import com.myjmxm.entity.track.Track;
import com.myjmxm.entity.track.TrackList;
import com.myjmxm.entity.translation.ArtistNameTranslationList;
import com.myjmxm.entity.translation.TrackNameTranslationList;
import com.myjmxm.entity.translation.Translation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MXMTrack {

    public MXMTrack(Track traccia)
    {
        Track  track  = traccia;
        Album  album  = MXM.getAlbum(track.getAlbumId());
        Artist artist = MXM.getArtist(track.getArtistId());

        title               = track.getTrackName();
        trackID             = track.getTrackId();
        titleTranslations   = takeTitleTranslations(track);
        releaseDate         = album.getReleaseDate();
        coverart350x350     = immToURL(track.getAlbumCoverart350x350());
        coverart100x100     = immToURL(track.getAlbumCoverart100x100());
        albumCover350x350   = immToURL(album.getCoverart350x350());

        this.artist         = track.getArtistName();
        artistID            = track.getArtistId();
        artistTranslations  = takeArtistTransalations(artist);

        this.album          = track.getAlbumName();
        this.albumID        = track.getAlbumId();
        this.albumType      = album.getType();
        this.noAlbumTracks  = album.getTrackCount();

        this.genres         = takeGenreList(track);

        this.trackNumber    = retrieveTrackNumber(album);
    }

    private int retrieveTrackNumber(Album album)  {
        List<TrackList> tl = MXM.getAlbumTrackList(album);

        if (tl == null) {
            return -1;
        }
        for (TrackList track : tl) {
            if (track.getTrack().getTrackId() == trackID &&
                    track.getTrack().getTrackName().equals(title))
            {
                return tl.indexOf(track) + 1;
            }
        }
        return -1;
    }

    private ArrayList<Translation> takeTitleTranslations(Track track) {
        ArrayList<Translation>         ritorno         = new ArrayList<>();
        List<TrackNameTranslationList> translationList = track.getTranslationNameList();

        //System.out.println("DEBUG PURPOSE ONLY: SIZE OF TRACK NAME TRANSLATION LIST = " + translationList.size());

        translationList.forEach(trackNameTranslationList ->
                ritorno.add(trackNameTranslationList.getTranslation()));

        return ritorno;
    }
    private ArrayList<Translation> takeArtistTransalations(Artist artist) {
        ArrayList<Translation>          ritorno         = new ArrayList<>();
        List<ArtistNameTranslationList> translationList = artist.getTranslationNameList();

        //System.out.println("DEBUG PURPOSE ONLY: SIZE OF ARTIST NAME TRANSLATION LIST = " + translationList.size());

        translationList.forEach(t -> ritorno.add(t.getTranslation()));

        return ritorno;
    }

    private ArrayList<MusicGenre> takeGenreList(Track track){
        ArrayList<MusicGenre> ritorno = new ArrayList<>();

        List<MusicGenreList> list = track.getPrimaryGenres().getMusicGenreList();

        list.forEach(musicGenreList -> ritorno.add(musicGenreList.getMusicGenre()));

        return ritorno;
    }

    private URL immToURL(String imm)
    {
        URL url = null;
        try {
            url = new URL(imm);
        } catch (MalformedURLException e) {
            System.err.printf("*** Cannot reach URL:%s ***\nError Message: %s\n",
                              imm, e.getMessage());

        }
        return url;
    }


    //// ***** G E T T E R S     M E T H O D S ***** ////

    public String   getTitle()              {return title;}
    public int      getTrackID()            {return trackID;}
    public String   getReleaseDate()        {return releaseDate;}
    public int      getTrackNumber()        {return trackNumber;}
    public URL      getCoverart350x350()    {return coverart350x350;}
    public URL      getCoverart100x100()    {return coverart100x100;}
    public URL      getAlbumCover350x350()  {return albumCover350x350;}

    public String   getArtist()             {return artist;}
    public int      getArtistID()           {return artistID;}
    public String   getAlbum()              {return album;}
    public int      getAlbumID()            {return albumID;}
    public String   getAlbumType()          {return albumType;}
    public int      getNoAlbumTracks()      {return noAlbumTracks;}

    public ArrayList<MusicGenre>    getGenres()             {return genres;}
    public ArrayList<Translation>   getTitleTranslations()  {return titleTranslations;}
    public ArrayList<Translation>   getArtistTranslations() {return artistTranslations;}

    // ***** F I E L D S ***** \\

    private String g;

    private String                  title;
    private int                     trackID;
    private String                  releaseDate;
    private int                     trackNumber;
    private URL                     coverart350x350;
    private URL                     coverart100x100;
    private URL                     albumCover350x350;
    private ArrayList<Translation>  titleTranslations;

    private String                  artist;
    private int                     artistID;
    private ArrayList<Translation>  artistTranslations;

    private String                  album;
    private int                     albumID;
    private String                  albumType;
    private int                     noAlbumTracks;

    private ArrayList<MusicGenre>   genres;


}
