
package civilizationclone.Unit;

import civilizationclone.City;
import java.awt.Point;

public abstract class NavalUnit extends MilitaryUnit{
    
    public NavalUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat, int maintainence) {
        super(MAX_MOVEMENT, c, MAX_HEALTH, combat, maintainence);
    }
    
    public NavalUnit(int MAX_MOVEMENT, MilitaryUnit u, int MAX_HEALTH, int combat, int maintainence) {
       super(u, MAX_MOVEMENT, MAX_HEALTH, combat, maintainence);
    }
    
}
