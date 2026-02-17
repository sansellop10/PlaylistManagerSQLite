
package service;

import config.Global;
import domain.mongo.MongoDAO;


public class RemoveCollection implements MenuOption{ //class that removes a MongoDB playlist collection
    @Override
    public void execute(){ //method that executes the removal
        int playlistId; //variable to store the playlist id
        
        do{
            playlistId = Global.enterInt("Enter the playlist id: "); //ask the user for the playlist id
            Global.mongoDAO = new MongoDAO(playlistId); //initialize the MongoDAO with the playlist id
            if(!Global.mongoDAO.collectionExists(playlistId)){ //check if the collection exists
                System.out.println("The playlist collection doesn't exist"); //if it doesn't exist, show a message
                return;// exit the method
            }
            
        }while(playlistId < 0); //repeat until a valid playlist id is entered
        
        Global.mongoDAO.removeCollection(playlistId); //remove the collection from MongoDB
        System.out.println("Playlist successfully removed"); //and show a success message
    }
}
