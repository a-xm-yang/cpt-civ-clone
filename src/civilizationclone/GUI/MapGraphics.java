/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civilizationclone.GUI;

import civilizationclone.GameMap;
import civilizationclone.Tile.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MapGraphics extends Application {

    ZoomMap root;

    @Override
    public void start(Stage primaryStage) {

        int sceneSize = 1000;

        //Create the zoompane called "root" in the scene
        root = createFalseMap(sceneSize);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, sceneSize, sceneSize));

        primaryStage.setTitle("java-buddy");
        primaryStage.show();
    }

    private ZoomMap createFalseMap(int sceneSize) {

        //creates a copy of the actual tile map, with the sides adjusted to a copied version for scrolling effect
        //first calculate how much spare is needed on both sides, should be according to scene size
        int spareSize = sceneSize / 100 + 1;

        Tile[][] original = new GameMap(GameMap.MapSize.SMALL, 150).getMap();
        Tile[][] copy = new Tile[original.length][original[0].length + spareSize * 2];

        //copy the back of the original to the front spare, then front of the original to the back spare
        //first copy the back to front
        for (int x = 0; x < copy.length; x++) {
            for (int y = 0; y < spareSize; y++) {
                copy[x][y] = original[x][original[0].length - spareSize + y];
            }
        }
        //then copy the original chunk
        for (int x = 0; x < copy.length; x++) {
            for (int y = 0; y < original[0].length; y++) {
                copy[x][y + spareSize] = original[x][y];
            }
        }
        //then copy the front of the orignial to the back spare
        for (int x = 0; x < copy.length; x++) {
            for (int y = 0; y < spareSize; y++) {
                copy[x][y + spareSize + copy.length] = original[x][y];
            }
        }

        ZoomMap pane = new ZoomMap(40, sceneSize, spareSize, copy);

        return pane;

    }

}
