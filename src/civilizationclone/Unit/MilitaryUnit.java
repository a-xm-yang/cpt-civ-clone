package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import java.awt.Point;
import java.util.ArrayList;

public abstract class MilitaryUnit extends Unit {

    private int combat;
    private static int MAX_HEALTH = 100;
    private int health;
    private int maintainence;
    private boolean fortified;

    //constructor from city
    public MilitaryUnit(int MAX_MOVEMENT, City c, int combat, int maintainence) {
        super(MAX_MOVEMENT, c);
        health = MAX_HEALTH;
        this.combat = combat;
        this.maintainence = maintainence;
    }

    //overloaded constructor from OG spawn
    public MilitaryUnit(Player player, Point p, int MAX_MOVEMENT, int combat, int maintainence) {
        super(MAX_MOVEMENT, player, p);
        health = MAX_HEALTH;
        this.combat = combat;
        this.maintainence = maintainence;
    }

    //overloaded constructor from another unit upgrading
    public MilitaryUnit(MilitaryUnit u, int MAX_MOVEMENT, int combat, int maintainence) {
        super(MAX_MOVEMENT, u.getPlayer(), new Point(u.getX(), u.getY()));
        health = MAX_HEALTH;
        this.combat = combat;
        this.maintainence = maintainence;
    }

    public abstract UnitType getUpgrade();

    public void heal() {

        //heals more on friendly territory
        if (getMapRef().getTile(getX(), getY()).isControlled() && getMapRef().getTile(getX(), getY()).getControllingCity().getPlayer() == getPlayer()) {
            health += 15;
        } else {
            health += 10;
        }

        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    @Override
    public void move(Point p) {
        super.move(p);
        fortified = false;
    }

    //filters out a list of attackables
    public Point[] getAttackable() {

        ArrayList<Point> list = this.getAdjacent();
        ArrayList<Point> attackable = new ArrayList<Point>();

        if (!hasEmbarked()) {
            //get a list of all the adjacent tiles, check to see ones that has a unit, is not water, and belongs to opposing players, removing everything else that's not
            for (Point p : list) {
                if (getMapRef().getTile(p.x, p.y).hasUnit()) {
                    if (getMapRef().getTile(p.x, p.y).getUnit().getPlayer() != this.getPlayer()) {
                        if (!getMapRef().getTile(p.x, p.y).isWater()) {
                            attackable.add(p);
                        }
                    }
                }
            }
        }

        return attackable.toArray(new Point[attackable.size()]);
    }

    // attacks things 
    public void attack(Unit x) {

        setMovement(0);
        fortified = false;

        if (!(x instanceof MilitaryUnit)) {
            x.delete();
        }

        if (x instanceof MilitaryUnit) {

            //Calculate damages
            MilitaryUnit enemy = (MilitaryUnit) x;
            int enemyCombat = enemy.combat;

            if (enemy instanceof RangeUnit) {
                enemyCombat = ((RangeUnit) enemy).closeCombat;
            }
            
            if (enemy.fortified){
                enemyCombat += 3;
            }

            int thisDmg = (int) ((30 * Math.pow(1.041, (combat - enemyCombat))) * getDamageReduction());
            int enemyDmg = (int) ((30 * Math.pow(1.041, (enemyCombat - combat))) * enemy.getDamageReduction());

            if (enemy.hasEmbarked()) {
                enemyDmg = 0;
            }

            if (this instanceof MeleeUnit && enemy instanceof CalvaryUnit) {
                thisDmg = (int) (thisDmg * 1.2);
                enemyDmg = (int) (enemyDmg * 0.8);
            } else if (this instanceof CalvaryUnit && enemy instanceof MeleeUnit) {
                thisDmg = (int) (thisDmg * 0.8);
                enemyDmg = (int) (enemyDmg * 1.2);
            } else if (this instanceof CalvaryUnit && enemy instanceof RangeUnit) {
                thisDmg = (int) (thisDmg * 1.15);
                enemyDmg = (int) (enemyDmg * 0.8);
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

    //filters out a list of siegables
    public Point[] getSiegable() {
        ArrayList<Point> list = this.getAdjacent();
        ArrayList<Point> attackable = new ArrayList<Point>();

        //get a list of all the adjacent tiles, check to see ones that has a city, is not water, and belongs to opposing players, removing everything else that's not
        for (Point p : list) {
            if (getMapRef().getTile(p.x, p.y).hasCity()) {
                if (getMapRef().getTile(p.x, p.y).getCity().getPlayer() != this.getPlayer()) {
                    attackable.add(p);
                }
            }
        }

        return attackable.toArray(new Point[attackable.size()]);
    }

    // seiges atack 
    public void siegeAttack(City c) {

        fortified = false;

        int siegeDmg = (int) ((30 * Math.pow(1.041, (combat - c.getCombat()))) * getDamageReduction() * 0.70);
        int cityDmg = (int) ((30 * Math.pow(1.041, (c.getCombat() - combat))) * getDamageReduction());

        if (this instanceof CalvaryUnit) {
            siegeDmg = (int) (siegeDmg * 0.6);
        }

        if (this instanceof NavalUnit) {
            siegeDmg = (int) (siegeDmg * 1.3);
        }

        health -= cityDmg;
        System.out.println("Enemy dealt " + cityDmg);
        c.setHealth(c.getHealth() - siegeDmg);
        System.out.println("Unit dealt " + siegeDmg);

        if (c.getHealth() <= 0) {
            c.conquer(getPlayer());
        }

        if (health <= 0) {
            this.delete();
        }

        this.setMovement(0);

    }

    // SIMPLE SETTER AND GETTERS
    //<editor-fold>
    public void setFortified(boolean fortified) {
        this.fortified = fortified;
    }

    public boolean isFortified() {
        return fortified;
    }

    public int getHealth() {
        return health;
    }

    public double getDamageReduction() {
        Double d = getHealthPercentage();

        if (d < 0.333) {
            return 0.333;
        } else {
            return d;
        }
    }

    public double getHealthPercentage() {
        return (health * 1.0) / MAX_HEALTH;
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

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    public void setMaintainence(int maintainence) {
        this.maintainence = maintainence;
    }

    //</editor-fold>
}
