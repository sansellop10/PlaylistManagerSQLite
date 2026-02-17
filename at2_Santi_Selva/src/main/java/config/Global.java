package config;

import domain.mongo.MongoDAO;
import domain.sqlite.PlaylistDAO;
import domain.sqlite.SongDAO;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Global {// class that contains all the global variables and methods used in the application
    public static Scanner in = new Scanner(System.in); //scanner for user input
    
    public static PlaylistDAO pDAO = new PlaylistDAO(); //playlist DAO for SQLite database
    public static SongDAO sDAO = new SongDAO(); //song DAO for SQLite database
    
    public static MongoDAO mongoDAO;// MongoDAO for MongoDB database
    
    
    public static int enterInt(String text){ //method to enter an integer value
        try{ //try catch to manage input exceptions
            int i= -1; //variable to store the integer value
            do{
                System.out.print(text); //print text for input
                i = Global.in.nextInt(); //read integer input
                Global.in.nextLine(); // consume the newline character

                if(i < 0){ //check if the input is less than 0
                    System.out.println("There is an error: This value can't be null");
                }
            }while(i < 0); // loop until a valid input is entered
            return i;// and return the integer value
        }catch(InputMismatchException ex){ //if the input is not an integer
            System.out.println("There is an error: This value must be a number"); //print error message
            Global.in.next(); //consume the invalid input
            return -1;  //and return -1 to indicate an error
        }
        
    }
    
    public static String enterString(String text){ //method to enter a string value
        String s = ""; //variable to store the string value
        do{
            System.out.print(text); //print text for input
            s = Global.in.nextLine(); //read string input

            if(s.isBlank()){ //check if the input is blank
                System.out.println("There is an error: This value can't be null"); //print error message
            }
        }while(s.isBlank()); // loop until a valid input is entered
        return s;  //and return the string value
    }
}
