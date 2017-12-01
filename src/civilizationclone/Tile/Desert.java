
package civilizationclone.Tile;

import java.util.EnumSet;
import java.util.Set;

public class Desert extends Tile{

    static Set<Improvement> possibleImprovement = EnumSet.of(Improvement.FARM, Improvement.RANCH, Improvement.ROAD);
    
    public Desert() {
        super(false, 1);
        
    }

    @Override
    public void improve() {
        this.setImprovement(Improvement.FARM);
        super.improve();
    }

    @Override
    public void calcOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
