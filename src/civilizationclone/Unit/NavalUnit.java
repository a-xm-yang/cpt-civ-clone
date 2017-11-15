
package civilizationclone.Unit;

import java.awt.Point;

public abstract class NavalUnit extends MilitaryUnit{
    
    public NavalUnit(int MAX_MOVEMENT, Point p, int MAX_HEALTH, int combat) {
        super(MAX_MOVEMENT, p, MAX_HEALTH, combat);
    }
    
}
