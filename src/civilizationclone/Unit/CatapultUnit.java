
package civilizationclone.Unit;

import civilizationclone.City;


public class CatapultUnit extends SiegeUnit{

    public CatapultUnit(City c) {
        super(2, c, 25, 40, 2) ;
    }

    @Override
    public UnitType getUpgrade() {
        return UnitType.BOMBARD;
    }
    
}
