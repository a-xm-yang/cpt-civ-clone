package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Tile.Improvement;
import java.util.Set;

public class BuilderUnit extends Unit {

    private int actions;

    public BuilderUnit(City c) {
        super(3, c);
        actions = 3;
    }

    public Set<Improvement> getPossibleImprovement(){
        return getMapRef().getTile(getX(),getY()).getPossibleImprovements();
    }
    
    public void improve(Improvement m){
        getMapRef().getTile(getX(), getY()).setImprovement(m);
        System.out.println("Improved!");
    }

}