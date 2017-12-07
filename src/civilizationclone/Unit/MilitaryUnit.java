package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import java.awt.Point;
import java.util.ArrayList;

public abstract class MilitaryUnit extends Unit {

    private int combat;
    private final int MAX_HEALTH;
    private int health;
    private int maintainence;

    //constructor from city
    public MilitaryUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat, int maintainence) {
        super(MAX_MOVEMENT, c);
        this.MAX_HEALTH = MAX_HEALTH;
        health = MAX_HEALTH;
        this.combat = combat;
        this.maintainence = maintainence;
    }

    //overloaded constructor from OG spawn
    public MilitaryUnit(Player player, Point p, int MAX_MOVEMENT, int MAX_HEALTH, int combat, int maintainence) {
        super(MAX_MOVEMENT, player, p);
        this.MAX_HEALTH = MAX_HEALTH;
        health = MAX_HEALTH;
        this.combat = combat;
        this.maintainence = maintainence;
    }

    //overloaded constructor from another unit upgrading
    public MilitaryUnit(MilitaryUnit u, int MAX_MOVEMENT, int MAX_HEALTH, int combat, int maintainence) {
        super(MAX_MOVEMENT, u.getPlayer(), new Point(u.getX(), u.getY()));
        this.MAX_HEALTH = MAX_HEALTH;
        health = MAX_HEALTH;
        this.combat = combat;
        this.maintainence = maintainence;
    }

    public abstract UnitType getUpgrade();

    public int getHealth() {
        return health;
    }

    public double getHealthPercentage() {
        return health / MAX_HEALTH;
    }

    public int getCombat() {
        return combat;
    }

    public int getMaintainence() {
        return maintainence;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCombat(int combat) {
        this.combat = combat;
    }

    public void heal() {
        health += 15;
        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }
    
    public Point[] getAttackable(){
        
        ArrayList<Point> list = this.getAdjacent();
        ArrayList<Point> attackable = new ArrayList<Point>();
        
        //get a list of all the adjacent tiles, check to see ones that has a unit, is not water, and belongs to opposing players, removing everything else that's not
        for (Point p: list){
            if (getMapRef().getTile(p.x, p.y).hasUnit()){
                if (getMapRef().getTile(p.x, p.y).getUnit().getPlayer() != this.getPlayer()){
                    if(!getMapRef().getTile(p.x, p.y).isWater()){
                        attackable.add(p);
                    }
                }
            } 
        }
        
        return attackable.toArray(new Point[attackable.size()]);
    }

    // attacks things 
    public void attack(Unit x) {

        setMovement(0);

        if (!(x instanceof MilitaryUnit)) {
            x.delete();
        }

        if (x instanceof MilitaryUnit) {

            MilitaryUnit enemy = (MilitaryUnit) x;
            int enemyCombat = enemy.combat;

            if (enemy instanceof RangeUnit) {
                enemyCombat = ((RangeUnit) enemy).closeCombat;
            }

            int thisDmg = (int) (combat * 0.6 * (getHealthPercentage() + (1 - getHealthPercentage() / 2)) * (combat / enemyCombat));
            int enemyDmg = (int) (enemyCombat * 0.6 * (enemy.getHealthPercentage() + (1 - enemy.getHealthPercentage() / 2)) * (enemyCombat / combat));

            if (this instanceof MeleeUnit && enemy instanceof CalvaryUnit) {
                thisDmg = (int) (thisDmg * 1.2);
                enemyDmg = (int) (enemyDmg * 0.8);
            } else if (this instanceof CalvaryUnit && enemy instanceof MeleeUnit) {
                thisDmg = (int) (thisDmg * 0.8);
                enemyDmg = (int) (enemyDmg * 1.2);
            }

            health -= enemyDmg;
            System.out.println("Enemy dealt " + enemyDmg);
            enemy.health -= thisDmg;
            System.out.println("Unit dealt " + thisDmg);

            if (health <= 0) {
                this.delete();
            }
            if (enemy.health <= 0) {
                enemy.delete();
            }

        }

    }

    // seiges atack 
    public void siegeAttack(City c) {

        int siegeDmg = (int) (combat * 0.4);
        int cityDmg = (int) (c.getCombat() * 0.6);

        if (this instanceof CalvaryUnit) {
            siegeDmg = (int) (siegeDmg * 0.6);
        }

        health -= cityDmg;
        System.out.println("Enemy dealt " + cityDmg);
        c.setHealth(c.getHealth() - siegeDmg);
        System.out.println("Unit dealt " + siegeDmg);

        if (c.getHealth() <= 0) {
            c.conquer(this.getPlayer());
        }

        if (health <= 0) {
            this.delete();
        }

    }

}
