package domain.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.Global;
import data.Playlist;
import data.Song;
import domain.manager.MongoManager;

import org.bson.Document;

public class MongoDAO { //class that manages the MongoDB operations for playlists

    MongoCollection<Document> collection; //MongoDB collection for the playlist

    public MongoDAO(int playlistId) { //constructor that initializes the collection based on the playlist ID
        collection = MongoManager.getDatabase().getCollection(playlistId + "_playlist");
    }
    
    public void insertSong(Playlist playlist) { //method to insert a song into the playlist collection
       
        collection.deleteOne(new Document("id_p", playlist.getId())); //delete existing document with the same playlist ID to avoid duplicates
        
        Song song = Global.sDAO.findSongById(playlist.getIdSong()); //create a song class
        Document songDoc = new Document("id", song.getId()) //create a document for the song
                .append("title", song.getTitle())
                .append("artist", song.getArtist())
                .append("album", song.getAlbum())
                .append("genre", song.getGenre())
                .append("duration", song.getDuration());

        Document doc = new Document("id_p", playlist.getId()) //and a document for the playlist
                .append("song", songDoc)
                .append("order_p", playlist.getOrder());
        collection.insertOne(doc); //insert the document into the collection
    }

    public void displaySong() {//method to display all songs in the playlist collection

        FindIterable<Document> docs = collection.find(); //get all documents in the collection
        for (Document doc : docs) { //loop through the documents and display their information
            Document song = (Document) doc.get("song"); //get the song document
            System.out.println("\nPLAYLIST ID: " + doc.getInteger("id_p")); //display playlist ID
            System.out.println("Song information:"); 
            
            System.out.println(" -id: "+ song.getInteger("id")); //display song information
            System.out.println(" -title: "+ song.getString("title")); //display song information
            System.out.println(" -artist: "+song.getString("artist")); //display song information
            System.out.println(" -genre: "+song.getString("genre")); //display song information
            System.out.println(" -duration: "+song.getInteger("duration")); //display song information
            
            System.out.println("ORDER: " + doc.getInteger("order_p")); //display order in playlist
        }
    }
    
    public void removeCollection(int playlistId){ //method to remove the playlist collection
       
        MongoDatabase db = MongoManager.getDatabase(); //get the database
        String name = playlistId + "_playlist"; //get the collection name
        
        db.getCollection(name).drop(); //drop the collection
    }
    
    public boolean collectionExists(int playlistId){ //method to check if the playlist collection exists
        MongoDatabase db = MongoManager.getDatabase(); //get the database
        String name = playlistId + "_playlist"; //get the collection name
        for(String n: db.listCollectionNames()){ //loop through the collection names
            if(n.equals(name)){ //if the collection name matches
                return true; //return true
            }
            
        }
        return false; //else return false
    }
}
