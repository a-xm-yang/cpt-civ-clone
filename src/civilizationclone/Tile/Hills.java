package civilizationclone.Tile;

import java.util.EnumSet;
import java.util.Set;

public class Hills extends Tile {

    static Set<Improvement> possibleImprovement = EnumSet.of(Improvement.MINE, Improvement.ROAD);

    
    public Hills() {
        super(false, 2);

    }

    @Override
    public void calcOutput() {
        setFoodOutput(1 + getImprovement().getFoodBonus() + getResource().getFoodBonus());
        setGoldOutput(getImprovement().getGoldBonus() + getResource().getGoldBonus());
        setProductionOutput(1 + getImprovement().getProductionBonus() + getResource().getProductionBonus());
        setScienceOutput(getImprovement().getScienceBonus() + getResource().getTechBonus());
    }

    @Override
    public Set<Improvement> getPossibleImprovements() {
        Set<Improvement> improve = possibleImprovement;
        improve.add(this.getResource().getImprovementType());
        return improve;
    }

}
