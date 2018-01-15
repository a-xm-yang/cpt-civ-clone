package civilizationclone.GUI;

import civilizationclone.Tile.*;
import civilizationclone.Unit.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 *
 * @author SGPSGPSGP
 */
public class DisplayTile extends Pane {

    private Polygon polygon;
    private Tile tile;
    private int x;
    private int y;
    static final double WIDTH = 100;
    static final double HEIGHT = 80;

    //Horribly inefficient, find a better way to do this before final version
    private static Image desert;
    private static Image hills;
    private static Image mountain;
    private static Image ocean;
    private static Image plains;
    private static Image image;

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

    static {
        try {
            desert = new Image(new FileInputStream("src/Assets/Tiles/Desert.png"), 100, 110, false, false);
            hills = new Image(new FileInputStream("src/Assets/Tiles/Hills.png"), 100, 110, false, false);
            mountain = new Image(new FileInputStream("src/Assets/Tiles/Mountain.png"), 100, 110, false, false);
            ocean = new Image(new FileInputStream("src/Assets/Tiles/Ocean.png"), 100, 110, false, false);
            plains = new Image(new FileInputStream("src/Assets/Tiles/Plains.png"), 100, 110, false, false);

            archer = new Image(new FileInputStream("src/Assets/Units/Archer.png"), 70, 70, false, false);
            builder = new Image(new FileInputStream("src/Assets/Units/Builder.png"), 70, 70, false, false);
            //catapult = new Image(new FileInputStream("src/Assets/Units/Catapult.png"), 70, 70, false, false);
            //destroyer = new Image(new FileInputStream("src/Assets/Units/Destroyer.png"), 70, 70, false, false);
            //galley = new Image(new FileInputStream("src/Assets/Units/Galley.png"), 70, 70, false, false);
            scout = new Image(new FileInputStream("src/Assets/Units/Scout.png"), 70, 70, false, false);
            //settler = new Image(new FileInputStream("src/Assets/Units/Settler.png"), 70, 70, false, false);
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

        polygon = new Polygon();
        Color c = Color.BLACK;
        if (tile instanceof Ocean) {
            //c = Color.NAVY;
            image = ocean;
        } else if (tile instanceof Plains) {
            //c = Color.GREEN;
            image = plains;
        } else if (tile instanceof Hills) {
            //c = Color.LIGHTGRAY;
            image = hills;
        } else if (tile instanceof Desert) {
            //c = Color.ORANGE;
            image = desert;
        } else if (tile instanceof Mountain) {
            //c = Color.GREY;
            image = mountain;
        } else {
            //c = Color.BLACK;
        }

        Canvas canvas = new Canvas(100, 110);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        polygon.setFill(c);
        polygon.setStroke(Color.GREEN);
        polygon.getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});

        this.getChildren().add(polygon);
        this.getChildren().add(canvas);
        gc.drawImage(image, 0, 0);

        if (tile.hasUnit()) {
            if (tile.getUnit() instanceof ArcherUnit) {
                gc.drawImage(archer, 15, 20);
            } else if (tile.getUnit() instanceof BuilderUnit) {
                gc.drawImage(builder, 15, 20);
            } else if (tile.getUnit() instanceof ScoutUnit) {
                gc.drawImage(scout, 15, 20);
            } else if (tile.getUnit() instanceof SettlerUnit) {
                gc.drawImage(settler, 15, 20);
            } else if (tile.getUnit() instanceof SlingerUnit) {
                gc.drawImage(slinger, 15, 20);
            } else if (tile.getUnit() instanceof WarriorUnit) {
                gc.drawImage(warrior, 15, 20);
            }
        }

        setOnMouseClicked(e -> {
            System.out.println("Dewit");
            polygon.setStroke(Color.RED);
        });
    }

    public static double getWIDTH() {
        return WIDTH;
    }

    public static double getHEIGHT() {
        return HEIGHT;
    }

    @Override
    public boolean equals(Object o) {
        if (x == ((DisplayTile)o).x && y == ((DisplayTile)o).y && this.tile == ((DisplayTile)o).tile){
            return true;
        }
        
        return false;
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
     
    

}
