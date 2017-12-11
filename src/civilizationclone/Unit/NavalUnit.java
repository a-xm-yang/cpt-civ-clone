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

    @Override
    public Point[] getSiegable() {
        ArrayList<Point> list = this.getAdjacent();
        ArrayList<Point> attackable = new ArrayList<Point>();

        //get a list of all the adjacent tiles, check to see ones that has a city, and belongs to opposing players, removing everything else that's not
        for (Point p : list) {
            if (getMapRef().getTile(p.x, p.y).hasCity()) {
                if (getMapRef().getTile(p.x, p.y).getCity().getPlayer() != this.getPlayer()) {
                    attackable.add(p);
                }
            }
        }

        return attackable.toArray(new Point[attackable.size()]);
    }
}


