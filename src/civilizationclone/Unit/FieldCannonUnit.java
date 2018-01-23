
package civilizationclone.Unit;

import civilizationclone.City;


public class FieldCannonUnit extends RangeUnit{

    public FieldCannonUnit(City c) {
        super(2, c, 60, 50, 5);
    }

    @Override
    public UnitType getUpgrade() {
        return null;
    }
    
}
