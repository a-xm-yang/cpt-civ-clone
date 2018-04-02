
package civilizationclone.Unit;

import civilizationclone.City;


public class BombardUnit extends SiegeUnit{

    public BombardUnit(City c) {
        super(2, c, 45, 65, 4);
    }

    @Override
    public UnitType getUpgrade() {
       return null;
    }
    
}
