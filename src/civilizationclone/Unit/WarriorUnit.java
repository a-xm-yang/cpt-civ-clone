package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import static civilizationclone.Unit.Unit.getMapRef;
import java.awt.Point;
import java.util.ArrayList;

public class WarriorUnit extends MeleeUnit {

    public WarriorUnit(City c) {
        super(2, c, 30, 0);
    }

    public WarriorUnit(Player player, Point p) {
        super(2, player, p, 30, 0);
    }

    @Override
    public UnitType getUpgrade() {
        return UnitType.SWORDSMAN;
    }

}
