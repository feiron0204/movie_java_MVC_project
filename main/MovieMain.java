package main;

import java.util.Scanner;

import viewer.MovieViewer;
import viewer.ActorViewer;
import viewer.BoxViewer;
import viewer.CommentViewer;
import viewer.ShowInfoViewer;
import viewer.TheaterViewer;
import viewer.UserViewer;

public class MovieMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        UserViewer userViewer = new UserViewer();
        MovieViewer movieViewer = new MovieViewer();
        TheaterViewer theaterViewer = new TheaterViewer();
        ShowInfoViewer showInfoViewer = new ShowInfoViewer();
        CommentViewer commentViewer = new CommentViewer();
        ActorViewer actorViewer = new ActorViewer();
        BoxViewer boxViewer = new BoxViewer();
        
        userViewer.setScanner(scanner);
        userViewer.setMovieViewer(movieViewer);
        userViewer.setTheaterViewer(theaterViewer);
        userViewer.setCommentViewer(commentViewer);
        userViewer.SetShowInfoViewer(showInfoViewer);
        
        movieViewer.setScanner(scanner);
        movieViewer.setCommentViewer(commentViewer);
        movieViewer.setShowInfoViewer(showInfoViewer);
        movieViewer.setActorViewer(actorViewer);
        
        
        theaterViewer.setScanner(scanner);
        theaterViewer.setShowInfoViewer(showInfoViewer);
        theaterViewer.setBoxViewer(boxViewer);
        
        showInfoViewer.setScanner(scanner);
        showInfoViewer.setTheaterViewer(theaterViewer);
        showInfoViewer.setBoxViewer(boxViewer);
        showInfoViewer.setMovieViewer(movieViewer);
        
        commentViewer.setScanner(scanner);
        commentViewer.setUserViewer(userViewer);
        commentViewer.setMovieViewer(movieViewer);
        
        actorViewer.setScanner(scanner);
        
        boxViewer.setScanner(scanner);
        
//----------------------------------------------------------------        
        
        userViewer.showIndex();
        
        scanner.close();
    }

}
