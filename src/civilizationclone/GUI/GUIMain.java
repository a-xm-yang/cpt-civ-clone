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
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIMain extends Application {

    private GamePane gamePane;

    @Override
    public void start(Stage primaryStage) {

        int resX = 1200;
        int resY = 800;

        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(new TitleMenu(resX, resY, primaryStage), resX, resY));
        //primaryStage.setScene(new Scene(startWithoutMenu(), 1200, 1000));

        primaryStage.setTitle("Alex Yang's Colonization II");
        primaryStage.show();

    }

    public Pane startWithoutMenu() {

        //intialize a game without going into title menu, strictly for testing
        
        GameMap gameMap = new GameMap(MapSize.MEDIUM, 400);
        ArrayList<Player> p = new ArrayList<Player>();
        p.add(new Player("Joseph Stalin"));
        //p.add(new Player("Winston Churchill"));

        //   p.add(new Player("Adolf Hitler"));
        //   p.add(new Player("Franklin Roosevelt"));
        //   p.add(new Player("Mao Zedong"));
        //   p.add(new Player("Bennito Mussolini"));
        testingStats(p.get(0));
        //testingStats(p.get(1));

       // gamePane = new GamePane(gameMap, p, 1200, 1000, true, false);
        return gamePane;
    }

    public void testingStats(Player p) {
        p.setTechIncome(10);
        p.setCurrentGold(100000000);

        for (TechType t : TechType.values()) {
            p.addTech(t);
        }
    }

}
