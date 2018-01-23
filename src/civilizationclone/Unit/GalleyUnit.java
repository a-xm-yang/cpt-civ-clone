package civilizationclone.Unit;

import civilizationclone.City;

public class GalleyUnit extends NavalUnit {

    public GalleyUnit(City c) {
        super(3, c, 25, 1);
    }

    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
