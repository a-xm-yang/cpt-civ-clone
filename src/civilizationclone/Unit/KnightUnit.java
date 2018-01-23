package civilizationclone.Unit;

import civilizationclone.City;

public class KnightUnit extends CalvaryUnit {

    public KnightUnit(City c) {
        super(4, c, 48, 3);
    }

    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
