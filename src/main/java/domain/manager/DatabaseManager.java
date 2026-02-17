package domain.manager;

import java.sql.*;

public class DatabaseManager { //class that manages the SQLite database connection and table creation
    static String URL = "jdbc:sqlite:playlist.db"; //database URL
    
    public static Connection getConnection(){ //method to get the database connection
        try{
            return DriverManager.getConnection(URL);
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static void createTable(){ //method to create the tables in the database
        String sqlSongs =
                """
                CREATE TABLE IF NOT EXISTS songs(
                    id INTEGER PRIMARY KEY,
                    title VARCHAR(50),
                    artist VARCHAR(50),
                    album VARCHAR(50),
                    genre VARCHAR(50),
                    duration INTEGER
                );                                   
                """;
        String sqlPlaylist = 
                """
                CREATE TABLE IF NOT EXISTS playlists(
                    id_p INTEGER,
                    id_song INTEGER,
                    order_p INTEGER,
                    PRIMARY KEY(id_p, id_song),
                    FOREIGN KEY(id_song) REFERENCES songs(id)
                );
                """;
        
        try(Connection conn = getConnection(); 
            Statement st = conn.createStatement();){
            //execute the SQL statements to create the tables
            st.executeUpdate(sqlSongs);
            st.executeUpdate(sqlPlaylist);
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
}