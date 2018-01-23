
package civilizationclone.Unit;

import civilizationclone.City;


public class MusketmanUnit extends MeleeUnit {

    public MusketmanUnit(City c) {
        super(2, c, 55, 4);
    }

    @Override
    public UnitType getUpgrade() {
        return UnitType.INFANTRY;
    }
    
}
