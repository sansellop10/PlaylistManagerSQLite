package service;

import config.Global;
import data.Playlist;
import java.util.ArrayList;

public class ListAllSongsInPlaylist implements MenuOption{ //class that lists all songs in a playlist
    @Override
    public void execute(){ //method that executes the listing
        int playlistId; //variable to store the playlist id
        do{
            playlistId = Global.enterInt("\nEnter the playlist id: "); //ask the user for the playlist id
            
        }while(playlistId < 0); //repeat until a valid playlist id is entered
        
        if(Global.pDAO.playlistExists(playlistId)){ //check if the playlist exists
            showSongs(playlistId); //show the songs in the playlist
        }else{
            System.out.println("The playlist doesn't exist"); //if it doesn't exist, show a message
        }
        
    }
    
    public void showSongs(int playlistId){ //method that shows the songs in the playlist
        ArrayList<Playlist>list = Global.pDAO.listPlaylist(playlistId); //get the list of songs in the playlist from the database
        
        if(!list.isEmpty()){ //check if the list is not empty
            for(Playlist playlist: list){ //for each song in the list
                showInformation(playlist); //show the song information
            }
        }else{ //if the list is empty show a message
            System.out.println("The playlist is empty");
        }
        
    }
    
    public void showInformation(Playlist playlist){ //method that shows the song information
        int songId = playlist.getIdSong(); //get the song id from the playlist
        System.out.println(Global.sDAO.findSongById(songId)); //print the song information
    }
}