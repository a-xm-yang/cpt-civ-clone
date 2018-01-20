package civilizationclone.GUI;

import civilizationclone.Tile.*;
import static civilizationclone.Tile.Improvement.NONE;
import java.awt.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class DisplayTile extends Polygon {

    //member variables
    //<editor-fold>
    //Graphic-related members
    private static Canvas canvas;
    private static GraphicsContext gc;
    private static ColorAdjust shade;
    private static ImagePattern cloudFill;
    private ImagePattern tileFill;
    private boolean highlighted = false;
    private boolean greenHighlighted = false;
    private boolean blueHighlighted = false;
    //Data-related members
    private int x;
    private int y;
    private Tile tile;
    private int accessLevel;
    //</editor-fold>

    static final double WIDTH = 102;
    static final double HEIGHT = 82;

    public DisplayTile(Tile tile, int x, int y) {

        //tile reference
        this.tile = tile;
        this.x = x;
        this.y = y;

        accessLevel = 2;

        setStroke(Color.BLACK);
        getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});
        tileFill = new ImagePattern(ImageBuffer.getImage(tile));
        setStrokeWidth(3);
    }

    public void update() {

        //Access level 0: Complete Coverage
        //Access level 1: Show terrain, city, and resource
        //Access level 2: Show everything
        gc = canvas.getGraphicsContext2D();

        if (accessLevel == 0) {
            
            setFill(cloudFill);
            setEffect(null);
            setStroke(Color.LIGHTGRAY);
        } else {
            
            setFill(tileFill);
            setStroke(Color.BLACK);

            if (tile.hasCity()) {
                gc.drawImage(ImageBuffer.getCityImage(), 5 + getTranslateX(), 10 + getTranslateY());
            }

            if (accessLevel == 1) {

                //ADD RESOURCE PRINTING
                if (tile.getImprovement() != NONE) {
                    gc.drawImage(ImageBuffer.getImage(tile.getImprovement()), 15 + getTranslateX(), 20 + getTranslateY());
                }

                setEffect(shade);

            } else {

                //FULL ACCESS
                if (tile.getImprovement() != NONE) {
                    gc.drawImage(ImageBuffer.getImage(tile.getImprovement()), 15 + getTranslateX(), 20 + getTranslateY());
                }
                if (tile.hasUnit()) {
                    gc.drawImage(ImageBuffer.getImage(tile.getUnit()), 15 + getTranslateX(), 20 + getTranslateY());
                }
                

                setEffect(null);
            }
        }

        if (tile.isControlled()) {
            blueHighlighted = true;
        }

        //will be changed later
        if (highlighted) {
            setStroke(Color.RED);
        }
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    //GETTERS & EQUAL METHODS
    //<editor-fold>
    public int getAccessLevel() {
        return accessLevel;
    }

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

    @Override
    public boolean equals(Object o) {

        if (o instanceof DisplayTile) {
            if (x == ((DisplayTile) o).getX() && y == ((DisplayTile) o).getY()) {
                return true;
            }
            return false;
        }

        if (o instanceof Tile) {
            if (this.getTile() == o) {
                return true;
            }
        }

        return false;

    }
    //</editor-fold>

    public static void referenceCanvas(Canvas ref) {
        canvas = ref;
        gc = canvas.getGraphicsContext2D();
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    //set fog of war cloud and shade to a static value
    static {
        shade = new ColorAdjust();
        shade.setBrightness(-0.75);

        cloudFill = new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOUD));
    }

}
