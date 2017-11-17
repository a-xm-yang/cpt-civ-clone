package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Map;
import civilizationclone.Player;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Unit {

    private static Map mapRef;
    private final int MAX_MOVEMENT;
    private Player player;
    private int movement;
    private Point position;

    public Unit(int movement, City c) {
        MAX_MOVEMENT = movement;
        movement = MAX_MOVEMENT;
        position = new Point(c.getPosition().x, c.getPosition().y);
        this.player = c.getPlayer();
    }

    public int getMovement() {
        return movement;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public static Map getMapRef() {
        return mapRef;
    }

    public Player getPlayer() {
        return player;
    }

    public Point[] getAdjacent() {

        ArrayList<Point> list = new ArrayList<Point>();

        int dif = 0;
        if (position.x % 2 == 0) {
            dif = 1;
        }

        for (int i = 0; i < 2; i++) {
            if (!mapRef.map[position.x - 1][position.y - dif + i].isIsWater()) {
                list.add(new Point(position.x - 1, position.y - dif + i));
            }
        }

        for (int i = -1; i < 2; i += 2) {
            if (!mapRef.map[position.x][position.y + i].isIsWater()) {
                list.add(new Point(position.x, position.y + i));
            }
        }

        for (int i = 0; i < 2; i++) {
            if (!mapRef.map[position.x + 1][position.y - dif + i].isIsWater()) {
                list.add(new Point(position.x + 1, position.y - dif + i));
            }
        }

        return list.toArray(new Point[list.size()]);

    }

    public void move(Point p) {
        mapRef.map[position.x][position.y].removeUnit();
        position = p;
        mapRef.map[position.x][position.y].setUnit(this);
    }

    public void delete() {
        getPlayer().getUnitList().remove(this);
        getMapRef().map[getX()][getY()].removeUnit();
        System.out.println("This unit is dead");
    }

    public boolean canMove() {
        if (movement == 0) {
            return false;
        }
        return true;
    }

    public static void referenceMap(Map m) {
        mapRef = m;
    }

}
