package civilizationclone.Tile;

import java.util.EnumSet;
import java.util.Set;

public class Mountain extends Tile {

    static Set<Improvement> possibleImprovement = EnumSet.noneOf(Improvement.class);

    public Mountain() {
        super(false, 999);
    }

    @Override
    public void calcOutput() {
        setFoodOutput(0);
        setGoldOutput(0);
        setProductionOutput(0);
        setScienceOutput(0);

        calcTechBonus();
    }

    @Override
    public Set<Improvement> getPossibleImprovements() {
        Set<Improvement> improve = possibleImprovement;
        improve.add(this.getResource().getImprovementType());
        return improve;
    }

}
