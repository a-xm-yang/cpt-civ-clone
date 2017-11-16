package civilizationclone.Unit;

import civilizationclone.City;

public class BuilderUnit extends Unit {

    private int actions;

    public BuilderUnit(City c) {
        super(3, c);
        actions = 3;
    }

    public void improveTile() {
        if (actions > 0) {
            Unit.getMapRef().map[getX()][getY()].improve();
            actions--;
        }
    }

}