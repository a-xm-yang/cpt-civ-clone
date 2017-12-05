package civilizationclone;

import civilizationclone.Unit.*;
import java.awt.Point;
import java.util.EnumSet;
import java.util.Set;

public class City {

    //variables
    //<editor-fold>
    private String name;
    private final Point POSITION;
    private Player player;

    private int health;
    private int maxHealth;
    private int combat;

    private Set<CityProject> builtProjects;
    private CityProject currentProject;
    private UnitType currentUnit;
    private int currentProduction;

    private int goldIncome;
    private int techIncome;
    private int productionIncome;

    private static GameMap mapRef;
    //</editor-fold>

    //constructors from a builder unit
    public City(String name, SettlerUnit u) {
        this.name = name;
        this.POSITION = new Point(u.getX(), u.getY());
        this.player = u.getPlayer();
        maxHealth = 300;
        currentProduction = 0;
        mapRef.getTile(POSITION.x,POSITION.y).setCity(this);
        builtProjects = EnumSet.noneOf(CityProject.class);
    }

    //TODO add a list of owned tiles for a city, and calculate how to add onto that list automatically
    //TODO add population growth function inside startTurn()
    //TODO finish the tile output after the tile design
    
    public void startTurn() {

        heal();
        calcIncome();

        currentProduction += productionIncome;

        if (currentUnit != null) {
            if (currentProduction >= currentUnit.getProductionCost()) {
                buildUnit();
            }
        } else {
            if (currentProduction >= currentProject.getProductionCost()) {
                buildUnit();
            }
        }

    }

    public boolean canEnd() {
        if (currentUnit != null || currentProject != null) {
            return true;
        }

        return false;
    }

    public void calcIncome() {
        calcTechIncome();
        calcGoldIncome();
        calcProductionIncome();
    }

    private void calcTechIncome() {
        //calculate tech income
        int tech = 0;

        //add from buildings
        for (CityProject c : builtProjects) {
            tech += c.getTechBonus();
        }

        //add from tiles
        
        
        techIncome = tech;
    }

    private void calcGoldIncome() {
        //calc gold income
        int gold = 0;

        //add from tiles
        
        //add from buildings
        for (CityProject c : builtProjects) {
            gold += c.getGoldBonus();
        }

        goldIncome = gold;
    }

    private void calcProductionIncome() {
        //calc production income
        int production = 0;

        //add from tiles
        //add from buildings
        for (CityProject c : builtProjects) {
            production += c.getProductionBonus();
        }

        productionIncome = production;
    }

    public void buildProject() {
        System.out.println(currentProject + " built!");
        builtProjects.add(currentProject);
        currentProduction = 0;
        currentProject = null;
    }

    public void buildUnit() {

        //adds the unit object onto the list then clean production queue
        currentProduction = 0;

        switch (currentUnit) {
            case BUILDER:
                player.addUnit(new BuilderUnit(this));
                break;
            case WARRIOR:
                player.addUnit(new WarriorUnit(this));
                break;
            case SCOUT:
                player.addUnit(new ScoutUnit(this));
            case SLINGER:
                player.addUnit(new SlingerUnit(this));
        }

        currentUnit = null;
    }

    public void heal() {
        health = health + 15;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    //GETTER
    //<editor-fold>
    public Point[] getControlledTiles(){
         return null;
    }
    
    public Set<CityProject> getBuiltProject() {
        return builtProjects;
    }

    public int getProductionIncome() {
        return productionIncome;
    }

    public int getTechIncome() {
        return techIncome;
    }

    public int getGoldIncome() {
        return goldIncome;
    }

    public int getCurrentProduction() {
        return currentProduction;
    }

    public UnitType getCurrentUnit() {
        return currentUnit;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getName() {
        return name;
    }

    public CityProject getCurrentProject() {
        return currentProject;
    }

    public Player getPlayer() {
        return player;
    }

    public Point getPosition() {
        return POSITION;
    }

    public int getHealth() {
        return health;
    }

    public int getCombat() {
        return combat;
    }
    //</editor-fold>

    //SETTER
    //<editor-fold>
    public void setCurrentProject(CityProject currentProject) {
        this.currentProject = currentProject;
        currentUnit = null;
    }

    public void setCurrentUnit(UnitType currentUnit) {
        this.currentUnit = currentUnit;
        currentProject = null;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    //</editor-fold>

    public void conquer(Player p) {
        System.out.println("City conquered by " + p.getName());
        this.player = p;
    }

    public static void referenceMap(GameMap m) {
        City.mapRef = m;
    }

}
