package domain.sqlite;

import data.Playlist;
import domain.manager.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;

public class PlaylistDAO { //class that manages the SQLite operations for playlists

    public void insertPlaylist(Playlist p) {  //method to insert a playlist into the database
        String sql = "INSERT INTO playlists VALUES (?, ?, ?)"; //SQL statement to insert a playlist

        try (Connection conn = DatabaseManager.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getId()); //set playlist ID
            ps.setInt(2, p.getIdSong()); //set song ID
            ps.setInt(3, p.getOrder()); //set order in playlist

            ps.executeUpdate(); //execute the SQL statement
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Playlist> listPlaylist(int playlistId) { //method to list all songs in a playlist
        String sql = "SELECT * FROM playlists WHERE id_p = ?"; //SQL statement to select all songs in a playlist
        ArrayList<Playlist> list = new ArrayList<>(); //list to store the songs in the playlist
        try (Connection conn = DatabaseManager.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, playlistId); //set playlist ID
            try(ResultSet rs = ps.executeQuery()){
                
                while (rs.next()) { //loop through the result set and create a playlist object for each song
                Playlist playlist = new Playlist( 
                        rs.getInt("id_p"),
                        rs.getInt("id_song"),
                        rs.getInt("order_p")
                ); //create playlist object
                list.add(playlist); //add playlist object to the list

                }
                return list; //finally return the list of songs in the playlist
            }
            

        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public boolean playlistExists(int playlistId){//method to check if a playlist exists
        String sql = "SELECT id_p FROM playlists WHERE id_p = ?"; //SQL statement to select the playlist ID
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setInt(1, playlistId); //set playlist ID
            
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    return true; //if the playlist exists, return true
                }
            }
         
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false; //else return false
    }
    
    public int returnOrder(int playlistId){ //method to return the current order of the playlist
        String sql = "SELECT max(order_p) as current_order FROM playlists WHERE id_p = ?"; // SQL statement to select the maximum order of the playlist
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
        
            ps.setInt(1, playlistId); //set playlist ID
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){ 
                    return rs.getInt("current_order"); //return the current order
                }
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return 0;
    }
    
    public void removeSong(int playlistId, int songId){ //method to remove a song from the playlist
        String sql = "DELETE FROM playlists WHERE id_p = ? AND id_song = ?"; //SQL statement to delete a song from the playlist
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
        
            ps.setInt(1, playlistId); //set playlist ID
            ps.setInt(2, songId); //set song ID
            
            ps.executeUpdate(); //execute the SQL statement
        
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean songExistsInPlaylist(int playlistId, int songId){//method to check if a song exists in the playlist
        String sql = "SELECT id_p FROM playlists WHERE id_p = ? AND id_song = ?"; //SQL statement to select the playlist ID and song ID
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);){
            
            ps.setInt(1, playlistId); //set playlist ID
            ps.setInt(2, songId); //set song ID
            
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    return true; //if the song exists in the playlist, return true
                }
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false; //else return false
    }
    
    public void removePlaylist(int playlistId){//method to remove a playlist
        String sql = "DELETE FROM playlists WHERE id_p = ?"; //SQL statement to delete a playlist
        
        try(Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setInt(1, playlistId); //set playlist ID
            ps.executeUpdate(); //execute the SQL statement
            
        }catch(SQLException ex){
            ex.printStackTrace(); 
        }
    
    }
    
    public Playlist findPlaylistById(int playlistId){//method to find a playlist by its ID
        String sql = "SELECT * FROM playlists WHERE id_p = ?"; //SQL statement to select the playlist by its ID
        try(Connection conn = DatabaseManager.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setInt(1, playlistId);//set playlist ID
            
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Playlist playlist = new Playlist(
                            rs.getInt("id_p"),
                            rs.getInt("id_song"),
                            rs.getInt("order_p")
                    
                    );//create playlist object
                    return playlist;//return the playlist object
                }
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
