package domain.sqlite;

import data.Song;
import domain.manager.DatabaseManager;
import java.util.ArrayList;
import java.sql.*;

public class SongDAO{//class that manages the SQLite operations for songs
    
    public void insertSong(Song s){ //method to insert a song into the database
        String sql = "INSERT INTO songs VALUES (?, ?, ?, ?, ?, ?)"; //SQL statement to insert a song
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setInt(1, s.getId()); //set song ID
            ps.setString(2, s.getTitle()); //set song title
            ps.setString(3, s.getArtist()); //set song artist
            ps.setString(4, s.getAlbum()); //set song album
            ps.setString(5, s.getGenre()); //set song genre
            ps.setInt(6, s.getDuration()); //set song duration
            
            ps.executeUpdate(); //execute the SQL statement
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public ArrayList<Song> listSong(){ //method to list all songs in the database
        String sql = "SELECT * FROM songs"; //SQL statement to select all songs
        ArrayList<Song> list = new ArrayList<>(); //list to store the songs
        
        try(Connection conn = DatabaseManager.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)){
            
            while(rs.next()){
                Song song = new Song(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("artist"),
                    rs.getString("album"),
                    rs.getString("genre"),
                    rs.getInt("duration")
                ); //create song object
                list.add(song); //add song object to the list
            }
            return list; //and finally return the list of songs
        
        }catch(SQLException ex){
            ex.printStackTrace();
            return new ArrayList<>();
        }
    
    }
    
    public Song findSongById(int songId){ //method to find a song by its ID
        String sql = "SELECT * FROM songs WHERE id = ?"; //SQL statement to select the song by its ID
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
            
            ps.setInt(1, songId); //set song ID
            
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Song song = new Song(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("artist"),
                        rs.getString("album"),
                        rs.getString("genre"),
                        rs.getInt("duration")
                    ); //create song object
                    return song; //return the song object
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public boolean songExists(int songId){ //method to check if a song exists
        String sql = "SELECT id FROM songs WHERE id = ?"; //SQL statement to select the song ID
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setInt(1, songId); //set song ID
            
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    return true; //if the song exists, return true
                }
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    public void removeSongInDataBase(int songId){ //method to remove a song from the database
        String sql1 = "DELETE FROM playlists WHERE id_song = ?"; //SQL statement to delete the song from all playlists
        String sql2 = "DElETE FROM songs WHERE id = ?"; //SQL statement to delete the song from the songs table
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2)){
            
            ps1.setInt(1, songId); //set song ID
            ps2.setInt(1, songId); //set song ID
            
            ps1.executeUpdate(); //execute the SQL statements
            ps2.executeUpdate(); //execute the SQL statements
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
}

