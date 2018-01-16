/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civilizationclone.GUI;

import civilizationclone.GameMap;
import civilizationclone.Player;
import civilizationclone.Tile.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUIMain extends Application {

    GamePane root;

    @Override
    public void start(Stage primaryStage){

        int resX = 1200;
        int resY = 1000;

        //Create the zoompane called "root" in the scene
        
        ArrayList<Player> p = new ArrayList<Player>();
        p.add(new Player("Stalin"));
        
        root = new GamePane(p, resX, resY);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, resX, resY, Color.BLACK));

        primaryStage.setTitle("Alex Yang's Colonization II");
        primaryStage.show();
    }

}
