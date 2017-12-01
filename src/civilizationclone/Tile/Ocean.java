
package civilizationclone.Tile;

import static civilizationclone.Tile.Plains.possibleImprovement;
import java.util.EnumSet;
import java.util.Set;

public class Ocean extends Tile{
    
    static Set<Improvement> possibleImprovement = EnumSet.of(Improvement.FISHING);;
    
    public Ocean(){
        
        super(true, 1);
    }

    @Override
    public void improve() {
        this.setImprovement(Improvement.FISHING);
        super.improve();
    }

    @Override
    public void calcOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Set<Improvement> getImprovements(){
        Set<Improvement> improve = possibleImprovement;
        improve.add(this.getResource().getImprovementType());
        return improve;
    }
    
    
}
