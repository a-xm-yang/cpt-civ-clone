package civilizationclone.Tile;

import civilizationclone.City;
import civilizationclone.TechType;
import civilizationclone.Unit.Unit;
import java.util.Set;

public abstract class Tile {

    private boolean isWater;
    private Improvement improvement;
    private Resource resource;
    private int movementCost;

    private int foodOutput;
    private int productionOutput;
    private int scienceOutput;
    private int goldOutput;

    private Unit unit;
    private City c;
    private City controllingCity;

    public Tile(boolean isWater, int movementCost) {
        this.isWater = isWater;
        this.movementCost = movementCost;

        this.resource = Resource.NONE;
        this.improvement = Improvement.NONE;
    }

    public abstract void calcOutput();

    public void calcTechBonus() {
        if (controllingCity == null) {
            return;
        }

        int foodOutput = this.foodOutput;
        int productionOutput = this.productionOutput;
        int scienceOutput = this.scienceOutput;
        int goldOutput = this.goldOutput;
        
        //Calculates the Tech Bonuses based on the tiles techs and improvements

        if (getImprovement() == Improvement.FISHING && controllingCity.getPlayer().getOwnedTech().contains(TechType.CARTOGRAPHY)) {
            goldOutput++;
        } else if (getImprovement() == Improvement.MINE) {
            if (controllingCity.getPlayer().getOwnedTech().contains(TechType.APPRENTICESHIP)) {
                productionOutput++;
            }
            if (controllingCity.getPlayer().getOwnedTech().contains(TechType.INDUSTRIALIZATION)) {
                productionOutput++;
            }
        } else if (getImprovement() == Improvement.PLANTATION) {
            if (controllingCity.getPlayer().getOwnedTech().contains(TechType.BANKING)) {
                goldOutput++;
            }
            if (controllingCity.getPlayer().getOwnedTech().contains(TechType.SCIENTIFIC_THEORY)) {

            }
        } else if (getImprovement() == Improvement.ACADEMY && controllingCity.getPlayer().getOwnedTech().contains(TechType.ASTRONOMY)) {
            scienceOutput++;
        } else if (getImprovement() == Improvement.RANCH) {
            if (controllingCity.getPlayer().getOwnedTech().contains(TechType.STIRRUPS)) {
                foodOutput++;
            }
        } else if (getImprovement() == Improvement.FARM) {
            if (controllingCity.getPlayer().getOwnedTech().contains(TechType.REPLACEABLE_PARTS)) {
                foodOutput += 2;
            }
        }

        this.foodOutput = foodOutput;
        this.productionOutput = productionOutput;
        this.scienceOutput = scienceOutput;
        this.goldOutput = goldOutput;
    }

    public abstract Set<Improvement> getPossibleImprovements();

    public boolean isWater() {
        return isWater;
    }

    public boolean hasUnit() {
        return !(unit == null);
    }

    public boolean hasCity() {
        return !(c == null);
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
        //when a city is established, all the resource and improvements should be removed and output is set to 0
        this.c = c;
        setResource(Resource.NONE);
        this.improvement = Improvement.NONE;
        setFoodOutput(0);
        setProductionOutput(0);
        setGoldOutput(0);
        setScienceOutput(0);

    }

    public Resource getResource() {
        return resource;
    }

    public Improvement getImprovement() {
        return improvement;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
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

    public void setMovementCost(int movementCost) {
        this.movementCost = movementCost;
    }

    public void setProductionOutput(int productionOutput) {
        this.productionOutput = productionOutput;
    }

    public void setScienceOutput(int scienceOutput) {
        this.scienceOutput = scienceOutput;
    }

    public void setGoldOutput(int goldOutput) {
        this.goldOutput = goldOutput;
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

    public int getFoodOutput() {
        return foodOutput;
    }

    public void setFoodOutput(int foodOutput) {
        this.foodOutput = foodOutput;
    }

    public City getControllingCity() {
        return controllingCity;
    }

    public void setControllingCity(City controllingCity) {
        this.controllingCity = controllingCity;
    }

    public boolean isControlled() {
        return !(this.controllingCity == null);
    }

    //</editor-fold>
}
