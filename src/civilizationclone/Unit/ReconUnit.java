package civilizationclone.Unit;

import civilizationclone.City;
import java.awt.Point;

public abstract class ReconUnit extends Unit {

    public ReconUnit(int movement, City c) {
        super(movement, c);
    }

    public ReconUnit(int movement, ReconUnit u) {
        super(movement, u.getPlayer(),new Point(u.getX(),u.getY()));
    }
    
}
