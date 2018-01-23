
package civilizationclone.Unit;

import civilizationclone.City;


public class MountedForceUnit extends CalvaryUnit{

    public MountedForceUnit(City c) {
        super(5, c, 62, 5);
    }

    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
