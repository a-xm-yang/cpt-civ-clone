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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MapGraphics extends Application {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    ZoomPane root;

    @Override
    public void start(Stage primaryStage) {

        int sceneSize = 1000;

        //Create the zoompane called "root" in the scene
        root = createFalseMap(sceneSize);
        root.setOnMousePressed(circleOnMousePressedEventHandler);
        root.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, sceneSize, sceneSize));

        primaryStage.setTitle("java-buddy");
        primaryStage.show();
    }

    private ZoomPane createFalseMap(int sceneSize) {

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

        ZoomPane pane = new ZoomPane(40, sceneSize, spareSize, copy);

        return pane;

    }

    private class ZoomPane extends Group {

        private double scale;
        private int mapSize;
        private int sceneSize;
        private int spareSize;

        public ZoomPane(int mapSize, int sceneSize, int spareSize, Tile[][] tileMap) {

            super();
            this.mapSize = mapSize;
            this.sceneSize = sceneSize;
            this.spareSize = spareSize;
            scale = 1;

            //set actual objects according to the given falsified tileMap
            //since each display tile has a x-y-coordinate, and the map is a false map to scroll around, we need to adjust the coordinates accoridngly
            double offset = 0;
            for (int i = 0; i < tileMap.length; i++){
                if (i % 2 == 1){
                    offset = 50;
                } else{
                    offset = 0;
                }
                for (int k = 0; k < tileMap[0].length; k++){
                    
                    int fixedY;
                    
                    if (k >= spareSize + mapSize){
                        //if it's the right buffer
                        fixedY = spareSize + mapSize;
                    } else if (k >= spareSize){
                        //if it's middle
                        fixedY = spareSize;
                    } else{
                        //if it's left buffer
                        fixedY = (mapSize - spareSize) * -1;
                    }
                    
                    DisplayTile t = new DisplayTile(tileMap[i][k], i, k - fixedY);
                    t.setTranslateY(80 * i);
                    t.setTranslateX(100 * k + offset);
                    
                    getChildren().add(t);
                }
            }
            
            
            
            setOnScroll((ScrollEvent event) -> {
                resize(event);
            });
            

        }

        private void resize(ScrollEvent event) {
            if (event.getDeltaY() == 0) {
                return;
            }

            if (event.getDeltaY() > 0) {
                if (scale < 5) {
                    scale++;
                }
            } else {
                if (scale > 1) {
                    scale--;
                }
            }

            this.setScaleX(scale);
            this.setScaleY(scale);
        }

        public int getSizeX() {
            return mapSize * 100 + 50;
        }

        public int getSizeY() {
            return mapSize / 2 * 160 + ((mapSize % 2 == 1) ? 80 : 0);
        }

        public int getHeight() {
            return sceneSize;
        }

        public int getTileWidth() {
            return 100;
        }
    }

    private class DisplayTile extends Pane {

        Polygon polygon;
        Tile tile;
        int x;
        int y;

        public DisplayTile(Tile tile, int x, int y) {

            //tile reference
            this.tile = tile;
            this.x = x;
            this.y = y;

            //position text
            Text t;
            t = new Text(Integer.toString(x) + " : " + Integer.toString(y));
            t.setTranslateX(50);
            t.setTranslateY(50);

            polygon = new Polygon();
            Color c = Color.TRANSPARENT;
            if (tile instanceof Ocean) {
                c = Color.NAVY;
            } else if (tile instanceof Plains) {
                c = Color.GREEN;
            } else if (tile instanceof Hills) {
                c = Color.BISQUE;
            } else if (tile instanceof Desert) {
                c = Color.ORANGE;
            } else {
                c = Color.BLACK;
            }

            polygon.setFill(c);
            polygon.setStroke(Color.GREEN);
            polygon.getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});

            this.getChildren().add(polygon);
            this.getChildren().add(t);

            this.setOnMouseClicked(e -> {

                //if click is not at center ignore
                System.out.println("Dewit");
                t.setStroke(Color.RED);
            });
        }
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            orgTranslateX = root.getTranslateX();
            orgTranslateY = root.getTranslateY();
        }
    };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {

            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            //translation shifts by half the screen size
            if (newTranslateY < (root.scale - 1) * root.getHeight() * 0.5 && newTranslateY > -1 * root.getSizeY() + (root.scale - 1) * root.getHeight() * -1.5 + root.getHeight()) {
                root.setTranslateY(newTranslateY);
            }


                root.setTranslateX(newTranslateX);
            

            System.out.println("X: " + root.getTranslateX());
            System.out.println("Y: " + root.getTranslateY());
        }
    };

}
