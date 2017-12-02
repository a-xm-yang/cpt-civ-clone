
package civilizationclone.Tile;

import java.util.EnumSet;
import java.util.Set;

public class Plains extends Tile {
    
    static Set<Improvement> possibleImprovement = EnumSet.of(Improvement.FARM, Improvement.ROAD, Improvement.ACADEMY);

    public Plains(){
        
        super(false, 1);
    }
    
    //TODO decide if resources will be determined when tile is created, or when map is created



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
