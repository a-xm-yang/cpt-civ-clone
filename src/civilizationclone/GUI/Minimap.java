
package civilizationclone.GUI;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;


public class Minimap extends Pane{
    
    private ArrayList<DisplayTile> tiles;
    private Rectangle border;
    private int mapSize;
    private Canvas canvas;
    
    
    public Minimap(ZoomMap zoomMap, int resX, int resY){
        
        //requires a reference to initialize tiles
        mapSize = zoomMap.getMapSize();
        
        border = new Rectangle(300,300);
        border.setFill(Color.BLACK);
        border.setStrokeWidth(8);
        border.setStroke(Color.BEIGE);
        border.setStrokeLineJoin(StrokeLineJoin.ROUND);
        
        canvas = new Canvas(300,300);
        
        setTranslateX(resX - border.getWidth());
        setTranslateY(resY - border.getHeight());
        
        //initialize tiles list
        ArrayList<DisplayTile> temp = zoomMap.getTileList();
        tiles = new ArrayList<>();
        
        for (DisplayTile t: temp){
            if (!tiles.contains(t)){
                tiles.add(t);
            }
        }
        
        
        getChildren().addAll(border, canvas);
    }
    
    public void update(){
       
    }
    
}
 