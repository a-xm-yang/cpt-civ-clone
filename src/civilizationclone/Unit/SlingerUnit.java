package civilizationclone.Unit;

import civilizationclone.City;

public class SlingerUnit extends RangeUnit {

    private static int maintainence = 2;

    public SlingerUnit(City c) {
        //movement speed, city, health, range attack, close attack
        super(3, c, 80, 25, 10);
    }

    public static int getMaintainence() {
        return maintainence;
    }

}
