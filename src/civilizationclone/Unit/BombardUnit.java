
package civilizationclone.Unit;

import civilizationclone.City;


public class BombardUnit extends SiegeUnit{

    public BombardUnit(City c) {
        super(2, c, 45, 55, 4);
    }

    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}