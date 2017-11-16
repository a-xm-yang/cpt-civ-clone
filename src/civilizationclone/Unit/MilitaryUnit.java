package civilizationclone.Unit;

import civilizationclone.City;

public abstract class MilitaryUnit extends Unit {
    
    private int combat;
    private final int MAX_HEALTH;
    private int health;
    
    public MilitaryUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat) {
        super(MAX_MOVEMENT, c);
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
