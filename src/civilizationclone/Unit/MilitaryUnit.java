package civilizationclone.Unit;

import civilizationclone.City;
import java.awt.Point;


public abstract class MilitaryUnit extends Unit {
    
    private int combat;
    
    private final int MAX_HEALTH;
    private int health;
    
    public MilitaryUnit(int MAX_MOVEMENT, Point p, int MAX_HEALTH, int combat) {
        super(MAX_MOVEMENT, p);
        this.MAX_HEALTH = MAX_HEALTH;
        health = MAX_HEALTH;
        this.combat = combat;
    }

    public int getHealth() {
        return health;
    }

    public int getCombat() {
        return combat;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    public abstract void attack (Unit x);
    
    public abstract void siegeAttack (City c);
    
}
