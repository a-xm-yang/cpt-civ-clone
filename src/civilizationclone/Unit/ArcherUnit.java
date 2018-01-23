package civilizationclone.Unit;

import civilizationclone.City;
    
public class ArcherUnit extends RangeUnit{
    
    public ArcherUnit(City c) {
        super(2, c, 25, 15, 1);
    }

    @Override
    public UnitType getUpgrade() {
        return UnitType.CROSSBOWMAN;
    }
}
