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
    
    //TODO --- Finish coding the native output of all the different types of tiles. Override abstract method calcOutput() to add unique native outputs

    public Tile(boolean isWater, int movementCost) {
        this.isWater = isWater;
        this.movementCost = movementCost;
    }
    
    public abstract void calcOutput();

    public boolean isWater() {
        return isWater;
    }

    public boolean hasUnit() {
        return !(unit == null);
    }

    public void removeUnit() {
        unit = null;
    }


    //GETTER & SETTER
    //<editor-fold>
    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
        calcOutput();
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setCity(City c) {
        this.c = c;
    }

    public Resource getResource() {
        return resource;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
       // calcOutput();
    }

    public int getMovementCost() {
        return movementCost;
    }

    public int getProductionOutput() {
        return productionOutput;
    }

    public int getScienceOutput() {
        return scienceOutput;
    }

    public int getGoldOutput() {
        return goldOutput;
    }

    public Unit getUnit() {
        return unit;
    }

    public City getCity() {
        return c;
    }
    //</editor-fold>

}
