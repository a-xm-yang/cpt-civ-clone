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

    //constructor
    public Player(String name) {
        this.name = name;

        //initializing enumsets
        ownedTech = EnumSet.of(TechType.NONE);
        buildableUnit = EnumSet.of(UnitType.BUILDER, UnitType.SCOUT, UnitType.SLINGER, UnitType.WARRIOR);
        researchableTech = EnumSet.noneOf(TechType.class);
        ownedImprovement = EnumSet.noneOf(Improvement.class);
        ownedCityProject = EnumSet.noneOf(CityProject.class);
    }

    public void startTurn() {

        calcGoldIncome();
        calcGoldIncome();

        techProgress += techIncome;

        if (techProgress >= research.getTechCost()) {
            addTech(research);
            calcResearchableTech();
            research = null;
            techProgress = 0;
        }

        currentGold += goldIncome;

        //reset movements for all units
        for (Unit unit : unitList) {
            unit.resetMovement();
        }

        //start turn action for all cities
        for (City city : cityList) {
            city.startTurn();
        }
    }

    public boolean endTurn() {
        for (Unit u : unitList) {
            if (u.canMove()) {
                System.out.println("Unit needs to move");
                return false;
            }
        }

        for (City c : cityList) {
            if (c.getCurrentProject() == null) {
                System.out.println("Needs to select city project for " + c.getName());
            }
        }

        if (research == null) {
            System.out.println("Need to select project");
            return false;
        }

        return true;
    }

    public void calcResearchableTech() {
        researchableTech = EnumSet.noneOf(TechType.class);
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
        if (t.getUnlockUnit() != null) {
            buildableUnit.addAll(t.getUnlockUnit());
        }
        if (t.getUnlockImprovement() != null) {
            ownedImprovement.addAll(t.getUnlockImprovement());
        }
        if (t.getUnlockProject() != null) {
            ownedCityProject.addAll(t.getUnlockProject());
        }
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
