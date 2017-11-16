
package civilizationclone.Unit;

import java.awt.Point;

public abstract class NavalUnit extends MilitaryUnit{
    
    public NavalUnit(int MAX_MOVEMENT, Point position, int player, int MAX_HEALTH, int combat) {
        super(MAX_MOVEMENT, position, player, MAX_HEALTH, combat);
    }
    
}
