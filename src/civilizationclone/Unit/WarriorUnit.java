
package civilizationclone.Unit;

import civilizationclone.City;

public class WarriorUnit extends MeleeUnit{

    private static int maintainence = 2;
    
    public WarriorUnit(City c) {
        super(4, c, 100, 30);
    }

    public static int getMaintainence() {
        return maintainence;
    }

}
