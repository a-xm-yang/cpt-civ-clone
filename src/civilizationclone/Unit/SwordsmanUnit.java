
package civilizationclone.Unit;

import civilizationclone.City;


public class SwordsmanUnit extends MeleeUnit{

    public SwordsmanUnit(City c) {
        super(2, c, 36, 2);
    }

    @Override
    public UnitType getUpgrade() {
        return UnitType.MUSKETMAN;
    }
    
}
