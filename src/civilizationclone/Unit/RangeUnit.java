package civilizationclone.Unit;

import civilizationclone.City;

public abstract class RangeUnit extends MilitaryUnit {

    int closeCombat;

    public RangeUnit(int MAX_MOVEMENT, City c, int MAX_HEALTH, int combat, int closeCombat, int maintainence) {
        super(MAX_MOVEMENT, c, MAX_HEALTH, combat, maintainence);
        this.closeCombat = closeCombat;
    }

    public RangeUnit(int MAX_MOVEMENT, MilitaryUnit u, int MAX_HEALTH, int combat, int closeCombat, int maintainence) {
        super(u, MAX_MOVEMENT, MAX_HEALTH, combat, maintainence);
        this.closeCombat = closeCombat;
    }

    @Override
    public void attack(Unit x) {

        setMovement(0);

        if (!(x instanceof MilitaryUnit)) {
            x.delete();
        }

        if (x instanceof MilitaryUnit) {

            MilitaryUnit enemy = (MilitaryUnit) x;

            int thisDmg = (int) (getCombat() * 0.6 * (getHealthPercentage() / +(1 - getHealthPercentage() / 2)) * (getCombat() / enemy.getCombat()));

            enemy.setHealth(enemy.getHealth() - thisDmg);
            System.out.println("Unit dealt " + thisDmg);

            if (enemy.getHealth() <= 0) {
                enemy.delete();
            }

        }
    }

    @Override
    public void siegeAttack(City c) {

        int siegeDmg = (int) (getCombat() * 0.3);

        c.setHealth(c.getHealth() - siegeDmg);
        System.out.println("Unit dealt " + siegeDmg);

        if (c.getHealth() <= 0) {
            c.conquer(this.getPlayer());
        }
    }

}
