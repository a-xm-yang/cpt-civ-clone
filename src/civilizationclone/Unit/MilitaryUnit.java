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

    public void heal() {
        health += 15;
        if (health > MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    public void attack(Unit x) {

        if (!(x instanceof MilitaryUnit)) {
            x.delete();
        }

        if (x instanceof MilitaryUnit) {

            MilitaryUnit enemy = (MilitaryUnit) x;
            int enemyCombat = enemy.combat;

            if (enemy instanceof RangeUnit) {
                enemyCombat = ((RangeUnit) enemy).closeCombat;
            }

            int thisDmg = (int) (combat * 0.6 * (health / MAX_HEALTH + (1 - (health / MAX_HEALTH) / 2)));
            int enemyDmg = (int) (enemyCombat * 0.6 * (enemy.health / enemy.MAX_HEALTH + (1 - (enemy.health / enemy.MAX_HEALTH) / 2)));

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

    public void siegeAttack(City c){
        
    };

}
