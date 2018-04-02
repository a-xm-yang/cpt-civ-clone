
package civilizationclone.Unit;

import civilizationclone.City;

public class ScoutUnit extends MilitaryUnit {

    public ScoutUnit(City c) {
        super(3, c, 10, 0);
    }

    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
