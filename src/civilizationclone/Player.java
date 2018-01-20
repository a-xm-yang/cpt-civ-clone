package civilizationclone;

import civilizationclone.Tile.Improvement;
import civilizationclone.Tile.Tile;
import civilizationclone.Unit.MilitaryUnit;
import civilizationclone.Unit.Unit;
import civilizationclone.Unit.UnitType;
import java.awt.Point;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
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
    private int happiness;

    private Set<TechType> ownedTech;
    private Set<TechType> researchableTech;
    private Set<UnitType> buildableUnit;
    private Set<Improvement> ownedImprovement;
    private Set<CityProject> ownedCityProject;

    private Set<Tile> exploredTiles;

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
        ownedImprovement = EnumSet.allOf(Improvement.class);
        ownedCityProject = EnumSet.noneOf(CityProject.class);
        exploredTiles = new HashSet<Tile>();

        research = TechType.AGRICULTURE;

        unitList = new ArrayList<Unit>();
        cityList = new ArrayList<City>();
    }

    public void startTurn() {

        //start turn action for all cities
        for (City city : cityList) {
            city.startTurn();
        }

        calcGoldIncome();
        calcTechIncome();
        calculateHappiness();

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

    }

    public boolean canEndTurn() {
        for (Unit u : unitList) {
            if (u.canMove()) {
                System.out.println("Unit needs to move");
                return false;
            }
        }

        for (City c : cityList) {
            if (!c.canEndTurn()) {
                System.out.println("Needs to select city project for " + c.getName());
                return false;
            }
        }

        if (research == null) {
            System.out.println("Need to select project");
            return false;
        }

        return true;
    }

    private void calcResearchableTech() {
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
            if (unit instanceof MilitaryUnit) {
                x -= ((MilitaryUnit) unit).getMaintainence();
            }
        }

        for (City c : cityList) {
            x += c.getGoldIncome();
        }

        goldIncome = x;
    }

    public void calcTechIncome() {
        
        int x = 0;

        for (City c : cityList) {
            x += c.getTechIncome();
        }

        techIncome = x;
    }

    //ADDING FUNCTIONS FOR PLAYER
    //<editor-fold>
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

    public void addExploredTiles(Set<Tile> visibleTiles) {

        //add what people see right now to the explored tiles
        for (Tile t : visibleTiles) {
            if (!exploredTiles.contains(t)) {
                exploredTiles.add(t);
            }
        }
    }
    //</editor-fold>

    //Getter && Setter
    //<editor-fold> 
    public int getHappiness() {
        return happiness;
    }

    public Point[] getAllPositions() {

        //return a collection of ALL the positions (such as units and cities) that the player has
        ArrayList<Point> list = new ArrayList<>();

        for (City c : cityList) {
            list.addAll(c.getAllPoints());
        }

        for (Unit u : unitList) {
            list.add(new Point(u.getX(), u.getY()));
        }

        return list.toArray(new Point[list.size()]);
    }

    public Set<Tile> getExploredTiles() {
        return exploredTiles;
    }

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

    private void calculateHappiness() {
        // To be changed in future
        int total = 0;

        //Calculate happiness
        for (City c : cityList) {
            for (Tile t : c.getOwnedTiles()) {
                if (t.getResource().isLuxury()) {
                    total += 4;
                }
            }
            for (CityProject p : c.getBuiltProject()) {
                total += 1;
                //TODO Make so only certain building increase happiness and some increase more than others
            }
        }
        //Calculate unhappiness
        for (City c : cityList) {
            total -= 2;
            total -= c.getPopulation();
            if (!c.getOriginalOwner().equals(this)) {
                total -= 2;
            }
        }
        happiness = total;
    }

}
