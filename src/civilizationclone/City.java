package civilizationclone;

import java.awt.Point;


public class City {
    
    private Point position;
    private Player player;
    private int health;
    private int combat;

    
    //GETTER
    //<editor-fold>
    public Player getPlayer() {
        return player;
    }

    public Point getPosition() {
        return position;
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
    
    
    
    public void delete(){
        System.out.println("City deleted");
    }
    
}
