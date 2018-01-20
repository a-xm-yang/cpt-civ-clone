
package civilizationclone.Unit;

import civilizationclone.City;


public class HorsemanUnit extends MeleeUnit {
    
    public HorsemanUnit(City c) {
        super(4, c, 100, 30, 0);
    }
    
    @Override
    public UnitType getUpgrade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
