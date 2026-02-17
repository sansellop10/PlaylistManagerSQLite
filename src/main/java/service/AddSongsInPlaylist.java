package service;

import config.Global;
import data.Playlist;
import data.Song;
import java.util.ArrayList;

public class AddSongsInPlaylist implements MenuOption { //class that manages the addition of songs to a playlist

    @Override
    public void execute() { //method to execute the option
        int playlistId; //variable to store the playlist ID
        while (true) { //loop to enter playlist IDs
            do {
                playlistId = Global.enterInt("\nEnter a playlist id (0 to exit): "); //enter playlist ID
            } while (playlistId < 0); //loop until a valid input is entered

            if (playlistId == 0) {
                break; //exit the loop if the user enters 0
            }

            //ArrayList<Integer> playlistIdList = createPlaylistIdList();
            if (Global.pDAO.playlistExists(playlistId)) { //check if the playlist exists
                createSong(playlistId, 1); //if it exists, add songs in mode 1 (append)
            } else { 
                createSong(playlistId, 2); //if it doesn't exist, add songs in mode 2 (new playlist)
            }
        }
    }

    public ArrayList<Integer> createSongIdList() { //method to create a list of song IDs
        ArrayList<Integer> idList = new ArrayList<>(); //list to store the song IDs
        ArrayList<Song> songList = Global.sDAO.listSong(); //list of all songs in the database
        for (Song p : songList) {
            idList.add(p.getId()); //add the song ID to the list
        }
        return idList; //and return the list
    }

    public void createSong(int playlistId, int mode) { //method to create songs and add them to the playlist
        ArrayList<Integer> songIdList = createSongIdList(); //list of existing song IDs
        boolean flag = true; //flag to check if the song exists

        while (true) { //loop to enter song details
            flag = true; //reset flag for each iteration
            int id; //variable to store the song ID
            int durationInSeconds; //variable to store the song duration
            

            do {
                id = Global.enterInt("\nEnter a song id (0 to exit): "); //enter song ID
                if (songIdList.contains(id) && Global.pDAO.songExistsInPlaylist(playlistId, id)) { 
                    System.out.println("There is an error: This id already exists"); //check if the song ID already exists in the playlist
                }
            } while ((songIdList.contains(id) && Global.pDAO.songExistsInPlaylist(playlistId, id)) || id < 0); //loop until a valid input is entered

            if (id == 0) { 
                break; //exit the loop if the user enters 0
            }else if(Global.sDAO.songExists(id)){ //check if the song exists in the database
                flag = false; //set flag to false if the song exists
            }
            
            if(flag){ //if the song doesn't exist, enter the song details
                songIdList.add(id); //add the song ID to the list

                String title = Global.enterString("Enter a song title: "); //enter song title
                String artist = Global.enterString("Enter a song artist: "); //enter song artist
                String album = Global.enterString("Enter a song album: "); //enter song album
                String genre = Global.enterString("Enter a song genre: "); //enter song genre

                do {
                    durationInSeconds = Global.enterInt("Enter a song duration in seconds: "); //enter song duration
                } while (durationInSeconds < 0); //loop until a valid input is entered

                Song song = new Song(id, title, artist, album, genre, durationInSeconds); //create song object
                Global.sDAO.insertSong(song); //insert the song into the database
            }
            
            Playlist playlist; //variable to store the playlist object
            if (mode == 1) { //create playlist object
                playlist = new Playlist(playlistId, id, 1); //create playlist object in mode 1 (append)
            } else {
                int order = Global.pDAO.returnOrder(playlistId); //get the order of the last song in the playlist
                playlist = new Playlist(playlistId, id, order + 1); //create playlist object in mode 2 (new playlist)
            }
            
            if (!Global.pDAO.songExistsInPlaylist(playlistId, id)) {
                Global.pDAO.insertPlaylist(playlist); //if the song doesn't exist in the playlist, insert it
            }
            
            System.out.println("\nSong saved successfully"); //and finally print success message
           
        }
    }

}
