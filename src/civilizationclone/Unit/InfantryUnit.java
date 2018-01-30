
package civilizationclone.Unit;

import civilizationclone.City;


public class InfantryUnit extends MeleeUnit{

    public InfantryUnit(City c) {
        super(2, c, 75, 6);
    }

    @Override
    public UnitType getUpgrade() {
       return null;
    }
    
}
