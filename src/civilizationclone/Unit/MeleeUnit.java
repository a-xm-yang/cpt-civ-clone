
package civilizationclone.Unit;

import java.awt.Point;

public abstract class MeleeUnit extends MilitaryUnit{
    
    public MeleeUnit(int MAX_MOVEMENT, Point p, int MAX_HEALTH, int combat) {
        super(MAX_MOVEMENT, p, MAX_HEALTH, combat);
    }
   
    
}
