package com.denis.musicdataeditor.mxmwrapper;


import com.denis.musicdataeditor.config.References;
import com.myjmxm.core.MusixMatch;
import com.myjmxm.core.MusixMatchException;
import com.myjmxm.entity.album.Album;
import com.myjmxm.entity.artist.Artist;
import com.myjmxm.entity.track.Track;
import com.myjmxm.entity.track.TrackList;


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
        List<MXMTrack>  ret = new ArrayList<>();
        List<TrackList> results;
        try {
            results = mxms.mxm.searchTracks("", q_artist, q_title, 1, References.FILTER_SEARCH, false);
        } catch (MusixMatchException e) {
            e.printStackTrace();
            return null;
        }

        results.forEach(t -> ret.add(new MXMTrack(t.getTrack())));

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

    public static List<TrackList> getAlbumTrackList(Album album)
    {
        List<TrackList> tracks = null;

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
