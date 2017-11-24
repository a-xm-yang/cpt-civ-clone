package civilizationclone;

import civilizationclone.Tile.Improvement;
import civilizationclone.Unit.MilitaryUnit;
import civilizationclone.Unit.Unit;
import civilizationclone.Unit.UnitType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

public class Player {

    //Variables that a player has
    //<editor-fold>
    private String name;

    private TechType research;
    private int techProgress;
    private int techIncome;

    private int goldIncome;
    private int currentGold;

    private Set<TechType> ownedTech;
    private Set<TechType> researchableTech;
    private Set<UnitType> buildableUnit;
    private Set<Improvement> ownedImprovement;
    private Set<CityProject> ownedCityProject;

    //lists of things owned
    private ArrayList<Unit> unitList;
    private ArrayList<City> cityList;
    //</editor-fold>

    public Player(String name) {
        this.name = name;
        ownedTech = EnumSet.of(TechType.NONE);
        buildableUnit = EnumSet.of(UnitType.BUILDER, UnitType.SCOUT, UnitType.SLINGER, UnitType.WARRIOR);
    }

    public void startTurn() {
        
        calcGoldIncome();
        calcGoldIncome();
        
        techProgress += techIncome;
        
        if (techProgress >= research.getTechCost()){
            addTech(research);
            research = null;
            techProgress = 0;
        }
        
        currentGold += goldIncome;
        
        for (Unit unit: unitList){
            unit.resetMovement();
        }
    }

    public void calcResearchableTech() {
        for (TechType t : TechType.values()) {
            if (ownedTech.containsAll(t.getPrerequisites()) && !ownedTech.contains(t)) {
                researchableTech.add(t);
            }
        }
    }

    public void calcGoldIncome() {
        int x = 0;

        for (Unit unit : unitList) {
            x -= ((MilitaryUnit) unit).getMaintainence();
        }

        for (City c : cityList) {
            // add gold from city
        }

        goldIncome = x;
    }

    public void calcTechIncome() {
        int x = 0;

        for (City c : cityList) {
            //add tech from each city
        }

        techIncome = x;
    }

    public void addTech(TechType t) {
        ownedTech.add(t);
        buildableUnit.addAll(t.getUnlockUnit());
        ownedImprovement.addAll(t.getUnlockImprovement());
        ownedCityProject.addAll(t.getUnlockProject());
    }

    public void addBuildableUnit(UnitType u) {
        buildableUnit.add(u);
    }

    public void addUnit(Unit u) {
        unitList.add(u);
    }

    public void addCity(City c) {
        cityList.add(c);
    }

    //Getter && Setter
    //<editor-fold>
    public TechType getResearch() {
        return research;
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }

    public ArrayList<Unit> getUnitList() {
        return unitList;
    }

    public Set<TechType> getResearchableTech() {
        return researchableTech;
    }

    public String getName() {
        return name;
    }

    public int getTechProgress() {
        return techProgress;
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

    public void setResearch(TechType research) {
        this.research = research;
    }
    //</editor-fold>

}
