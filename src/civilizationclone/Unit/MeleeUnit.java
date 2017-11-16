
package civilizationclone.Unit;

import java.awt.Point;

public abstract class MeleeUnit extends MilitaryUnit{
    
    public MeleeUnit(int MAX_MOVEMENT, Point position, int player, int MAX_HEALTH, int combat) {
        super(MAX_MOVEMENT, position, player,MAX_HEALTH, combat);
    }
   
    
}
