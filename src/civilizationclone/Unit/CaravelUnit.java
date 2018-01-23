
package civilizationclone.Unit;

import civilizationclone.City;


public class CaravelUnit extends NavalUnit{

    public CaravelUnit(City c) {
        super(4, c, 50, 4);
    }

    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
