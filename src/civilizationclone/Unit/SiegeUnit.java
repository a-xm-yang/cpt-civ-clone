
package civilizationclone.Unit;

import civilizationclone.City;
import java.awt.Point;

public abstract class SiegeUnit extends MilitaryUnit{
    
    private int siegeCombat;

    public SiegeUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat, int siegeCombat) {
        super(MAX_MOVEMENT, c, MAX_HEALTH, combat);
        this.siegeCombat = siegeCombat;
    }
    
    @Override
    public void siegeAttack(City c){
        
    }
}
