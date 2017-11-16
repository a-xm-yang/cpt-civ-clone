package civilizationclone.Tile;

import civilizationclone.Unit.Unit;


public class Tile {
    
    private boolean isWater = false;
    private Unit unit;

    public boolean isIsWater() {
        return isWater;
    }
    
    public boolean hasUnit(){
        return unit == null;
    }
    
    public void removeUnit(){
        unit = null;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    
    
}
