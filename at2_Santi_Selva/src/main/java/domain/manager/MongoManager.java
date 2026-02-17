package domain.manager;

import com.mongodb.*;
import com.mongodb.client.*;



public class MongoManager { //class that manages the MongoDB connection
    static String url = "mongodb://localhost:27017"; //MongoDB URL
    static String database = "playlistDB"; //database name
    
    static MongoClient mongoClient = MongoClients.create(url); //MongoDB client
    
    
    public static MongoDatabase getDatabase(){ //method to get the database
        try{
            return mongoClient.getDatabase(database);
        }catch(MongoException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
