package civilizationclone.Unit;

import civilizationclone.City;
    
public class ArcherUnit extends RangeUnit{
    
    public ArcherUnit(City c) {
        super(3, c, 100, 40, 15, 1);
    }

    @Override
    public UnitType getUpgrade() {
        return null;
    }
}
