package com.denis.musicdataeditor.config;


import javax.swing.*;
import java.io.File;

public class Setup
{

    public final String INITIAL_LIB_DIR = References.USERHOME.concat("Music/MusicDataEditor");

    public Setup()
    {
        if (!alreadyInstalled()) {
            createFirstConfigFile();
            createMusicLibrary();
        }
    }

    private boolean alreadyInstalled()
    {
        return new File(References.CONFIG).exists();
    }

    private void createConfiDir()
    {
        File file = new File(References.CONFIG_HOME);

        if (file.mkdir()) {
            JOptionPane.showMessageDialog(null, "Config Folder created at  path: " + References.CONFIG_HOME
                    , "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createFirstConfigFile()
    {
        createConfiDir();
        References.writeConfigFile(
                        "",
                        INITIAL_LIB_DIR,
                        10
        );
    }

    private void createMusicLibrary()
    {
        File f = new File(INITIAL_LIB_DIR);
        if (f.mkdir()) {
            JOptionPane.showMessageDialog(null, "Created folder at: " + INITIAL_LIB_DIR + "to store your lib",
                                          "Alert", JOptionPane.ERROR_MESSAGE);
        }

    }
}
