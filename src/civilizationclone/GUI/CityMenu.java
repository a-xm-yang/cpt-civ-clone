
package civilizationclone.GUI;

import civilizationclone.City;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CityMenu extends Pane{
    
    int resX, resY;
    City city;

    public CityMenu(City city, int resX, int resY) {
        this.resX = resX;
        this.resY = resY;
        this.city = city;
        
        Rectangle rect = new Rectangle(resX - 200, 0, 200, resY);
        rect.setFill(Color.BLACK);
        
        this.getChildren().add(rect);
    }
    
    
    
}
