package service;

import config.Global;


public class RemoveSong implements MenuOption{ //class that removes a song from the relational database
    public void execute(){ //method that executes the removal 
        int songId; //variable to store the song id
        
        do{
            songId = Global.enterInt("\nEnter the song id: "); //ask the user for the song id
            if(!Global.sDAO.songExists(songId)){ //check if the song exists
                System.out.println("This song doesn't exist"); //if it doesn't exist, show a message
                return; // exit the method
            } 
        }while(songId < 0); //repeat until a valid song id is entered
        
        Global.sDAO.removeSongInDataBase(songId); //remove the song from the database
        
        System.out.println("Song successfully removed"); //and show a success message
    }
}
