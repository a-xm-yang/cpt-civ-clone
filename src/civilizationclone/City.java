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
        this.POSITION = new Point(u.getX(), u.getY());
        this.originalOwner = u.getPlayer();

        this.name = name;
        this.player = u.getPlayer();

        realPopulation = 1000;
        maxHealth = 125;
        combat = 35;
        health = maxHealth;
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

        calcFakePopulation();
        calcIncome();
    }

    public void startTurn() {

        autoSelectWork();

        heal();
        calcIncome();
        inceaseRealPopulation();
        calcFakePopulation();

        //if population reaches a certain threshold, you can expand a new tile
        if (population > (ownedTiles.size() - 5) && ownedTiles.size() < 36) {
            canExpand = true;
        }

        //if pupulation has been lost, disable one of the worked tiles
        if (workedTiles.size() > population) {
            //remove one from worked tiles
            Tile temp = null;
            for (Tile t : workedTiles) {
                temp = t;
                break;
            }

            workedTiles.remove(temp);
        }

        currentProduction += productionIncome;

        if (currentUnit != null) {
            if (currentProduction >= currentUnit.getProductionCost()) {
                if (!mapRef.getTile(POSITION).hasUnit()) {
                    buildUnit();
                } else {
                    System.out.println("This tile is occupied! Move your unit away!");
                }
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

    private void autoSelectWork() {
        while (workedTiles.size() < this.population) {

            Tile highest = null;
            int highestValue = 0;

            for (Tile t : ownedTiles) {
                if (!workedTiles.contains(t)) {
                    int temp = t.getFoodOutput() + t.getGoldOutput() + t.getProductionOutput() + t.getScienceOutput();
                    if (temp > highestValue) {
                        highest = t;
                        highestValue = temp;
                    }
                }
            }

            workedTiles.add(highest);
        }
    }

    private void calcFakePopulation() {
        //fake population is the population that the player sees, while real population is what is behind the calculation
        this.population = (int) (Math.pow((realPopulation / 1000), 1 / 2.85));
    }

    private void inceaseRealPopulation() {
        //Increase population according to how much happiness you have
        if (player.getHappiness() > 2) {
            this.realPopulation += (this.foodIncome - this.population) * 500; //Fiddle around with this number to make it good
        } else if (player.getHappiness() >= 0) {
            this.realPopulation += (this.foodIncome - this.population) * 400; //Fiddle around with this number to make it good
        } else {
            this.realPopulation += (this.foodIncome - this.population) * 250;
        }
    }

    public boolean canEndTurn() {

        //whether this city can end turn (which means it has a project)
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
        int tech = 1 + population;

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
        int gold = 1 + population;

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
        int production = 5;

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
        //Build project according to how
        System.out.println(currentProject + " built!");
        builtProjects.add(currentProject);
        currentProduction = 0;
        if (currentProject.name().endsWith("WALL")) {
            //If you get a wall, the city increase in defense and in strength
            maxHealth += 50;
            health = maxHealth;
            combat += 10;
        }
        currentProject = null;
    }

    private void buildUnit() {

        //adds the unit object onto the list then clean production queue
        currentProduction = 0;

        try {
            getPlayer().addUnit((Unit) currentUnit.getCorrespondingClass().getConstructor(City.class).newInstance(this));
        } catch (Exception e) {
            System.out.println("Constructing unit from production failed!");
        }

        currentUnit = null;
    }

    private void heal() {
        health = health + 5;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void conquer(Player p) {
        System.out.println("City conquered by " + p.getName());
        this.player.getCityList().remove(this);
        this.player = p;
        this.player.getCityList().add(this);
        updateTiles();
        calcIncome();

        health = (int) (maxHealth * 0.25);
    }

    public void addTile(Tile t) {
        this.ownedTiles.add(t);
        t.setControllingCity(this);
        t.calcOutput();
        canExpand = false;
    }

    public void addCityProject(CityProject c) {
        builtProjects.add(c);
        calcIncome();
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

    public boolean isCostal() {
        for (Tile t : mapRef.getTiles(mapRef.getRange(POSITION, 1))) {
            if (t.isWater()) {
                return true;
            }
        }

        return false;
    }

    public void updateTiles() {
        for (Tile t : ownedTiles) {
            t.calcOutput();
        }
    }

    //GETTER
    //<editor-fold>
    public double getDamageReduction() {
        Double d = getHealthPercentage();

        if (d < 0.35) {
            return 0.35;
        } else {
            return d;
        }
    }

    public Tile getCityTile() {
        return mapRef.getTile(POSITION);
    }

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

        //First find the list of all the tiles within 3 range that has not been added yet
        ArrayList<Point> threeRangeList = mapRef.getRange(POSITION, 3);
        ArrayList<Point> filteredList = new ArrayList<>();

        //cannot add the tile itself
        threeRangeList.remove(POSITION);

        for (Point p : threeRangeList) {
            if (!ownedTiles.contains(mapRef.getTile(p)) && !mapRef.getTile(p).isControlled()) {
                filteredList.add(p);
            }
        }

        //Then filter again, with only ones that have 2 or more adjacencies left
        ArrayList<Point> list = new ArrayList<>();
        for (Point p : filteredList) {

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

    public double getHealthPercentage() {
        return (health * 1.0) / maxHealth;
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

    public static GameMap getMapRef() {
        return mapRef;
    }
    //</editor-fold>

    //SETTER
    //<editor-fold>
    public void setName(String name) {
        this.name = name;
    }

    public void setFoodIncome(int foodIncome) {
        this.foodIncome = foodIncome;
    }

    public void setWorkedTiles(Set<Tile> workedTiles) {
        this.workedTiles = workedTiles;
    }

    public void setProduction(CityProject currentProject) {
        this.currentProject = currentProject;
        currentUnit = null;
        currentProduction = 0;
    }

    public void setProduction(UnitType currentUnit) {
        this.currentUnit = currentUnit;
        currentProject = null;
        currentProduction = 0;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setCanExpand(boolean canExpand) {
        this.canExpand = canExpand;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCombat(int combat) {
        this.combat = combat;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setRealPopulation(int realPopulation) {
        this.realPopulation = realPopulation;
    }

    public void setCurrentProject(CityProject currentProject) {
        this.currentProject = currentProject;
    }

    public void setCurrentUnit(UnitType currentUnit) {
        this.currentUnit = currentUnit;
    }

    public void setCurrentProduction(int currentProduction) {
        this.currentProduction = currentProduction;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public void setGoldIncome(int goldIncome) {
        this.goldIncome = goldIncome;
    }

    public void setTechIncome(int techIncome) {
        this.techIncome = techIncome;
    }

    public void setProductionIncome(int productionIncome) {
        this.productionIncome = productionIncome;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        String s = "";
        s += POSITION.x;
        s += POSITION.y;
        s += player.getName();
        return s.hashCode();
    }

    public static void referenceMap(GameMap m) {
        City.mapRef = m;
    }
}
