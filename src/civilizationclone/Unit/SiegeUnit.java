
package civilizationclone.Unit;

import java.awt.Point;


public abstract class SiegeUnit extends MilitaryUnit{
    
    private int siegeCombat;

    public SiegeUnit(int MAX_MOVEMENT, Point p, int MAX_HEALTH, int combat, int siegeCombat) {
        super(MAX_MOVEMENT, p, MAX_HEALTH, combat);
        this.siegeCombat = siegeCombat;
    }
    
    
}
