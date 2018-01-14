package civilizationclone.GUI;

import civilizationclone.Tile.Desert;
import civilizationclone.Tile.Hills;
import civilizationclone.Tile.Ocean;
import civilizationclone.Tile.Plains;
import civilizationclone.Tile.Tile;
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

    public static double getWIDTH() {
        return WIDTH;
    }

    public static double getHEIGHT() {
        return HEIGHT;
    }

}
