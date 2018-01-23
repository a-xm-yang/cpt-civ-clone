
package civilizationclone.Unit;

import civilizationclone.City;


public class IroncladUnit extends NavalUnit {

    public IroncladUnit(City c) {
        super(5, c, 60, 5);
    }

    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
