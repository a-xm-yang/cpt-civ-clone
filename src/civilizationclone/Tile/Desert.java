package civilizationclone.Tile;

import static civilizationclone.Tile.Plains.possibleImprovement;
import static civilizationclone.Tile.Resource.NONE;
import java.util.EnumSet;
import java.util.Set;

public class Desert extends Tile {

    static Set<Improvement> possibleImprovement = EnumSet.of(Improvement.FARM, Improvement.RANCH, Improvement.ROAD);

    public Desert() {
        super(false, 1);

    }

    @Override
    public void calcOutput() {
        setFoodOutput(2 + getImprovement().getFoodBonus() + getResource().getFoodBonus());
        setGoldOutput(getImprovement().getGoldBonus() + getResource().getGoldBonus());
        setProductionOutput(getImprovement().getProductionBonus() + getResource().getProductionBonus());
        setScienceOutput(getImprovement().getScienceBonus() + getResource().getTechBonus());
    }


    @Override
    public Set<Improvement> getPossibleImprovements() {
        Set<Improvement> improve = possibleImprovement;
        if(this.getResource() != NONE){
            improve.add(this.getResource().getImprovementType());
        }
        return improve;
    }

}
