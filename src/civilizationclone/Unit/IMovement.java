package civilizationclone.Unit;

import java.awt.Point;


public interface IMovement {
    
    public void move(Point p);
    
    public void setMovement(int movement);
    
    public int getMovement();
    
    public boolean canMove();
}
