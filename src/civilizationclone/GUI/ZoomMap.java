/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civilizationclone.GUI;

import civilizationclone.Player;
import civilizationclone.Tile.Tile;
import civilizationclone.Unit.Unit;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class ZoomMap extends Group {

    //Graphics Related Vars ----
    //<editor-fold>
    private double topCap, bottomCap;
    private double leftCap, rightCap;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    private double scale;
    private int mapSize;
    private int resX, resY;
    private int spareSize;
    private double sizeX, sizeY, totalSizeX;
    //</editor-fold>

    private Canvas canvas;
    private ArrayList<DisplayTile> tileList;
    private Point[] highlightedTiles;
    private HighlightType highlightType;

    private Player currentPlayer;
    private Unit selectedUnit;

    //size refers to the number of tiles
    public ZoomMap(int mapSize, int resX, int resY, int spareSize, Tile[][] tileMap) {

        super();

        highlightType = HighlightType.NONE;
        this.mapSize = mapSize;
        this.resX = resX;
        this.resY = resY;
        this.spareSize = spareSize;

        this.sizeX = mapSize * DisplayTile.getWIDTH();
        this.sizeY = mapSize / 2 * 164 + ((mapSize % 2 == 1) ? 82 : 0);
        this.totalSizeX = sizeX + 2 * getSpareWidth();

        scale = 1;
        tileList = new ArrayList<>();

        canvas = new Canvas(getSizeX() + 2 * (getSpareWidth()), getSizeY());
        DisplayTile.referenceCanvas(canvas);
        canvas.setMouseTransparent(true);

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
                tileList.add(t);
            }
        }

        getChildren().add(canvas);

        //shifts to original position
        setTranslateX(-1 * getSpareWidth());

        //handler for resizing the map
        setOnScroll((ScrollEvent event) -> {
            resize(event);
        });

        //handler for actions (incomplete)
        setOnMouseClicked((MouseEvent event) -> {
            clickEventHandling(event);
        });

        setOnMousePressed(onMousePressedEventHandler);
        setOnMouseDragged(onMouseDraggedEventHandler);

        calculateBounds();
        repaint();
    }

    private void clickEventHandling(MouseEvent e) {
        
        Tile clickedTile;
        
        //in case it doesn't exactly hit on a tile (which indeed happens)
        try {
            clickedTile = ((DisplayTile) e.getTarget()).getTile();
        } catch (Exception exception) {
            return;
        }

        if (clickedTile.hasCity()) {
            //TODO add city handling
        }

        //testing for only movement highlight
        if (highlightType == HighlightType.NONE) {
            if (clickedTile.hasUnit() && clickedTile.getUnit().getPlayer().equals(currentPlayer)) {
                //TODO Invoke a UnitOptionPane
                //Right now it's just for movement
                highlightType = HighlightType.MOVEMENT;
                addHighlightedTiles(clickedTile.getUnit().getMoves());
                selectedUnit = clickedTile.getUnit();
            }
        } else {

            //if movement is desired
            if (highlightType == highlightType.MOVEMENT) {
                Point clickPoint = ((DisplayTile) e.getTarget()).getPoint();
                for (Point p : highlightedTiles) {
                    if (clickPoint.x == p.x && clickPoint.y == p.y) {
                        selectedUnit.move(p);
                    }
                }
            }

            //reset the highlight status
            selectedUnit = null;
            highlightType = HighlightType.NONE;
            cleanHighlight();

        }

        repaint();
    }

    public void repaint() {
        canvas.getGraphicsContext2D().clearRect(0, 0, totalSizeX, sizeY);
        for (DisplayTile t : tileList) {
            t.update();
        }
    }

    public void addHighlightedTiles(Point[] possibleMoves) {
        highlightedTiles = possibleMoves;

        for (Point p : possibleMoves) {
            for (DisplayTile t : tileList) {
                if (t.getX() == p.x && t.getY() == p.y) {
                    t.setHighlighted(true);
                }
            }
        }
    }

    public void cleanHighlight() {
        highlightType = HighlightType.NONE;
        for (DisplayTile t : tileList) {
            t.setHighlighted(false);
        }
    }

    //handlers for scrolling ---------------
    //<editor-fold>
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
    //</editor-fold>

    //GETTERS AND SETTERS
    //<editor-fold>
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public HighlightType getHighlightType() {
        return highlightType;
    }

    public void setHighlightType(HighlightType highlightType) {
        this.highlightType = highlightType;
    }

    public double getSizeX() {
        return sizeX;
    }

    public double getSizeY() {
        return sizeY;
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

    public Canvas getCanvas() {
        return canvas;
    }
    //</editor-fold>

    EventHandler<MouseEvent> onMousePressedEventHandler
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            orgTranslateX = getTranslateX();
            orgTranslateY = getTranslateY();

        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler
            = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent t) {

            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

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
        }
    };
}

enum HighlightType {
    MOVEMENT, ATTACK, EXPANSION, NONE;
}
