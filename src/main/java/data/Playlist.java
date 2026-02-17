package data;


public class Playlist { //class that represents a playlist
    private int id; //playlist id
    private int idSong; //song id that refers to the song in the playlist
    private int order; //order of the song in the playlist
   
    public Playlist(int id,int idSong, int order){ //constructor
        this.id = id;
        this.idSong = idSong;
        this.order = order;
    }
    
    //getters
    public int getId(){return id;}
    public int getIdSong(){return idSong;}
    public int getOrder(){return order;}
    
    //toString method
    @Override
    public String toString(){
        return "id: "+id+"  idSong: "+idSong+"  order: "+order;
    }
}