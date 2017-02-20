package com.denis.musicdataeditor.config;


import com.denis.musicdataeditor.mxmwrapper.MXMTrack;
import com.google.gson.Gson;
import com.mpatric.mp3agic.ID3v1Genres;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class References {

    public static String MXM_API_KEY;
    public static String LIB_DIR;
    public static int    FILTER_SEARCH;

    public static final String USERHOME    = System.getProperty("user.home").replace("\\", "/") + "/";
    public static final String CONFIG_HOME =
                                OS.getOS() == OS.WIN ?
                                    USERHOME + "AppData/Roaming/MusicDataEditor" : USERHOME + "Music/MusicDataEditor/config";
    public static final String CONFIG      = CONFIG_HOME + "/config.json";

    public final static String RES_DIR   = System.getProperty("user.dir").replace('\\', '/') + "/res/";

    public final static int SCREEN_WIDTH_SIZE = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public final static int SCREEN_HEIGHT_SIZE = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public static void setMxmApiKey()
    {
        MXM_API_KEY = getPOJO().getMxm_api_key();
    }

    public static void setLibDir()
    {
        LIB_DIR = getPOJO().getLib_dir();
    }

    public static void setFilterSearch()
    {
        FILTER_SEARCH = getPOJO().getFilter_search();
    }

    private static ConfigPOJO getPOJO()
    {
        Gson gson = new Gson();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(CONFIG));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ConfigPOJO c = gson.fromJson(br, ConfigPOJO.class);
        return c;
    }

    public static void writeConfigFile(String key, String dir, int numSearch)
    {
        Gson gson = new Gson();
        ConfigPOJO pojo = new ConfigPOJO();

        pojo.setMxm_api_key(key);
        pojo.setLib_dir(dir);
        pojo.setFilter_search(numSearch);

        String json = gson.toJson(pojo);

        if (new File(CONFIG).exists()) {
            if (!new File(CONFIG).delete()) {
                System.err.println("Ohi i've failed to delete older file");
            }
        }
        try {
            FileWriter writer = new FileWriter(References.CONFIG);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed creating config.json", "Critical Error!" , JOptionPane.ERROR_MESSAGE);
            System.err.println("Failed to open: " + References.CONFIG + " error was: " + e.getLocalizedMessage());
            System.exit(-1);
        }


    }

    public final static String[] GENRES_LIST = garrtoglist(ID3v1Genres.GENRES);

    public static String[] garrtoglist(String[] in){
        ArrayList<String> inAl = new ArrayList<>(Arrays.asList(in));
        Collections.sort(inAl);
        return inAl.toArray(new String[inAl.size()]);
    }

    public static String toTitleCase(String input)
    {
        if (input.length() == 0) return "";
        String[] arr = input.split(" ");
        StringBuilder fin = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            fin.append(arr[i].substring(0,1).toUpperCase()).append(arr[i].substring(1, arr[i].length()));
            if (i != arr.length -1)
            {
                fin.append(" ");
            }
        }
        return fin.toString();
    }

    public static String[] splitArrayForInput(String in)
    {
        return in.split(" - ");
    }

    public static Object[] normalizeArtistTranslation(MXMTrack track)
    {
        List<String> transl = new ArrayList<>(track.getArtistTranslations().size());

        track.getArtistTranslations().forEach(t -> transl.add(t.getTranslation()));

        return (Object[]) transl.toArray();

    }

    public static Object[] normalizeTitleTranslation(MXMTrack track)
    {
        List<String> transl = new ArrayList<>(track.getTitleTranslations().size());

        track.getTitleTranslations().forEach(t -> transl.add(t.getTranslation()));

        return (Object[]) transl.toArray();

    }

    public static String[] normalizeTrackGenres(MXMTrack track)
    {
        String[] unk = new String[] {"Unknown"};
        if (track.getGenres().size() ==  0) return unk;
        String[] rit = new String[track.getGenres().size()];
        for (int i = 0; i < rit.length; i++) {
            rit[i] = track.getGenres().get(i).getGenreName();
        }
        return rit;
    }
    
    public static enum OS {
        WIN, LINUX, MAC, NULL;
        
        public static OS getOS(){
            String so = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
            if (so.indexOf("mac") >= 0 || so.indexOf("darwin") >= 0) return OS.MAC;
            else if (so.indexOf("win") >= 0) return OS.WIN;
            else if (so.indexOf("nux") >= 0) return OS.LINUX;
            return OS.NULL;
        }
    }
}
