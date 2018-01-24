
package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import civilizationclone.Tile.Resource;
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
        getMapRef().getTile(getX(), getY()).setResource(Resource.NONE);
        getPlayer().addCity(c);
        getPlayer().calcGoldIncome();
        getPlayer().calcTechIncome();
        getPlayer().calculateHappiness();
        delete();
    }
    
}
