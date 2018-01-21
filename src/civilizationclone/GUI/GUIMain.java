/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civilizationclone.GUI;

import civilizationclone.GameMap;
import civilizationclone.GameMap.MapSize;
import civilizationclone.Player;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUIMain extends Application {

    GamePane root;

    @Override
    public void start(Stage primaryStage){

        int resX = 1200;
        int resY = 1000;

        //Create the zoompane called "root" in the scene
        GameMap gameMap = new GameMap(MapSize.SMALL,400);
        ArrayList<Player> p = new ArrayList<Player>();
        p.add(new Player("Иосиф Сталин"));
        p.add(new Player("Adolf Hitler"));
        p.add(new Player("毛泽东"));
        p.add(new Player("Teddy Roosevelt"));
        
        root = new GamePane(gameMap, p, resX, resY);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, resX, resY, Color.BLACK));

        primaryStage.setTitle("Alex Yang's Colonization II");
        primaryStage.show();
        
        Media media = new Media(new File("src/Assets/Misc/babayetu.mp3").toURI().toString()); 
        MediaPlayer player = new MediaPlayer(media); 
       // player.play();
    }

    
    //TODO -- Finish resource import and printing
}
