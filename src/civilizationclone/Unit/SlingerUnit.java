package civilizationclone.Unit;

import civilizationclone.City;

public class SlingerUnit extends RangeUnit {


    public SlingerUnit(City c) {
        super(3, c, 15, 5, 0);
    }

    public UnitType getUpgrade() {
        return UnitType.ARCHER;
    }

}
