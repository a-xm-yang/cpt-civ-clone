
package civilizationclone.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class UnitOption extends Rectangle{
    int x,y,w,h;
    //Rectangle rect;
    String optionType;

    public UnitOption(int x, int y, int w, int h, String optionType) {
        super(x,y,w,h);
        
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.optionType = optionType;
        
        
        
        this.setFill(Color.BLACK);
    }
    

    public Rectangle getRect() {
        return this;
    }

    public String getOptionType() {
        return optionType;
    }
    
}

    

