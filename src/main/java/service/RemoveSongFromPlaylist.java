package service;

import config.Global;

public class RemoveSongFromPlaylist implements MenuOption{ //class that removes a song from a playlist in the relational database
    @Override
    public void execute(){ //method that executes the removal
        int playlistId; //variable to store the playlist id
        int songId; //variable to store the song id
       
        do{
          
            playlistId = Global.enterInt("\nEnter the playlist id where the song is located: "); //ask the user for the playlist id
            if(!Global.pDAO.playlistExists(playlistId)){ //check if the playlist exists
                System.out.println("This playlist doesn't exist"); //if it doesn't exist, show a message
                return; // exit the method
            } 
        }while(playlistId < 0); //repeat until a valid playlist id is entered
        
        do{
            songId = Global.enterInt("Enter the song id: "); //ask the user for the song id
            if(!Global.sDAO.songExists(songId)){ //check if the song exists
                System.out.println("This song doesn't exist"); //if it doesn't exist, show a message
                return; // exit the method
            }
        }while(songId < 0); //repeat until a valid song id is entered
        
        removeSong(playlistId, songId); //call the method that removes the song from the playlist
    }
    
    public void removeSong(int playlistId, int songId){ //method that removes the song from the playlist
        Global.pDAO.removeSong(playlistId, songId);     //remove the song from the playlist in the database
        System.out.println("Song successfully removed from her playlist"); //and show a success message
    }
}
