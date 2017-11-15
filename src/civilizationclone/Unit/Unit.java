package civilizationclone.Unit;

import java.awt.Point;

public abstract class Unit {

    private final int MAX_MOVEMENT;
    private int movement;
    private Point position;


    public Unit(int movement, Point p) {
        MAX_MOVEMENT = movement;
        movement = MAX_MOVEMENT;
        position = new Point(p.x, p.y);
    }

    public int getMovement() {
        return movement;
    }

    public int getX(){
        return position.x;
    }
    
    public int getY(){
        return position.y;
    }
    
    public void setPosition(Point p){
        position.x = p.x;
        position.y = p.y;
    }

    abstract void move();

}
