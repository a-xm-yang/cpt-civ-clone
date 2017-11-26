package civilizationclone.Tile;

import civilizationclone.Unit.Unit;


public abstract class Tile {
    
    private boolean isWater;
    private Unit unit;
    private Improvement improvement;
    private Resource resource;
    private int movementCost;
    private int productionOutput;
    private int scienceOutput;
    private int goldOutput;
    
    //TEST

    public Tile(boolean isWater, int movementCost) {
        this.isWater = isWater;
        this.movementCost = movementCost;
    }
   
    public boolean isIsWater() {
        return isWater;
    }
    
    public boolean hasUnit(){
        return !(unit == null);
    }
    
    public void removeUnit(){
        unit = null;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public abstract void improve();
    
    public void setResourse(Resource resource){
        this.resource = resource;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    
}
