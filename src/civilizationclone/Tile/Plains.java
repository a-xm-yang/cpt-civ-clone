
package civilizationclone.Tile;

import java.util.EnumSet;
import java.util.Set;

public class Plains extends Tile {
    
    static Set<Improvement> possibleImprovement = EnumSet.of(Improvement.FARM, Improvement.ROAD, Improvement.ACADEMY);

    public Plains(){
        super(false, 1);
    }
    
    @Override
    public void calcOutput() {
        setFoodOutput(2 + getImprovement().getFoodBonus() + getResource().getFoodBonus());
        setGoldOutput(getImprovement().getGoldBonus() + getResource().getGoldBonus());
        setProductionOutput(getImprovement().getProductionBonus() + getResource().getProductionBonus());
        setScienceOutput(getImprovement().getScienceBonus() + getResource().getTechBonus());
    }
    
    public Set<Improvement> getImprovements(){
        Set<Improvement> improve = possibleImprovement;
        improve.add(this.getResource().getImprovementType());
        return improve;
    }
}
