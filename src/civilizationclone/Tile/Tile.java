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
    
    public void improve(){
        System.out.println("improved");
    }
    
}
