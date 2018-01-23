
package civilizationclone.Unit;

import civilizationclone.City;


public class CrossbowmanUnit extends RangeUnit {

    public CrossbowmanUnit(City c) {
        super(2, c, 40, 30, 3);
    }

    @Override
    public UnitType getUpgrade() {
        return UnitType.FIELDCANNON;
    }
    
}
