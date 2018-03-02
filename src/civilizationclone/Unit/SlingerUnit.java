package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import java.awt.Point;

public class SlingerUnit extends RangeUnit {

    public SlingerUnit(City c) {
        super(2, c, 15, 5, 0);
    }

    public UnitType getUpgrade() {
        return UnitType.ARCHER;
    }

}
