package civilizationclone.Tile;

import civilizationclone.City;
import civilizationclone.Unit.Unit;


public abstract class Tile {
    
    private boolean isWater;
    private Improvement improvement;
    private Resource resource;
    private int movementCost;
    private int productionOutput;
    private int scienceOutput;
    private int goldOutput;
    
    private Unit unit;
    private City c;
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
    
    public void improve(){
        
        System.out.println("improved");
        
        //recalibrate income stats for the city
        c.calcIncome();
    }
    
}
