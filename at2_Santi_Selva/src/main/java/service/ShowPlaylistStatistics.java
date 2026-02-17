package service;

import config.Global;
import data.Playlist;
import data.Song;
import java.util.ArrayList;
import java.util.HashMap;

/*  To calculate the playlist statistics, I chose to do in
    Java because it is more optimal for large projects with 
    complex logic. I can also work directly with objects, perform 
    filtering, iterations, and conditional calculations in a easy way
 */

public class ShowPlaylistStatistics implements MenuOption{ //class that shows statistics of a playlist
    @Override
    public void execute(){ //method that executes the statistics showing
         
        int playlistId; //variable to store the playlist id
        
        do{
            playlistId = Global.enterInt("\nEnter the playlist id: "); //ask the user for the playlist id
            if(!Global.pDAO.playlistExists(playlistId)) { //check if the playlist exists
                System.out.println("This playlist doesn't exists"); //if it doesn't exist, show a message
                return; // exit the method
            }
        }while(playlistId <0); //repeat until a valid playlist id is entered
        
        showStatistics(playlistId); //show the statistics of the playlist
    }
    
    public void showStatistics(int playlistId){ //method that shows the statistics of the playlist
        ArrayList<Playlist> list = Global.pDAO.listPlaylist(playlistId); //get the list of songs in the playlist from the database
        System.out.println("\nStatics of playlist:"); //print the statistics
        System.out.println("Number of songs in playlist: "+calculateNumberOfSongs(list)); //calculate and print the number of songs
        System.out.println("Total duration of playlist: "+calculateDuration(list)); //calculate and print the total duration
        System.out.println("Average duration of playlist: "+calculateAverageDuration(list)); //calculate and print the average duration
        getLongestSong(list); //get and print the longest song
        listGenre(list); //list and print the genres in the playlist
    } 

    public int calculateNumberOfSongs(ArrayList<Playlist> list) { //method that calculates the number of songs in the playlist
        
        int songNumber = 0; //variable to store the number of songs
        for(Playlist playlist: list){ //for each song in the playlist
            songNumber++; //increment the song number
        }
        return songNumber; //return the number of songs
    }
    
    public String calculateDuration(ArrayList<Playlist> list){ //method that calculates the total duration of the playlist
        int duration = 0; //variable to store the duration
        
        for(Playlist playlist: list){ //for each song in the playlist
            int songId = playlist.getIdSong(); //get the song id
            Song song = Global.sDAO.findSongById(songId); //find the song by its id
            duration += song.getDuration(); //add the song duration to the total duration
        }
        
        int[] hms = calculateDurationHMS(duration); //calculate the duration in hours, minutes and seconds
        
        return hms[0]+":"+hms[1]+":"+hms[2]; //return the duration as a string
    }
    
    public int[] calculateDurationHMS(int duration){ //method that calculates the duration in hours, minutes and seconds
        int hours = duration / 3600; //calculate the hours
        int minutes = (duration % 3600) / 60; //calculate the minutes
        int seconds = duration % 60; //calculate the seconds 
        return new int[]{hours, minutes, seconds}; //return the duration as an array
    }
    
    public String calculateAverageDuration(ArrayList<Playlist> list){ //method that calculates the average duration of the playlist
        int duration = 0; //variable to store the total duration
        int count = 0; //variable to store the number of songs
        for(Playlist playlist: list){ //for each song in the playlist
            int songId = playlist.getIdSong(); //get the song id
            Song song = Global.sDAO.findSongById(songId); //find the song by its id
            duration += song.getDuration(); //add the song duration to the total duration
            count++; //increment the song count
        }
        int avgInSeconds = duration / count; //calculate the average duration in seconds
        int[] ms = calculateDurationMS(avgInSeconds); //calculate the duration in minutes and seconds
        return ms[0]+":"+ms[1]; //return the average duration as a string
    } 
    
    public int[] calculateDurationMS(int duration){//method that calculates the duration in minutes and seconds
        int minutes = duration / 60; //calculate the minutes
        int seconds = duration % 60; //calculate the seconds 
        return new int[]{minutes, seconds}; //return the duration as an array
    }
    
    public void getLongestSong(ArrayList<Playlist> list){//method that gets the longest song in the playlist
        ArrayList<String> longSongList = new ArrayList<>(); //list to store the titles of the longest songs
        ArrayList<Song>songList = new ArrayList<>();//list to store the songs
        int maxDuration = 0; //variable to store the maximum duration
        
        for(Playlist playlist: list){ //for each song in the playlist
            int songId = playlist.getIdSong(); //get the song id
            Song song = Global.sDAO.findSongById(songId); //find the song by its id
            int songDuration = song.getDuration(); //get the song duration
            songList.add(song); //add the song to the song list
            
            if(songDuration > maxDuration){ //if the song duration is greater than the maximum duration
                maxDuration = songDuration; //update the maximum duration
            } 
        }
        for(Song song: songList){ //for each song in the song list
            int songDuration = song.getDuration(); //get the song duration
            if (songDuration == maxDuration){ //if the song duration is equal to the maximum duration
                longSongList.add(song.getTitle()); //add the song title to the longest song list
            } 
        }
        
        System.out.println("Longest song in the playlist"); //print the longest songs
        for(String songTitle: longSongList){ //for each song title in the longest song list
            int[] ms = calculateDurationMS(maxDuration); //calculate the duration in minutes and seconds
            System.out.println("-"+songTitle+"("+ms[0]+":"+ms[1]+")"); //print the song title and duration
        }
        
    }
    
    public void listGenre(ArrayList<Playlist> list){ //method that lists the genres in the playlist
        HashMap<String, Integer> genreMap = getGenreInfo(list); //get the genre information
        System.out.println("Genres of the playlist:"); //print the genres
        for(HashMap.Entry<String, Integer> entry : genreMap.entrySet()){//for each entry in the genre map
            String genre = entry.getKey(); //get the genre
            int quantity = entry.getValue(); //get the quantity
            System.out.println("-"+genre+"("+quantity+")"); //print the genre and quantity
        }
        
    }
    
    public HashMap<String, Integer> getGenreInfo(ArrayList<Playlist> list){ //method that gets the genre information
        
        HashMap<String, Integer> genreMap = new HashMap<>(); //map to store the genre information
                
        for(Playlist playlist: list){ //for each song in the playlist
            int songId = playlist.getIdSong(); //get the song id
            Song song = Global.sDAO.findSongById(songId);// find the song by its id
            String genre = song.getGenre(); //get the song genre
             
            if(!genreMap.containsKey(genre)){ //if the genre is not in the map
                genreMap.put(genre, 1); //add the genre to the map with quantity 1
            }else{ //if the genre is already in the map
                int quantity = genreMap.get(genre); //get the current quantity
                quantity++; //increment the quantity
                genreMap.put(genre, quantity); //update the quantity in the map
            }
        }
        return genreMap; //and finally return the genre map
    }
}

