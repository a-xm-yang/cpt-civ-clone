
package civilizationclone.Unit;

import civilizationclone.City;
import civilizationclone.Player;
import civilizationclone.Tile.Tile;
import java.awt.Point;

public class SettlerUnit extends Unit{
    
    public SettlerUnit(City c) {
        super(2, c);
    }
    
    public SettlerUnit(Player player, Point p){
        super(2, player, p);
    }
    
    public boolean canSettle(){
        
        //Check if there is any enemy city near by
        Tile[] tempList = getMapRef().getTiles(getMapRef().getRange(new Point(getX(), getY()), 3));
        
        for (Tile t: tempList){
            if (t.hasCity() && t.getCity().getPlayer() != getPlayer()){
                return false;
            }
        }
        
        return true;
    }
    
    public void settle(String name){
        
        //add a new city to player and to map, then delete the unit itself        
        City c = new City(name,this);
    
        getPlayer().addCity(c);
        getPlayer().calcGoldIncome();
        getPlayer().calcTechIncome();
        getPlayer().calculateHappiness();
        delete();
        
    }
    
}
