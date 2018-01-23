
package civilizationclone.Unit;

import civilizationclone.City;


public class QuadriremeUnit extends NavalUnit{

    public QuadriremeUnit(City c) {
        super(3, c, 40, 1);
    }

    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
