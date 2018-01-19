package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.GameMap;
import civilizationclone.Player;
import civilizationclone.Tile.Mountain;
import civilizationclone.Tile.Tile;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Unit implements IMovement {

    private static GameMap mapRef;
    private final int MAX_MOVEMENT;
    private Player player;
    private int movement;
    private Point position;

    public Unit(int movement, City c) {
        MAX_MOVEMENT = movement;
        position = new Point(c.getPosition().x, c.getPosition().y);
        this.player = c.getPlayer();
        mapRef.getTile(position.x, position.y).setUnit(this);
    }

    public Unit(int movement, Player player, Point p) {
        MAX_MOVEMENT = movement;
        this.player = player;
        position = new Point(p.x, p.y);
        mapRef.getTile(position.x, position.y).setUnit(this);
    }

    @Override
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
    
    public ArrayList<Point> getAdjacent(int range){
        return mapRef.getRange(position, range);
    }
    
    public Point[] getMoves(){
        
        ArrayList<Point> list = getAdjacent();
        ArrayList<Point> moves = new ArrayList<>();
        
        for (Point p: list){
            Tile t = mapRef.getTile(p.x, p.y);
            System.out.println(movement);
            if (!t.hasUnit() && !t.isWater() && movement >= t.getMovementCost()){
                moves.add(p);
            }
        }
        
        return moves.toArray(new Point[moves.size()]);
    }
    
    @Override
    public void setMovement(int movement) {
        this.movement = movement;
    }

    public void resetMovement() {
        this.movement = MAX_MOVEMENT;
    }

    @Override
    public void move(Point p) {
        mapRef.getTile(position.x, position.y).removeUnit();
        position = p;
        mapRef.getTile(position.x, position.y).setUnit(this);
        movement--;
    }

    public void delete() {
        getPlayer().getUnitList().remove(this);
        mapRef.getTile(getX(), getY()).removeUnit();
        System.out.println("This unit is dead");

        player.calcGoldIncome();
    }

    @Override
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
    
    

}