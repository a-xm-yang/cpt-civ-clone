package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Tile.Improvement;
import java.util.ArrayList;

public class BuilderUnit extends Unit {

    private int actions;

    public BuilderUnit(City c) {
        super(2, c);
        actions = 3;
    }

    public Improvement[] getPossibleImprovements(){
        
        ArrayList<Improvement> list = new ArrayList<>();
        
        
        for (Improvement p: getMapRef().getTile(getX(),getY()).getPossibleImprovements()){
            if (getPlayer().getOwnedImprovement().contains(p)){
                list.add(p);
            }
        }
        
        return list.toArray(new Improvement[list.size()]);
    }
    
    public void improve(Improvement m){
        getMapRef().getTile(getX(), getY()).setImprovement(m);
        actions--;
        this.setMovement(0);
        if (actions == 0){ 
            delete();
        }
    }

    public int getActions() {
        return actions;
    }
    
    

}