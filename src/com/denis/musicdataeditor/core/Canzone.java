package com.denis.musicdataeditor.core;


import com.denis.musicdataeditor.config.References;
import com.denis.musicdataeditor.image.CoverArt;
import com.mpatric.mp3agic.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Canzone extends Mp3File{

    // AUTORE means ARTISTA ALBUM
    private Mp3File song;
    private ID3v2   track_tags;
    private final   String song_path;

    public Canzone(String song)
    {
        if (song == null || song.equals("")) throw new IllegalArgumentException("Il path della canzone non è valido");
        song_path = song;

        try
        {
            this.song = new Mp3File(song);
            if (this.song.hasId3v2Tag())
            {
                if (this.song.hasId3v1Tag()) { this.song.removeId3v1Tag();}

                this.track_tags = this.song.getId3v2Tag();
            }

        } catch (IOException|UnsupportedTagException|InvalidDataException e) {
            e.printStackTrace();
        }
    }

    public Mp3File  getSONGFILE()       {return song;}
    public void     setTrack_tags(ID3v2 tags) {track_tags = tags;}
    public ID3v2    getTrack_tags()     {return track_tags;}
    public void     refreshTrack_tags() {track_tags = this.song.getId3v2Tag();}

    // Get Track Components
    public String   getTITOLO()     {return this.track_tags.getTitle();}
    public String   getARTISTA()    {return this.track_tags.getArtist();}
    public String   getALBUM()      {return this.track_tags.getAlbum();}
    public String   getAUTORE()     {return this.track_tags.getAlbumArtist();}
    public String   getNUMERO()     {return this.track_tags.getTrack();}
    public String   getANNO()       {return this.track_tags.getYear();}
    public String   getGENERE()     {return this.track_tags.getGenreDescription();}
    public byte[]   getCOVER()      {return  this.track_tags.getAlbumImage();}
    public String[] getARTISTI()    {return this.track_tags.getArtist().split("/");}
    public String   getSong_path()  {return song_path;}

    public BufferedImage getCOVERasBuffImm() {return CoverArt.byteArrayToBufferedImage(this.getCOVER());}

    // Set Track Components
    public void setTITOLO(String titolo)            {this.track_tags.setTitle(titolo);}
    public void setARTISTA(String artista)          {this.track_tags.setArtist(artista.replace(';', '/'));}
    public void setAUTORE(String autore)            {this.track_tags.setAlbumArtist(autore);}
    public void setALBUM(String album)              {this.track_tags.setAlbum(album);}
    public void setNUMERO(String numero)            {this.track_tags.setTrack(numero);}
    public void setANNO(String anno)                {this.track_tags.setYear(anno);}
    public void setGENERE(String genere)            {this.track_tags.setGenreDescription(genere);}
    public void setCOVER(byte[] cover, String format)
                                                    {this.track_tags.setAlbumImage(cover, format);}

    /* Salva la nuova canzone prima in un file temporaneo salvato con estensione .mp3.bak ; poi elimina il file sorgente
     di partenza in modo da poter poi rinominare il file anche con il nome iniziale qualora titolo o artista non cambino.
     poi rinomina il file temporaneo rimuovendo il ".bak" alla fine del nome. La rinominazione a quanto pare elimina anche
     la sorgente alla chiamata del metodo renameTo(src, dest):
     La tracklist mi serve per poterla aggiornare dopo le modifiche effettuate alle canzoni */
    public void save()
    {
        // Cancello ID3v1 e custom tags dalla canzone
        if (this.song.hasId3v1Tag()) {this.song.removeId3v1Tag();}
        else if (this.song.hasCustomTag()) {this.song.removeCustomTag();}
        // Aggiorna i metatdati della canzone sulla canzone corrente che presto sarà la vecchia
        this.song.setId3v2Tag(getTrack_tags());
        // costruisce il path dove verrà salvata la nuova canzone con i dati aggiornati prima temporanea poi quella definitiva
        // Tenere docchio la nuova distinzione fatta tra la stringa percorso per windows e le stringhe generate per dai metodi
        // equals, toStringName e toString eliminando i caratteri come "?", in quanto queste non saranno uguali. Cmq dovrebbe
        // funzionare senza intoppi in quanto di rado cerco se due canzoni sono uguali
        String new_temp_path = 
                References.LIB_DIR + "/" +
                this.getAUTORE().replace("?", "") + " - " +
                this.getTITOLO().replace("?", "") + ".mp3.bak" ;
        // path versione finale ottenuto togliendo il formato ".bak" finale
        final String final_path = new_temp_path.substring(0, new_temp_path.length()-4);
        // path della canzone originale che verrà cancellata per liberare il nome all nuova canzone
        final String old_path_to_delete = this.song_path;
        // crea la nuova canzone in new_temp_path a partire dalla corrente canzone(che ora diventerà vecchia) con i dati
        // aggiornati di questa
        try {
            this.song.save(new_temp_path);
            System.out.println("Saved as: " + new File(new_temp_path).getName());
        } catch (IOException | NotSupportedException ex) {
            JOptionPane.showMessageDialog(null, "pls select a genre!");
            Logger.getLogger(Canzone.class.getName()).log(Level.SEVERE, null, ex);
        }
        // cancella il file della vecchia canzone per far si che la nuova canzone possa anche avere il nome della versione
        // precendente se necessario: qualora il titolo o l'autore non siano cambiati
        if (!new File(old_path_to_delete).delete()) {
            try {
                throw new Exception("Couldn't delete the original mp3 at: " + old_path_to_delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Semplicemnte rimuove il ".bak" finale e rende eseguibile la canzone
        if (!(new File(new_temp_path)).renameTo(new File(final_path))) {
            try {
                throw new Exception("Couldn't rename the saved file: \"" +
                        new_temp_path + "\" to \"" + final_path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasCoverArt()
    {
        return getCOVER() != null;
    }

    public String toStringName()
    { return getAUTORE() + " - " + getTITOLO();}

    public String toString()
    { return getAUTORE() + " - " + getTITOLO() + " - " + getALBUM(); }

    public boolean equals(Object object)
    {
        if (object == this) return true;
        if (object == null || this.getClass() != object.getClass()) return false;

        Canzone o = (Canzone) object;

        return this.getTITOLO().equals(o.getTITOLO()) &&
                this.getARTISTA().equals(o.getARTISTA()) &&
                this.getALBUM().equals(o.getALBUM()) &&
                this.getAUTORE().equals(o.getAUTORE()) ;
    }


}

