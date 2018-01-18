package civilizationclone.GUI;

import civilizationclone.City;
import civilizationclone.Tile.*;
import civilizationclone.Unit.*;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class DisplayTile extends Polygon {

    //member variables
    //<editor-fold>
//    private Polygon polygon;
    private Tile tile;
    private static Canvas canvas;
    //x y position of the tile
    private int x;
    private int y;
    private boolean highlighted = false;
    private boolean greenHighlighted = false;
    private boolean blueHighlighted = false;

    static final double WIDTH = 102;
    static final double HEIGHT = 82;
    //</editor-fold>

    //resources and variables for image loading
    //<editor-fold>
    private static Image desert;
    private static Image hills;
    private static Image mountain;
    private static Image ocean;
    private static Image plains;
    private static Image image;
    private static Image city;

    private static Image archer;
    private static Image builder;
    private static Image catapult;
    private static Image destroyer;
    private static Image galley;
    private static Image scout;
    private static Image settler;
    private static Image swordsman;
    private static Image slinger;
    private static Image warrior;
    //</editor-fold>

    //staticly loading images
    static {
        try {
            desert = new Image(new FileInputStream("src/Assets/Tiles/Desert.png"), 100, 110, false, false);
            hills = new Image(new FileInputStream("src/Assets/Tiles/Hills.png"), 100, 110, false, false);
            mountain = new Image(new FileInputStream("src/Assets/Tiles/Mountain.png"), 100, 110, false, false);
            ocean = new Image(new FileInputStream("src/Assets/Tiles/Ocean.png"), 100, 110, false, false);
            plains = new Image(new FileInputStream("src/Assets/Tiles/Plains.png"), 100, 110, false, false);
            city = new Image(new FileInputStream("src/Assets/Tiles/City.png"), 90, 90, false, false);

            archer = new Image(new FileInputStream("src/Assets/Units/Archer.png"), 70, 70, false, false);
            builder = new Image(new FileInputStream("src/Assets/Units/Builder.png"), 70, 70, false, false);
            //catapult = new Image(new FileInputStream("src/Assets/Units/Catapult.png"), 70, 70, false, false);
            //destroyer = new Image(new FileInputStream("src/Assets/Units/Destroyer.png"), 70, 70, false, false);
            //galley = new Image(new FileInputStream("src/Assets/Units/Galley.png"), 70, 70, false, false);
            scout = new Image(new FileInputStream("src/Assets/Units/Scout.png"), 70, 70, false, false);
            settler = new Image(new FileInputStream("src/Assets/Units/Settler.png"), 70, 70, false, false);
            swordsman = new Image(new FileInputStream("src/Assets/Units/Swordsman.png"), 70, 70, false, false);
            slinger = new Image(new FileInputStream("src/Assets/Units/Slinger.png"), 70, 70, false, false);
            warrior = new Image(new FileInputStream("src/Assets/Units/Warrior.png"), 70, 70, false, false);
        } catch (IOException e) {
            System.out.println("Image loading failed");
            System.out.println(e.getStackTrace());
        }
    }

    public DisplayTile(Tile tile, int x, int y) {

        //tile reference
        this.tile = tile;
        this.x = x;
        this.y = y;

        setStroke(Color.BLACK);
        getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});
        if (tile instanceof Ocean) {
            image = ocean;
        } else if (tile instanceof Plains) {
            image = plains;
        } else if (tile instanceof Hills) {
            image = hills;
        } else if (tile instanceof Desert) {
            image = desert;
        } else if (tile instanceof Mountain) {
            image = mountain;
        }
        setFill(new ImagePattern(image));
        setStrokeWidth(3);

        update();

    }

    public void update() {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        if (tile.hasUnit()) {
            if (tile.getUnit() instanceof ArcherUnit) {
                gc.drawImage(archer, 15 + getTranslateX(), 20 + getTranslateY());
            } else if (tile.getUnit() instanceof BuilderUnit) {
                gc.drawImage(builder, 15 + getTranslateX(), 20 + getTranslateY());
            } else if (tile.getUnit() instanceof ScoutUnit) {
                gc.drawImage(scout, 15 + getTranslateX(), 20 + getTranslateY());
            } else if (tile.getUnit() instanceof SettlerUnit) {
                gc.drawImage(settler, 15 + getTranslateX(), 20 + getTranslateY());
            } else if (tile.getUnit() instanceof SlingerUnit) {
                gc.drawImage(slinger, 15 + getTranslateX(), 20 + getTranslateY());
            } else if (tile.getUnit() instanceof WarriorUnit) {
                gc.drawImage(warrior, 15 + getTranslateX(), 20 + getTranslateY());
            }
        }

        if (tile.hasCity()) {
            gc.drawImage(city, 5 + getTranslateX(), 10 + getTranslateY());
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
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

}
