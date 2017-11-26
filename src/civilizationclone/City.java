package civilizationclone;

import civilizationclone.Unit.BuilderUnit;
import java.awt.Point;

public class City {

    //variables
    //<editor-fold>
    private String name;
    private final Point POSITION;
    private Player player;
    
    private int health;
    private int combat;
    
    private CityProject currentProject;
    //</editor-fold>

    //constructors from default
    public City(String name, Point position, Player player) {
        this.name = name;
        this.POSITION = position;
        this.player = player;
    }

    //constructors from a builder unit
    public City(String name, BuilderUnit u) {
        this.name = name;
        this.POSITION = new Point(u.getX(),u.getY());
        this.player = u.getPlayer();
    }
    
    public void heal() {
        health = health + 15;
    }
    
    public void startTurn(){
        heal();
    }

    //GETTER
    //<editor-fold>
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
    public void setHealth(int health) {
        this.health = health;
    }
    //</editor-fold>

    public void delete() {
        System.out.println("City deleted");
    }

}
