package service;

import config.Global;
import domain.mongo.MongoDAO;

public class ExportPlaylistToMongoDB implements MenuOption{ //class that exports a playlist to MongoDB
    @Override
    public void execute(){ //method that executes the export
        int playlistId; //variable to store the playlist id
       
        do{
            playlistId = Global.enterInt("Enter the playlist id to export it to MongoDB: "); //ask the user for the playlist id
            if(!Global.pDAO.playlistExists(playlistId)){ //check if the playlist exists
                System.out.println("The playlist doesn't exist"); 
                return;//if it doesn't exist, return
            }
        }while(playlistId < 0); //repeat until a valid playlist id is entered
        
        Global.mongoDAO = new MongoDAO(playlistId); //create a new MongoDAO object
         
        Global.mongoDAO.insertSong(Global.pDAO.findPlaylistById(playlistId)); //insert the playlist into MongoDB
        
        System.out.println("Playlist exported successfully"); //and finally, print a success message
        
    }
}