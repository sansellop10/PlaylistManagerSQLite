package presentation;

import config.Global;
import domain.manager.DatabaseManager;
import java.util.InputMismatchException;
import service.AddSongsInPlaylist;
import service.ExportPlaylistToMongoDB;
import service.ListAllSongs;
import service.ListAllSongsInPlaylist;
import service.ListSongsFromMongoDBPlaylist;
import service.RemoveCollection;
import service.RemovePlaylist;
import service.RemoveSong;
import service.RemoveSongFromPlaylist;
import service.ShowPlaylistStatistics;

public class Main {
    
    public static void main(String[] args) {
       
        DatabaseManager.createTable(); //create the tables if they don't exist
        
        
        int option = -1; //variable to store the user's option
        do{
            try{
                showMenu(); //show the menu
                option = Global.in.nextInt(); //read the user's option
                Global.in.nextLine();// consume the newline character
                manageOptions(option); //manage the user's option
            }catch(InputMismatchException ex){ //if the input is not an integer
                System.out.println("There is an error: enter a number"); //print error message
                Global.in.next(); //and consume the invalid input
            }
        }while(option != 0); //loop until the user chooses to exit
    }
    
    static void showMenu(){ //method to show the menu
        System.out.print("""
                        \n************************************************************************
                                                       MENU
                        ************************************************************************
                        ========================================================================
                        0.  Exit
                        1.  Add songs to a playlist
                        2.  List all songs
                        3.  List all songs in a playlist
                        4.  Remove a song from the data base
                        5.  Remove a song from a playlist
                        6.  Remove a playlist
                        7.  Show playlist statistics
                        8.  Export playlist to MongoDB
                        9.  List songs from a MongoDB playlist
                        10. Remove playlist from MongoDB
                        ========================================================================
                            Select an option
                            >""");
    }
    
    static void manageOptions(int option){ //method to manage the user's option
        switch(option){ //switch case to manage the user option
            case 0 -> System.out.println("Leaving..."); // if the user chooses to exit print leaving message
            case 1 -> new AddSongsInPlaylist().execute(); // execute the add songs to playlist service
            case 2 -> new ListAllSongs().execute(); // execute the list all songs service
            case 3 -> new ListAllSongsInPlaylist().execute(); // execute the list all songs in playlist service
            case 5 -> new RemoveSongFromPlaylist().execute(); // execute the remove song from playlist service
            case 4 -> new RemoveSong().execute(); // execute the remove song from database service
            case 6 -> new RemovePlaylist().execute(); // execute the remove playlist service
            case 7 -> new ShowPlaylistStatistics().execute(); // execute the show playlist statistics service
            case 8 -> new ExportPlaylistToMongoDB().execute(); // execute the export playlist to MongoDB service
            case 9 -> new ListSongsFromMongoDBPlaylist().execute(); // execute the list songs from MongoDB playlist service
            case 10 -> new RemoveCollection().execute(); // execute the remove playlist from MongoDB service
            default -> System.out.println("There is an error: invalid option"); // if the user enters an invalid option print error message
        }
    }
}
