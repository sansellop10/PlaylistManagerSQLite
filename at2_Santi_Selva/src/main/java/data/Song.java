package data;

public class Song { //class that represents a song
    private int id; //song id
    private String title; //song title
    private String artist; //song artist
    private String album; //song album
    private String genre; //song genre
    private int duration; //song duration in seconds
    
    public Song(int id, String title, String artist, String album, String genre, int duration){ //constructor
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.duration = duration;
    }
    
    //getters
    public int getId(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getAlbum(){return album;}
    public String getGenre(){return genre;}
    public int getDuration(){return duration;}
    
    //toString method
    @Override
    public String toString(){
        return "-"+id+".  -TITLE: "+title+"  -ARTIST: "+artist+"  -ALBUM: "+album+"  -GENRE: "+genre+"  -DURATION: "+duration+"s.";
    }
    
    
}
