/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civilizationclone.GUI;

import civilizationclone.GameMap;
import civilizationclone.GameMap.MapSize;
import civilizationclone.Player;
import civilizationclone.Unit.UnitType;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GUIMain extends Application {

    private GamePane gamePane;
    private Scene titleScene;
    private Scene gameScene;

    @Override
    public void start(Stage primaryStage) {

        int resX = 1200;
        int resY = 800;

        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(new TitleMenu(resX, resY, primaryStage), resX, resY));
        //primaryStage.setScene(new Scene(startWithoutMenu(), resX, resY));

        primaryStage.setTitle("Alex Yang's Colonization II");
        primaryStage.show();

    }

    public Pane startWithoutMenu() {

        //Create the zoompane called "root" in the scene
        GameMap gameMap = new GameMap(MapSize.MEDIUM, 400);
        ArrayList<Player> p = new ArrayList<Player>();
        p.add(new Player("Joseph Stalin"));
        //p.add(new Player("Winston Churchill"));

        //   p.add(new Player("Adolf Hitler"));
        //   p.add(new Player("Franklin Roosevelt"));
        //   p.add(new Player("Mao Zedong"));
        //   p.add(new Player("Bennito Mussolini"));
        p.get(0).setCurrentGold(1000);
        //testingStats(p.get(0));
        //testingStats(p.get(1));

        gamePane = new GamePane(gameMap, p, 1200, 800, true);

        Media media = new Media(new File("src/Assets/Misc/babayetu.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        // player.play();

        return gamePane;
    }

    public void testingStats(Player p) {
        p.setTechIncome(10);
        p.setCurrentGold(100000000);

        for (UnitType u : UnitType.values()) {
            p.addBuildableUnit(u);
        }
    }

}
