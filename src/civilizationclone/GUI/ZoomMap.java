/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civilizationclone.GUI;

import civilizationclone.Tile.Tile;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.ScrollEvent;

public class ZoomMap extends Group {

    //used for dragging
    private double topCap, bottomCap;
    private double leftCap, rightCap;

    private double scale;
    private int mapSize;
    private int resX;
    private int resY;
    private int spareSize;

    //size refers to the number of tiles
    public ZoomMap(int mapSize, int resX, int resY, int spareSize, Tile[][] tileMap) {

        super();
        this.mapSize = mapSize;
        this.resX = resX;
        this.resY = resY;
        this.spareSize = spareSize;
        scale = 1;

        //set actual objects according to the given falsified tileMap
        //since each display tile has a x-y-coordinate, and the map is a false map to scroll around, we need to adjust the coordinates accoridngly
        double offset = 0;
        for (int i = 0; i < tileMap.length; i++) {
            if (i % 2 == 1) {
                offset = DisplayTile.getWIDTH() / 2;
            } else {
                offset = 0;
            }
            for (int k = 0; k < tileMap[0].length; k++) {

                int fixedY;

                if (k >= spareSize + mapSize) {
                    //if it's the right buffer
                    fixedY = spareSize + mapSize;
                } else if (k >= spareSize) {
                    //if it's middle
                    fixedY = spareSize;
                } else {
                    //if it's left buffer
                    fixedY = (mapSize - spareSize) * -1;
                }

                DisplayTile t = new DisplayTile(tileMap[i][k], i, k - fixedY);
                t.setTranslateY(DisplayTile.getHEIGHT() * i);
                t.setTranslateX(DisplayTile.getWIDTH() * k + offset);
                getChildren().add(t);
            }
        }
        
        //initialize the canvas drawing for each object
        for (int i = 0; i < getChildren().size(); i++){
            Node n = this.getChildren().get(i);
            if (n instanceof DisplayTile){
                ((DisplayTile) n).initCanvas();
            }
        }

        //shifts to original position
        setTranslateX(-1 * getSpareWidth());

        setOnScroll((ScrollEvent event) -> {
            resize(event);
        });

        calculateBounds();
    }

    //handlers for scrolling
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
                setTranslateY(getTranslateY() + getSizeY() / 2);
            }
        }

        this.setScaleX(scale);
        this.setScaleY(scale);

        calculateBounds();
        adjustPosition();

    }

    private void calculateBounds() {

        //the Y-traslation that would hit the top bottom border of the map
        topCap = (getScale() - 1) * getSizeY() * 0.5;
        bottomCap = getSizeY() * (-0.5 * getScale() - 0.5) + getResY();

        //the X-translation that would hit the boundary between the real map and the fake extension map
        leftCap = (getSizeX() / 2 + getSpareWidth()) * (getScale() - 1) - getSpareWidth() * getScale();
        rightCap = (getSizeX() / 2 + getSpareWidth()) * (getScale() - 1) - (getSpareWidth() + getSizeX()) * getScale();
    }

    private void adjustPosition() {

        //make sure the screen is in bounds after what happened
        if (getTranslateY() > topCap) {
            setTranslateY(topCap);
        } else if (getTranslateY() < bottomCap) {
            setTranslateY(bottomCap);
        }

        if (getTranslateX() >= leftCap + getResX()) {
            setTranslateX(rightCap + getResX());
        } else if (getTranslateX() <= rightCap) {
            setTranslateX(leftCap);
        }
    }

    /*
                if (newTranslateY < topCap && newTranslateY > bottomCap) {
                setTranslateY(newTranslateY);
            }

            if (newTranslateX >= leftCap + getResX()) {
                setTranslateX(rightCap + getResX());
            } else if (newTranslateX <= rightCap) {
                setTranslateX(leftCap);
            } else {
                setTranslateX(newTranslateX);
            }

    
     */
    
    //GETTERS AND SETTERS
    //<editor-fold>
    public double getSizeX() {
        return mapSize * DisplayTile.getWIDTH();
    }

    public double getSizeY() {
        return mapSize / 2 * 160 + ((mapSize % 2 == 1) ? 80 : 0);
    }

    public int getResX() {
        return resX;
    }

    public int getResY() {
        return resY;
    }

    public double getTileWidth() {
        return DisplayTile.WIDTH;
    }

    public double getScale() {
        return scale;
    }

    public double getSpareSize() {
        return spareSize;
    }

    public double getSpareWidth() {
        return spareSize * DisplayTile.getWIDTH() + DisplayTile.getWIDTH() / 2;
    }
    
    public void addCanvas(Canvas n){
        this.getChildren().add(n);
    }
    //</editor-fold>
}
