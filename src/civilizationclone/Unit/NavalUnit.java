package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.GameMap;
import civilizationclone.Tile.Tile;
import java.awt.Point;
import java.util.ArrayList;

public abstract class NavalUnit extends MilitaryUnit {

    public NavalUnit(int MAX_MOVEMENT, City c, int combat, int maintainence) {
        super(MAX_MOVEMENT, c, combat, maintainence);
    }

    public NavalUnit(int MAX_MOVEMENT, MilitaryUnit u, int combat, int maintainence) {
        super(u, MAX_MOVEMENT, combat, maintainence);
    }

    @Override
    public Point[] getMoves() {

        ArrayList<Point> list = getAdjacent();
        ArrayList<Point> moves = new ArrayList<>();

        for (Point p : list) {
            Tile t = getMapRef().getTile(p.x, p.y);
            if (!t.hasUnit() && t.isWater() && getMovement() >= t.getMovementCost()) {
                moves.add(p);
            }
        }

        return moves.toArray(new Point[moves.size()]);
    }

    @Override
    public Point[] getAttackable() {
        ArrayList<Point> list = this.getAdjacent();
        ArrayList<Point> attackable = new ArrayList<Point>();

        //get a list of all the adjacent tiles, check to see ones that has a unit, is not water, and belongs to opposing players, removing everything else that's not
        for (Point p : list) {
            if (getMapRef().getTile(p.x, p.y).hasUnit()) {
                if (getMapRef().getTile(p.x, p.y).getUnit().getPlayer() != this.getPlayer()) {
                    if (getMapRef().getTile(p.x, p.y).isWater()) {
                        attackable.add(p);
                    }
                }
            }
        }

        return attackable.toArray(new Point[attackable.size()]);
    }
}
