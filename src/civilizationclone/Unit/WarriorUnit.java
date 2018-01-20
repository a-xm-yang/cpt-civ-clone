package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import java.awt.Point;

public class WarriorUnit extends MeleeUnit {

    public WarriorUnit(City c) {
        super(45, c, 100, 30, 0);
    }

    public WarriorUnit(Player player, Point p) {
        super(45, player, p, 100, 30, 0);
    }

    @Override
    public UnitType getUpgrade() {
        return null;
    }

}
