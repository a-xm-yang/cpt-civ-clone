package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.GameMap;
import civilizationclone.Tile.Tile;
import java.awt.Point;
import java.util.ArrayList;

public abstract class NavalUnit extends MilitaryUnit {

    public NavalUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat, int maintainence) {
        super(MAX_MOVEMENT, c, MAX_HEALTH, combat, maintainence);
    }

    public NavalUnit(int MAX_MOVEMENT, MilitaryUnit u, int MAX_HEALTH, int combat, int maintainence) {
        super(u, MAX_MOVEMENT, MAX_HEALTH, combat, maintainence);
    }

    @Override
    public Point[] getMoves() {

        ArrayList<Point> list = this.getAdjacent();

        for (Point p : list) {
            Tile t = getMapRef().getTile(p.x, p.y);
            if (t.hasUnit() || !t.isWater()) {
                list.remove(p);
            }
        }

        return list.toArray(new Point[list.size()]);
    }

}
