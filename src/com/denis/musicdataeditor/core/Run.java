package com.denis.musicdataeditor.core;

import com.denis.musicdataeditor.config.References;
import com.denis.musicdataeditor.config.Setup;
import com.denis.musicdataeditor.gui.GuiMusicEditor;
import org.jmusixmatch.MusixMatchException;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class Run {

    public static void main(String[] args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            NoSuchFieldException, MusixMatchException, IOException
    {
        setLookAndFeel("Windows");
        new Setup();
        
        References.setMxmApiKey();
        References.setLibDir();
        References.setFilterSearch();

        SwingUtilities.invokeLater(() -> new GuiMusicEditor().setVisible(true));
    }


    public static void setLookAndFeel(String s)
        {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if (s.equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiMusicEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        }


}
