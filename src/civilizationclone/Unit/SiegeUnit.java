package civilizationclone.Unit;

import civilizationclone.City;

public abstract class SiegeUnit extends MilitaryUnit {

    private int siegeCombat;

    public SiegeUnit(int MAX_MOVEMENT, City c, int combat, int siegeCombat, int maintainence) {
        super(MAX_MOVEMENT, c, combat, maintainence);
        this.siegeCombat = siegeCombat;
    }

    public SiegeUnit(int MAX_MOVEMENT, MilitaryUnit u, int combat, int siegeCombat, int maintainence) {
       super(u, MAX_MOVEMENT, combat, maintainence);
        this.siegeCombat = siegeCombat;
    }

    @Override
    public void siegeAttack(City c) {

        int siegeDmg = (int) (siegeCombat * 0.75);

        c.setHealth(c.getHealth() - siegeDmg);
        System.out.println("Unit dealt " + siegeDmg);

        if (c.getHealth() <= 0) {
            c.conquer(this.getPlayer());
        }
    }
    
}
