
package civilizationclone.Unit;

import civilizationclone.City;

public abstract class MeleeUnit extends MilitaryUnit{
    
    public MeleeUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat) {
        super(MAX_MOVEMENT, c ,MAX_HEALTH, combat);
    }
   
}
