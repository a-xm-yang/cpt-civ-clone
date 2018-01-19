package civilizationclone.GUI;

import civilizationclone.Tile.*;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class DisplayTile extends Polygon {

    //member variables
    //<editor-fold>
//    private Polygon polygon;
    private Tile tile;
    private static Canvas canvas;
    private static GraphicsContext gc;
    //x y position of the tile
    private int x;
    private int y;
    private boolean highlighted = false;
    private boolean greenHighlighted = false;
    private boolean blueHighlighted = false;

    static final double WIDTH = 102;
    static final double HEIGHT = 82;
    
    public DisplayTile(Tile tile, int x, int y) {

        //tile reference
        this.tile = tile;
        this.x = x;
        this.y = y;

        setStroke(Color.BLACK);
        getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});
        setFill(new ImagePattern(ImageBuffer.getImage(tile)));
        setStrokeWidth(3);

        update();

    }

    public void update() {

        gc = canvas.getGraphicsContext2D();

        if (tile.hasCity()) {
            gc.drawImage(ImageBuffer.getCityImage(), 5 + getTranslateX(), 10 + getTranslateY());
        }
        
        if (tile.hasUnit()) {
            gc.drawImage(ImageBuffer.getImage(tile.getUnit()), 15 + getTranslateX(), 20 + getTranslateY());

        }

        if (tile.isControlled()) {
            blueHighlighted = true;
        }

        //will be changed later
        if (highlighted) {
            setStroke(Color.RED);

        } else {
            setStroke(Color.BLACK);
        }
    }

    //GETTERS & EQUAL METHODS
    //<editor-fold>
    public Point getPoint() {
        return new Point(x, y);
    }

    public static double getWIDTH() {
        return WIDTH;
    }

    public static double getHEIGHT() {
        return HEIGHT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile getTile() {
        return tile;
    }
    //</editor-fold>

    public static void referenceCanvas(Canvas ref) {
        canvas = ref;
        gc = canvas.getGraphicsContext2D();
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

}
