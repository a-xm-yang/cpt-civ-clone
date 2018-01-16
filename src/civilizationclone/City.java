package civilizationclone;

import civilizationclone.Tile.Tile;
import civilizationclone.Unit.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class City {

    //variables
    //<editor-fold>
    private String name;
    private final Point POSITION;
    private final Player originalOwner;
    private Player player;

    private int health;
    private int maxHealth;
    private int combat;
    private int population;
    private int realPopulation;

    private Set<CityProject> builtProjects;
    private CityProject currentProject;
    private UnitType currentUnit;
    private int currentProduction;
    private boolean canExpand;
    private boolean canAttack;

    private int goldIncome;
    private int techIncome;
    private int productionIncome;
    private int foodIncome;

    private Set<Tile> ownedTiles;
    private Set<Tile> workedTiles;

    private static GameMap mapRef;
    //</editor-fold>

    //constructors from a builder unit
    public City(String name, SettlerUnit u) {
        this.name = name;
        this.POSITION = new Point(u.getX(), u.getY());
        this.player = u.getPlayer();
        this.originalOwner = u.getPlayer();

        realPopulation = 1000;
        maxHealth = 300;
        currentProduction = 0;

        ownedTiles = new HashSet<>();
        workedTiles = new HashSet<>();

        ArrayList<Point> temp = mapRef.getRange(POSITION, 1);
        for (Point p : temp) {
            ownedTiles.add(mapRef.getTile(p.x, p.y));
            mapRef.getTile(p.x, p.y).setControllingCity(this);
        }
        ownedTiles.remove(mapRef.getTile(POSITION.x, POSITION.y));

        mapRef.getTile(POSITION.x, POSITION.y).setCity(this);

        builtProjects = EnumSet.noneOf(CityProject.class);

        calcIncome();
        calcFakePopulation();
    }

    public void startTurn() {

        heal();
        calcIncome();
        inceaseRealPopulation();
        calcFakePopulation();

        //if population reaches a certain threshold, you can expand a new tile
        if (population > (ownedTiles.size() + 5)) {
            canExpand = true;
        }

        currentProduction += productionIncome;

        if (currentUnit != null) {
            if (currentProduction >= currentUnit.getProductionCost()) {
                buildUnit();
            }
        } else {
            if (currentProduction >= currentProject.getProductionCost()) {
                buildProject();
            }
        }

        canAttack = false;
        for (Tile t : mapRef.getTiles(mapRef.getRange(POSITION, 3))) {
            if (t.hasUnit()) {
                if (t.getUnit().getPlayer() != this.getPlayer()) {
                    this.canAttack = true;
                }
            }
        }

    }

    private void calcFakePopulation() {
        this.population = (int) Math.pow((realPopulation / 1000), -2.8);
    }

    private void inceaseRealPopulation() {
        if (player.getHappiness() > 2) {
            this.realPopulation += (this.foodIncome - this.population) * 500; //Fiddle around with this number to make it good
        } else if (player.getHappiness() > 0) {
            this.realPopulation += (this.foodIncome - this.population) * 400; //Fiddle around with this number to make it good
        } else {
            this.realPopulation += (this.foodIncome - this.population) * 300;
        }
    }

    public boolean canEndTurn() {

        if (currentUnit != null || currentProject != null) {
            return true;
        }

        return false;
    }

    public void calcIncome() {
        calcTechIncome();
        calcGoldIncome();
        calcProductionIncome();
        calcFoodIncome();
    }

    private void calcTechIncome() {
        //calculate tech income
        int tech = 2;

        //add from buildings
        for (CityProject c : builtProjects) {
            tech += c.getTechBonus();
        }

        //add from tiles
        for (Tile t : workedTiles) {
            tech += t.getScienceOutput();
        }

        techIncome = tech;
    }

    private void calcGoldIncome() {
        //calc gold income
        int gold = 2;

        //add from tiles
        for (Tile t : workedTiles) {
            gold += t.getGoldOutput();
        }

        //add from buildings
        for (CityProject c : builtProjects) {
            gold += c.getGoldBonus();
        }

        goldIncome = gold;
    }

    private void calcFoodIncome() {
        //calc food income
        int food = 2;

        //add from tiles
        for (Tile t : workedTiles) {
            food += t.getFoodOutput();
        }

        //add from buildings
        for (CityProject c : builtProjects) {
            food += c.getFoodBonus();
        }

        foodIncome = food;
    }

    private void calcProductionIncome() {
        //calc production income
        int production = 10;

        //add from tiles
        for (Tile t : workedTiles) {
            production += t.getProductionOutput();
        }
        //add from buildings
        for (CityProject c : builtProjects) {
            production += c.getProductionBonus();
        }

        productionIncome = production;
    }

    private void buildProject() {
        System.out.println(currentProject + " built!");
        builtProjects.add(currentProject);
        currentProduction = 0;
        currentProject = null;
    }

    private void buildUnit() {

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
                break;
            case SLINGER:
                player.addUnit(new SlingerUnit(this));
                break;
            case SETTLER:
                player.addUnit(new SettlerUnit(this));
        }

        currentUnit = null;
    }

    private void heal() {
        health = health + 15;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void conquer(Player p) {
        System.out.println("City conquered by " + p.getName());
        this.player = p;
    }

    public void addTile(Tile t) {
        this.ownedTiles.add(t);
    }

    public void attack(Unit x) {

        if (!(x instanceof MilitaryUnit)) {
            x.delete();
        }

        if (x instanceof MilitaryUnit) {

            MilitaryUnit enemy = (MilitaryUnit) x;

            int thisDmg = 30;

            enemy.setHealth(enemy.getHealth() - thisDmg);
            System.out.println("Unit dealt " + thisDmg);

            if (enemy.getHealth() <= 0) {
                enemy.delete();
            }

        }
    }

    //GETTER
    //<editor-fold>
    public Point[] getAttackable() {

        ArrayList<Point> list = mapRef.getRange(POSITION, 3);
        ArrayList<Point> attackable = new ArrayList<Point>();
        //get a list of all the adjacent tiles, check to see ones that has a unit, is not water, and belongs to opposing players, removing everything else that's not
        for (Point p : list) {
            if (mapRef.getTile(p.x, p.y).hasUnit()) {
                if (mapRef.getTile(p.x, p.y).getUnit().getPlayer() != this.getPlayer()) {
                    attackable.add(p);
                }
            }
        }
        return attackable.toArray(new Point[attackable.size()]);
    }

    public boolean canAttack() {
        return canAttack;
    }

    public boolean canExpand() {
        return canExpand;
    }

    public Point[] getPossibleExpansion() {
        ArrayList<Point> threeRangeList = mapRef.getRange(POSITION, 3);
        ArrayList<Point> list = new ArrayList<>();

        for (Point p : threeRangeList) {

            ArrayList<Point> adjacency = mapRef.getRange(p, 1);

            int count = 0;

            for (Point x : adjacency) {
                if (count == 2) {
                    break;
                }
                if (ownedTiles.contains(mapRef.getTile(x.x, x.y))) {
                    count++;
                }
            }
            if (count >= 2) {
                list.add(p);
            }
        }

        return list.toArray(new Point[list.size()]);
    }

    public ArrayList<Point> getAllPoints() {

        ArrayList<Point> list = new ArrayList<>();

        for (Tile t : getOwnedTiles()) {
            list.add(mapRef.getPoint(t));
        }

        return list;
    }

    public Player getOriginalOwner() {
        return originalOwner;
    }

    public int getPopulation() {
        return population;
    }

    public int getRealPopulation() {
        return realPopulation;
    }

    public Set<CityProject> getBuiltProjects() {
        return builtProjects;
    }

    public int getFoodIncome() {
        return foodIncome;
    }

    public Set<Tile> getWorkedTiles() {
        return workedTiles;
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

    public Set<Tile> getOwnedTiles() {
        return ownedTiles;
    }
    //</editor-fold>

    //SETTER
    //<editor-fold>
    public void setWorkedTiles(Set<Tile> workedTiles) {
        this.workedTiles = workedTiles;
    }

    public void setProduction(CityProject currentProject) {
        this.currentProject = currentProject;
        currentUnit = null;
    }

    public void setProduction(UnitType currentUnit) {
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

    public static void referenceMap(GameMap m) {
        City.mapRef = m;
    }
}
