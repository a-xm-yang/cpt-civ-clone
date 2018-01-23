package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import java.awt.Point;

public abstract class MeleeUnit extends MilitaryUnit {

    public MeleeUnit(int MAX_MOVEMENT, City c, int combat, int maintainence) {
        super(MAX_MOVEMENT, c, combat, maintainence);
    }

    //OG spawning for warriors only
    public MeleeUnit(int MAX_MOVEMENT, Player player, Point p, int combat, int maintainence) {
        super(player, p, MAX_MOVEMENT, combat, maintainence);
    }

    public MeleeUnit(int MAX_MOVEMENT, MilitaryUnit u, int combat, int maintainence) {
        super(u, MAX_MOVEMENT, combat, maintainence);
    }

}
