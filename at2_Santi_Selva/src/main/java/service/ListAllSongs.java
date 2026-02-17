package service;

import config.Global;
import data.Song;
import java.util.ArrayList;


public class ListAllSongs implements MenuOption{ //class that lists all songs
    @Override
    public void execute(){ //method that executes the listing
        ArrayList<Song> list = Global.sDAO.listSong(); //get the list of songs from the database
        
        if(!list.isEmpty()) { //check if the list is not empty
            System.out.println("\nSong list"); //print the song list
            for(Song song: list){ //for each song in the list
                System.out.println(song); //print the song
            }
        }else{ //if the list is empty show a message
            System.out.println("There isn't any song");
        }
        
    }
}
