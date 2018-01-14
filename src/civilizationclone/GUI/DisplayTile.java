package civilizationclone.GUI;

import civilizationclone.Tile.*;
import civilizationclone.Unit.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    Polygon polygon;
    Tile tile;
    int x;
    int y;
    static final double WIDTH = 100;
    static final double HEIGHT = 80;
    
    //Horribly inefficient, find a better way to do this before final version
    
    Image desert;
    Image hills;
    Image mountain;
    Image ocean;
    Image plains ;
    Image image;
    
    Image archer;
    Image builder;
    Image catapult;
    Image destroyer;
    Image galley;
    Image scout;
    Image settler;
    Image swordsman;
    Image slinger;
    Image warrior;

    public DisplayTile(Tile tile, int x, int y) throws FileNotFoundException {
        
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
        }  else {
            //c = Color.BLACK;
        }
        
        Canvas canvas = new Canvas(100, 110);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        polygon.setFill(c);
        polygon.setStroke(Color.GREEN);
        polygon.getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});

        this.getChildren().add(polygon);
        this.getChildren().add(t);
        this.getChildren().add(canvas);
        gc.drawImage(image, 0, 0);
        
        if (tile.hasUnit()){
            if(tile.getUnit() instanceof ArcherUnit){
                gc.drawImage(archer, 15, 20);
            }else if(tile.getUnit() instanceof BuilderUnit){
                gc.drawImage(builder, 15, 20);
            }else if(tile.getUnit() instanceof ScoutUnit){
                gc.drawImage(scout, 15, 20);
            }else if(tile.getUnit() instanceof SettlerUnit){
                gc.drawImage(settler, 15, 20);
            }else if(tile.getUnit() instanceof SlingerUnit){
                gc.drawImage(slinger, 15, 20);
            }else if(tile.getUnit() instanceof WarriorUnit){
                gc.drawImage(warrior, 15, 20);
            }
        }
        
        
        

        this.setOnMouseClicked(e -> {

            //if click is not at center ignore
            System.out.println("Dewit");
            t.setStroke(Color.RED);
        });
    }

    public static double getWIDTH() {
        return WIDTH;
    }

    public static double getHEIGHT() {
        return HEIGHT;
    }

}
