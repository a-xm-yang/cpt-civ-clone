
package civilizationclone.Tile;

import java.util.EnumSet;
import java.util.Set;

public class Hills extends Tile{
    
    static Set<Improvement> possibleImprovement = EnumSet.of(Improvement.MINE, Improvement.ROAD);;
    
    public Hills(){
        super(false, 2);

    }

    @Override
    public void calcOutput() {
    
    }
    
    public Set<Improvement> getImprovements(){
        Set<Improvement> improve = possibleImprovement;
        improve.add(this.getResource().getImprovementType());
        return improve;
    }
    
    
}
