package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.GameMap;
import civilizationclone.Player;
import civilizationclone.TechType;
import civilizationclone.Tile.Tile;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Unit {

    private static GameMap mapRef;
    private final int MAX_MOVEMENT;
    private Player player;
    private int movement;
    private Point position;
    private boolean embarked;

    public Unit(int movement, City c) {
        MAX_MOVEMENT = movement;
        position = new Point(c.getPosition().x, c.getPosition().y);
        this.player = c.getPlayer();
        mapRef.getTile(position.x, position.y).setUnit(this);
        this.embarked = false;
    }

    public Unit(int movement, Player player, Point p) {
        MAX_MOVEMENT = movement;
        this.player = player;
        position = new Point(p.x, p.y);
        mapRef.getTile(position.x, position.y).setUnit(this);
        this.embarked = false;
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

    public static GameMap getMapRef() {
        return mapRef;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Point> getAdjacent() {
        return mapRef.getRange(position, 1);
    }

    public ArrayList<Point> getAdjacent(int range) {
        return mapRef.getRange(position, range);
    }

    public Point[] getMoves() {

        ArrayList<Point> list = getAdjacent();
        ArrayList<Point> moves = new ArrayList<>();

        for (Point p : list) {
            Tile t = mapRef.getTile(p.x, p.y);
            if (!t.hasUnit() && !t.hasCity() && movement >= t.getMovementCost()) {
                if (!t.isWater() || getPlayer().getOwnedTech().contains(TechType.SAILING)) {
                    moves.add(p);
                }
            }
        }

        return moves.toArray(new Point[moves.size()]);
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public void resetMovement() {
        this.movement = MAX_MOVEMENT;
    }

    public void move(Point p) {
        mapRef.getTile(position.x, position.y).removeUnit();
        position = p;
        mapRef.getTile(position.x, position.y).setUnit(this);
        movement -= mapRef.getTile(position.x, position.y).getMovementCost();

        if (!(this instanceof NavalUnit)) {
            if (!embarked) {
                if (mapRef.getTile(p).isWater()) {
                    embarked = true;
                }
            } else {
                if (!(mapRef.getTile(p).isWater())) {
                    embarked = false;
                }
            }
        }
    }

    public void delete() {
        getPlayer().getUnitList().remove(this);
        mapRef.getTile(getX(), getY()).removeUnit();

        player.calcGoldIncome();
    }

    public boolean hasEmbarked() {
        return embarked;
    }

    public boolean canMove() {
        if (movement == 0) {
            return false;
        }
        return true;
    }

    public static void referenceMap(GameMap m) {
        mapRef = m;
    }

    public int getMAX_MOVEMENT() {
        return MAX_MOVEMENT;
    }

    @Override
    public int hashCode() {

        String s = "";
        s += position.x;
        s += position.y;
        s += movement;
        s += player.getName();
        s += this.getClass().getSimpleName();

        return s.hashCode();
    }

    @Override
    public String toString() {
        String s = this.getClass().getSimpleName();
        return s.substring(0, s.indexOf("Unit"));
    }
    
    

}
