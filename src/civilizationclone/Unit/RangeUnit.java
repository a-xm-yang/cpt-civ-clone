package civilizationclone.Unit;

import civilizationclone.City;

public abstract class RangeUnit extends MilitaryUnit{
    
    int closeCombat;

    public RangeUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat, int closeCombat) {
        super(MAX_MOVEMENT, c, MAX_HEALTH, combat);
        this.closeCombat = closeCombat;
    }
    
  
    
}