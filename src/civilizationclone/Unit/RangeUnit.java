package civilizationclone.Unit;

import civilizationclone.City;
import java.awt.Point;
import java.util.ArrayList;

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
    public Point[] getAttackable() {

        ArrayList<Point> list = getAdjacent(3);
        ArrayList<Point> attackable = new ArrayList<Point>();

        //get a list of all the adjacent tiles, check to see ones that has a unit, is not water, and belongs to opposing players, removing everything else that's not
        for (Point p : list) {
            if (getMapRef().getTile(p.x, p.y).hasUnit()) {
                if (getMapRef().getTile(p.x, p.y).getUnit().getPlayer() != this.getPlayer()) {
                    attackable.add(p);
                }
            }
        }

        return attackable.toArray(new Point[attackable.size()]);
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
    public Point[] getSiegable() {
        ArrayList<Point> list = this.getAdjacent(3);
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
