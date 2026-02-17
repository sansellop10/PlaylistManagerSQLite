package service;

import config.Global;


public class RemovePlaylist implements MenuOption{ //class that removes a playlist from the relational database
    @Override
    public void execute(){  //method that executes the removal
        int playlistId; //variable to store the playlist id
        
        do{
            playlistId = Global.enterInt("\nEnter the playlist id: "); //ask the user for the playlist id
            if(!Global.pDAO.playlistExists(playlistId)){ //check if the playlist exists
                System.out.println("This playlist doesn't exist"); //if it doesn't exist, show a message
                return; // exit the method
            }
        }while(playlistId < 0); //repeat until a valid playlist id is entered
        Global.pDAO.removePlaylist(playlistId); //remove the playlist from the database
        
        System.out.println("Playlist successfully removed"); //and show a success message
    }
    
}
