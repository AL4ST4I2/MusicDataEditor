package com.denis.musicdataeditor.mxmwrapper;


import com.denis.musicdataeditor.config.References;
import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.album.Album;
import org.jmusixmatch.entity.album.AlbumData;
import org.jmusixmatch.entity.artist.Artist;
import org.jmusixmatch.entity.track.Track;

import java.util.ArrayList;
import java.util.List;

public class MXM
{
    private static MXM mxms = new MXM();

    private MusixMatch mxm;

    public MXM()
    {
        this.mxm = new MusixMatch(References.MXM_API_KEY);
    }

    public static List<MXMTrack> searchTrack(String q_artist, String q_title)
    {
        List<MXMTrack> ret = new ArrayList<>();
        List<Track> results;
        try {
            // TODO: il 10 va settatto nei confing situazionalmente per per non ottener troppi hits ai server
            results = mxms.mxm.searchTracks("", q_artist, q_title, 1, References.FILTER_SEARCH, false);
        } catch (MusixMatchException e) {
            e.printStackTrace();
            return null;
        }

        results.forEach(t -> ret.add(new MXMTrack(t)));

        return ret;
    }

    public static Track getMatchingTrack(String artist, String title)
    {
        Track track = null;
        try {
            track = mxms.mxm.getMatchingTrack(title, artist);
        } catch (MusixMatchException e) {
            e.printStackTrace();
        }
        return track;
    }

    public static Track getTrackByID(int trackID)
    {
        Track track = null;
        try {
            track = mxms.mxm.getTrack(trackID);
        } catch (MusixMatchException e) {
            e.printStackTrace();
        }
        return track;
    }

    public static Album getAlbum(int albumId)
    {
        Album album;
        try {
            album = mxms.mxm.getAlbum(albumId);
        } catch (MusixMatchException e) {
            e.printStackTrace();
            return null;
        }
        return album;
    }

    public static Artist getArtist(int artistId)
    {
        Artist artist;
        try {
            artist = mxms.mxm.getArtist(artistId);
        } catch (MusixMatchException e) {
            e.printStackTrace();
            return null;
        }
        return artist;
    }

    public static List<Track> getAlbumTrackList(AlbumData album)
    {
        List<Track> tracks = null;

        try {
            tracks = mxms.mxm.getAlbumTrackList(album.getId(), 1, album.getTrackCount(), false);
        } catch (MusixMatchException e) {
            e.printStackTrace();
        }
        return tracks;
    }

    public static void updateMXM()
    {
        mxms = new MXM();
    }
}
