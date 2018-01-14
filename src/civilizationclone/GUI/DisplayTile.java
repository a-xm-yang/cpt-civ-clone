package civilizationclone.GUI;

import civilizationclone.Tile.Desert;
import civilizationclone.Tile.Hills;
import civilizationclone.Tile.Mountain;
import civilizationclone.Tile.Ocean;
import civilizationclone.Tile.Plains;
import civilizationclone.Tile.Tile;
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
    
    Image desert;
    Image hills;
    Image mountain;
    Image ocean;
    Image plains ;
    Image image;

    public DisplayTile(Tile tile, int x, int y) throws FileNotFoundException {
        
        desert = new Image(new FileInputStream("src/Assets/Tiles/Desert.png"), 100, 110, false, false);
        hills = new Image(new FileInputStream("src/Assets/Tiles/Hills.png"), 100, 110, false, false);
        mountain = new Image(new FileInputStream("src/Assets/Tiles/Mountain.png"), 100, 110, false, false);
        ocean = new Image(new FileInputStream("src/Assets/Tiles/Ocean.png"), 100, 110, false, false);
        plains = new Image(new FileInputStream("src/Assets/Tiles/Plains.png"), 100, 110, false, false);

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
        
        if (tile.hasUnit()){
            c = Color.RED;
        }

        Canvas canvas = new Canvas(100, 100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        polygon.setFill(c);
        polygon.setStroke(Color.GREEN);
        polygon.getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});

        this.getChildren().add(polygon);
        this.getChildren().add(t);
        this.getChildren().add(canvas);
        gc.drawImage(image, 0, 0);
        
        

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
