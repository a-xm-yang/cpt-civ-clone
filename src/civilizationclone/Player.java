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
import javafx.scene.paint.Color;

public class Player {

    //Variables that a player has
    //<editor-fold>
    private String name;

    private boolean defeated;
    private boolean gMode;

    private TechType research;
    private int techProgress;
    private int techIncome;

    private int goldIncome;
    private int currentGold;
    private int happiness;

    private int turnNumber;

    private Set<TechType> ownedTech;
    private Set<TechType> researchableTech;
    private Set<UnitType> buildableUnit;
    private Set<Improvement> ownedImprovement;
    private Set<CityProject> ownedCityProject;

    private Set<Tile> exploredTiles;

    //lists of things owned
    private ArrayList<Unit> unitList;
    private ArrayList<City> cityList;

    Leader leader;
    Color color;
    //</editor-fold>



    //constructor
    public Player(String name) {
        this.name = name;
        gMode = false;

        //initializing enumsets
        ownedTech = EnumSet.of(TechType.NONE);
        buildableUnit = EnumSet.of(UnitType.BUILDER, UnitType.SCOUT, UnitType.SLINGER, UnitType.WARRIOR);
        researchableTech = EnumSet.noneOf(TechType.class);
        ownedImprovement = EnumSet.noneOf(Improvement.class);
        ownedCityProject = EnumSet.noneOf(CityProject.class);
        exploredTiles = new HashSet<Tile>();

        research = TechType.NONE;

        unitList = new ArrayList<Unit>();
        cityList = new ArrayList<City>();

        turnNumber = 0;

        setLeader();
    }

    public void startTurn() {

        turnNumber++;
        //start turn action for all cities
        for (City city : cityList) {
            city.startTurn();
        }

        calcGoldIncome();
        calcTechIncome();

        calculateHappiness();

        techProgress += techIncome;

        //if technology progress has surpassed currente requirement
        if (techProgress >= research.getTechCost()) {
            addTech(research);
            calcResearchableTech();
            research = TechType.NONE;
            techProgress = 0;
        }

        currentGold += goldIncome;

        //reset movements for all units
        for (Unit unit : unitList) {
            unit.resetMovement();
            if (unit instanceof MilitaryUnit) {
                if (((MilitaryUnit) unit).isFortified()) {
                    ((MilitaryUnit) unit).heal();
                }
            }
        }

    }

    public int canEndTurn() {

        //return different integer representing each situation
        for (Unit u : unitList) {
            if (u instanceof MilitaryUnit) {
                if (((MilitaryUnit) u).isFortified()) {
                    continue;
                }
            }

            if (u.canMove()) {
                return 1;
            }
        }

        for (City c : cityList) {
            if (!c.canEndTurn()) {
                return 2;
            } else if (c.canExpand()){
                return 4;
            }
        }

        if (research == TechType.NONE && !researchableTech.isEmpty()) {
            return 3;
        }

        return 0;
    }

    private void calcResearchableTech() {
        //Loop through the list of techs to see which ones we can research right now
        researchableTech = EnumSet.noneOf(TechType.class);
        for (TechType t : TechType.values()) {
            if (ownedTech.containsAll(t.getPrerequisites()) && !ownedTech.contains(t)) {
                researchableTech.add(t);
            }
        }
    }

    public void calcGoldIncome() {
        int x = 0;

        //calculate gold income of all cities
        for (Unit unit : unitList) {
            if (unit instanceof MilitaryUnit) {
                x -= ((MilitaryUnit) unit).getMaintainence();
            }
        }

        for (City c : cityList) {
            x += c.getGoldIncome();
        }

        if (gMode) {
            x += 350;
        }

        goldIncome = x;
    }

    public void calcTechIncome() {

        int x = 0;

        for (City c : cityList) {
            x += c.getTechIncome();
        }

        if (gMode) {
            x += 350;
        }

        techIncome = x;
    }

    public void calculateHappiness() {
        // To be changed in future
        int total = 5;

        //Calculate happiness
        for (City c : cityList) {
            for (Tile t : c.getOwnedTiles()) {
                if (t.getResource().isLuxury()) {
                    total += 2;
                }
            }
            for (CityProject p : c.getBuiltProjects()) {
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

        //if the country is going broke
        if (getGoldIncome() < 0 || getCurrentGold() < 0) {
            total -= 2;
        }

        happiness = total;
    }

    public boolean isDefeated() {
        return (cityList.size() == 0 && unitList.isEmpty());
    }

    //ADDING FUNCTIONS FOR PLAYER
    //<editor-fold>
    public void addTech(TechType t) {
        //add a new tech, and add all the things that this tech brings, e.g. new unit, new improvement, new project, etc.
        ownedTech.add(t);
        if (t.getUnlockUnit() != null) {
            for (UnitType u : t.getUnlockUnit()) {
                for (UnitType ut : buildableUnit) {
                    if (ut.getCorrespondingClass().getSuperclass().equals(u.getCorrespondingClass().getSuperclass()) && ut != UnitType.BUILDER) {
                        buildableUnit.remove(ut);
                        break;
                    }
                }
                buildableUnit.add(u);
            }
        }
        if (t.getUnlockImprovement() != null) {
            ownedImprovement.addAll(t.getUnlockImprovement());
        }
        if (t.getUnlockProject() != null) {
            ownedCityProject.addAll(t.getUnlockProject());
        }

        for (City c : cityList) {
            c.updateTiles();
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

    //strictly for testing, not invoked in game
    public void addResearchableTech(TechType t) {
        researchableTech.add(t);
    }
    //</editor-fold>

    //Getter && Setter
    //<editor-fold> 
    public void setgMode(boolean gMode) {
        this.gMode = gMode;
    }

    public boolean isgMode() {
        return gMode;
    }

    private void setLeader() {
        for (Leader l : Leader.values()) {
            if (this.getName().substring(this.getName().indexOf(" ") + 1, this.getName().length()).equalsIgnoreCase(l.name())) {
                this.leader = l;

                switch (l) {
                    case CHURCHILL:
                        this.color = Color.MAROON;
                        break;
                    case HITLER:
                        this.color = Color.GAINSBORO;
                        break;
                    case MUSSOLINI:
                        this.color = Color.PURPLE;
                        break;
                    case ROOSEVELT:
                        this.color = Color.BLUE;
                        break;
                    case STALIN:
                        this.color = Color.YELLOW;
                        break;
                    case ZEDONG:
                        this.color = Color.FORESTGREEN;
                        break;
                }

                return;
            }
        }

        //default
        this.leader = Leader.STALIN;
    }

    public Leader getLeader() {
        return this.leader;
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

    public int getHappiness() {
        return happiness;
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
        techProgress = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTechProgress(int techProgress) {
        this.techProgress = techProgress;
    }

    public void setTechIncome(int techIncome) {
        this.techIncome = techIncome;
    }

    public void setGoldIncome(int goldIncome) {
        this.goldIncome = goldIncome;
    }

    public void setCurrentGold(int currentGold) {
        this.currentGold = currentGold;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public void setOwnedTech(Set<TechType> ownedTech) {
        this.ownedTech = ownedTech;
    }

    public void setResearchableTech(Set<TechType> researchableTech) {
        this.researchableTech = researchableTech;
    }

    public void setOwnedImprovement(Set<Improvement> ownedImprovement) {
        this.ownedImprovement = ownedImprovement;
    }

    public void setOwnedCityProject(Set<CityProject> ownedCityProject) {
        this.ownedCityProject = ownedCityProject;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public Color getColor() {
        return color;
    }

    //</editor-fold>
}
