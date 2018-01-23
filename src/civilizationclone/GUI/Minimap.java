
package civilizationclone.GUI;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;


public class Minimap extends Pane{
    
    private ArrayList<DisplayTile> tiles;
    private Rectangle border;
    private int mapSize;
    
    
    public Minimap(ZoomMap zoomMap, int resX, int resY){
        
        //requires a reference to initialize tiles
        mapSize = zoomMap.getMapSize();
        
        border = new Rectangle(300,300);
        border.setFill(Color.BLACK);
        border.setStrokeWidth(5);
        border.setStroke(Color.BEIGE);
        border.setStrokeLineJoin(StrokeLineJoin.ROUND);
        
        setTranslateX(resX - border.getWidth());
        setTranslateY(resY - border.getHeight());
        
        //initialize tiles list
        ArrayList<DisplayTile> temp = zoomMap.getTileList();
        tiles = new ArrayList<>();
        
        
        
        
        getChildren().addAll(border);
    }
    
    public void update(){
       
    }
    
}
 