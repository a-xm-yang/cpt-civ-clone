package civilizationclone;

import civilizationclone.Tile.Improvement;
import civilizationclone.Unit.Unit;
import civilizationclone.Unit.UnitType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

public class Player {
    
    //Variables that a student have
    //<editor-fold>
    String name;
    
    int techProgress;
    int techCost;
    int techIncome;
    
    int goldIncome;
    int currentGold;
    
    private Set<TechType> ownedTech;
    private Set<UnitType> buildableUnit;
    private Set<Improvement> ownedImprovement;
    private Set<CityProject> ownedCityProject;
    
    //lists of things owned
    private ArrayList<Unit> unitList;
    private ArrayList<City> cityList;
     //</editor-fold>

    public Player(String name){
        this.name = name;
        ownedTech = EnumSet.of(TechType.NONE);
        buildableUnit = EnumSet.of(UnitType.BUILDER, UnitType.SCOUT, UnitType.SLINGER, UnitType.WARRIOR);
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }
    
    public ArrayList<Unit> getUnitList() {
        return unitList;
    }
    
    public void addTech(TechType t){
        ownedTech.add(t);
        buildableUnit.addAll(t.getUnlockUnit());
        ownedImprovement.addAll(t.getUnlockImprovement());
        ownedCityProject.addAll(t.getUnlockProject());
    }
    
    public void addBuildableUnit(UnitType u){
        buildableUnit.add(u);
    }
    
    public void addUnit(Unit u){
        unitList.add(u);
    }
    
    public void addCity(City c){
        cityList.add(c);
    }

    public String getName() {
        return name;
    }

    //Getter
    //<editor-fold>
    public int getTechProgress() {
        return techProgress;
    }

    public int getTechCost() {
        return techCost;
    }

    public int getTechIncome() {
        return techIncome;
    }

    public int getGoldIncome() {
        return goldIncome;
    }

    public int getCurrentGold() {
        return currentGold;
    }

    public Set<TechType> getOwnedTech() {
        return ownedTech;
    }

    public Set<UnitType> getBuildableUnit() {
        return buildableUnit;
    }

    public Set<Improvement> getOwnedImprovement() {
        return ownedImprovement;
    }

    public Set<CityProject> getOwnedCityProject() {
        return ownedCityProject;
    }
     //</editor-fold>
    
    
}
