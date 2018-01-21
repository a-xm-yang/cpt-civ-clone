
package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import java.awt.Point;

public class SettlerUnit extends Unit{
    
    public SettlerUnit(City c) {
        super(2, c);
    }
    
    public SettlerUnit(Player player, Point p){
        super(2, player, p);
    }
    
    public void settle(String name){
        
        //add a new city to player and to map, then delete the unit itself        
        City c = new City(name,this);
        
        getMapRef().getTile(getX(), getY()).setCity(c);
        getPlayer().addCity(c);
        getPlayer().calcGoldIncome();
        getPlayer().calcTechIncome();
        delete();
    }
    
}
