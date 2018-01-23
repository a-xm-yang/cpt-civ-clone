/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civilizationclone.GUI;

import civilizationclone.GameMap;
import civilizationclone.GameMap.MapSize;
import civilizationclone.Player;
import civilizationclone.TechType;
import civilizationclone.Unit.UnitType;
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
    public void start(Stage primaryStage) {

        int resX = 1200;
        int resY = 1000;

        //Create the zoompane called "root" in the scene
        GameMap gameMap = new GameMap(MapSize.MEDIUM, 400);
        ArrayList<Player> p = new ArrayList<Player>();
        p.add(new Player("Joseph Stalin"));
        p.add(new Player("Winston Churchill"));
        p.add(new Player("Adolf Hitler"));
        p.add(new Player("Franklin Roosevelt"));
        p.add(new Player("Mao Zedong"));
        p.add(new Player("Bennito Mussolini"));
        
        //For testing purposes
        p.get(0).setTechIncome(10);
        p.get(0).setCurrentGold(1000);

        root = new GamePane(gameMap, p, resX, resY, true);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, resX, resY, Color.BLACK));

        primaryStage.setTitle("Alex Yang's Colonization II");
        primaryStage.show();

        Media media = new Media(new File("src/Assets/Misc/babayetu.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
       // player.play();
    }

}
