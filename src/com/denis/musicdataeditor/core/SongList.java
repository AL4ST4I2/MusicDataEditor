package com.denis.musicdataeditor.core;


import java.io.File;
import java.util.*;

public class SongList implements Iterable<Canzone>, Comparator<Canzone> {

    public ArrayList<Canzone> libreria = new ArrayList<>();
    
    private final String directoryPath;

    public SongList(String dirPath) throws  NullPointerException
    {
        File input_dir = new File(dirPath);
        directoryPath = dirPath;
        
        File[] input_list = input_dir.listFiles();
        for (File file : input_list) {
            if (file.getName().endsWith(".mp3")) {
                libreria.add(new Canzone(file.getAbsolutePath()));
            }
        }
    }

    public ArrayList<Canzone> getLibreria(){return libreria;}

    public void updateTrackList()
    {
        this.libreria = new SongList(directoryPath).getLibreria();
    }
    
    public void HopefulyPerformantUpdateTacklist(Canzone toRemove, Canzone toAdd)
    {
        new File(toRemove.getSong_path()).delete();
        remove(toRemove);

        add(toAdd);
    }
    
    public int size() {
        return libreria.size();
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public Iterator<Canzone> iterator() {
        return new Iterator<Canzone>() {
            public int i = 0;

            @Override
            public boolean hasNext() {
                return i < SongList.this.size();
            }

            @Override
            public Canzone next() {
                if (!hasNext()) throw new NoSuchElementException();
                Canzone temp = SongList.this.libreria.get(i);
                i++;
                return temp;
            }
        };
    }

    public Canzone[] toArray() {
        /*Canzone[] aux = new Canzone[libreria.size()];
        for (int i = 0; i < aux.length; i++) {
            aux[i] = libreria.get(i);
        } */
        return libreria.toArray(new Canzone[libreria.size()]);
    }

    // Sarebbe meglio da ricontrollare!
    public boolean add(Canzone canzone) {
        if (canzone == null) throw new NullPointerException();
        if (canzone.getClass() != Canzone.class) throw new ClassCastException();
        try
        {
            libreria.add(canzone);
        } catch (NullPointerException | ClassCastException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean remove(Canzone canzone)
    {
        if (canzone == null) throw new NullPointerException();
        if (canzone.getClass() != Canzone.class) throw new ClassCastException();
        try
        {
            libreria.remove(canzone);
        } catch (NullPointerException | ClassCastException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void clear() {
        libreria.clear();
    }

    public Canzone get(int index) {
        return libreria.get(index);
    }

    public Canzone set(int index, Canzone element) {
        return libreria.set(index, element);
    }

    public void add(int index, Canzone element) { libreria.add(index, element); }

    public Canzone remove(int index) {
        return libreria.remove(index);
    }

    public int indexOf(String titolo )
    {
        return indexOfRicorsivo(titolo, 0);
    }
    public int indexOfRicorsivo(String titolo, int index)
    {
        if (getLibreria().get(index).getTITOLO().equals(titolo))
        {
            return index;
        }
        else
        {
            indexOfRicorsivo(titolo, index+1);
        }
        return -1;
    }

    public String[] toNamesArray()
    {
        String[] aux = new String[size()];
        for (int i = 0; i < size(); i++) {
            aux[i] = get(i).toStringName();

        }
        return aux;
    }
    
    public void debugSongList()
    {
        System.out.println("/////////// I N I Z I O    D E B U G /////////// ");
        int i = 0;
        for (Canzone song : getLibreria())
        {
            System.out.println(i++ + " " + song.toString());
        }
        System.out.println("/////////// F I N E    D E B U G /////////// ");
    }

    @Override
    public int compare(Canzone o1, Canzone o2) {
        return o1.toString().compareTo(o2.toString());
    }
}
