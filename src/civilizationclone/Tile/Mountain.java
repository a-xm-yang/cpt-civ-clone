
package civilizationclone.Tile;

import static civilizationclone.Tile.Plains.possibleImprovement;
import java.util.EnumSet;
import java.util.Set;


public class Mountain extends Tile{
    
    static Set<Improvement> possibleImprovement = EnumSet.noneOf(Improvement.class);;

    
    public Mountain(){
        super(false, 999);
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
