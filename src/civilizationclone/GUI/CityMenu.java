
package civilizationclone.GUI;

import civilizationclone.City;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CityMenu extends Pane{
    
    int resX, resY;
    City city;
    Rectangle rect;

    public CityMenu(City city, int resX, int resY) {
        this.resX = resX;
        this.resY = resY;
        this.city = city;
        
        rect = new Rectangle(0, 0, 100, 100);
        

        rect.setFill(Color.BLACK);
        
        this.getChildren().add(rect);
        this.setVisible(true);
    }

    public Rectangle getRect() {
        return rect;
    }
    
    
    
    
    
}
