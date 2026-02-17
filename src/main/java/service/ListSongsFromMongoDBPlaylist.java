package service;

import config.Global;
import domain.mongo.MongoDAO;

public class ListSongsFromMongoDBPlaylist implements MenuOption{ //class that lists all songs in a MongoDB playlist
    @Override
    public void execute(){ //method that executes the listing
        int playlistId; //variable to store the playlist id
        do{ 
            playlistId = Global.enterInt("Enter the playlist id to display the playlist songs: "); //ask the user for the playlist id
            Global.mongoDAO = new MongoDAO(playlistId); //initialize the MongoDAO with the playlist id
            if(!Global.mongoDAO.collectionExists(playlistId)){ //check if the collection exists
                System.out.println("This playlist doesn't exist"); //if it doesn't exist, show a message
                return;// exit the method
            }
        }while(playlistId < 0); //repeat until a valid playlist id is entered
        
        Global.mongoDAO.displaySong(); //display the songs in the playlist
        
    }
}