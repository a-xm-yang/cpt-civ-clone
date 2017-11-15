package civilizationclone.Unit;

import java.awt.Point;

public abstract class RangeUnit extends MilitaryUnit{
    
    int closeCombat;

    public RangeUnit(int MAX_MOVEMENT, Point p, int MAX_HEALTH, int combat) {
        super(MAX_MOVEMENT, p, MAX_HEALTH, combat);
    }
    
    
    
}