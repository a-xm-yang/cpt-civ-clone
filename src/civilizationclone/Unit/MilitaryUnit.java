package civilizationclone.Unit;

import civilizationclone.City;

public abstract class MilitaryUnit extends Unit {

    private int combat;
    private final int MAX_HEALTH;
    private int health;
    private int maintainence;

    public MilitaryUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat, int maintainence) {
        super(MAX_MOVEMENT, c);
        this.MAX_HEALTH = MAX_HEALTH;
        health = MAX_HEALTH;
        this.combat = combat;
        this.maintainence = maintainence;
    }

    public int getHealth() {
        return health;
    }
    
    public double getHealthPercentage(){
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

    public void siegeAttack(City c) {

        int siegeDmg = (int) (combat * 0.4);
        int cityDmg = (int) (c.getCombat() * 0.6);
        
        if (this instanceof CalvaryUnit){
            siegeDmg = (int) (siegeDmg * 0.6);
        }

        health -= cityDmg;
        System.out.println("Enemy dealt " + cityDmg);
        c.setHealth(c.getHealth() - siegeDmg); 
        System.out.println("Unit dealt " + siegeDmg);
        
        if (c.getHealth() <= 0){
            c.delete();
        }
        
        if (health <= 0){
            this.delete();
        }
        
    }
;

}
