package civilizationclone;

import civilizationclone.Tech.Tech;
import civilizationclone.Tech.TechType;
import civilizationclone.Unit.Unit;
import civilizationclone.Unit.UnitType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

public class Player {
    
    //Variables that a student have
    //<editor-fold>
    String name;
    
    int techProgress;
    int techCost;
    int techIncome;
    
    int goldIncome;
    int currentGold;
    
    private Set<TechType> ownedTech;
    private Set<UnitType> buildableUnit;
    
    //lists of things owned
    private ArrayList<Unit> unitList;
    private ArrayList<City> cityList;
     //</editor-fold>

    public Player(String name){
        this.name = name;
        ownedTech = EnumSet.of(TechType.NONE);
        buildableUnit = EnumSet.of(UnitType.BUILDER, UnitType.SCOUT, UnitType.SLINGER, UnitType.WARRIOR);
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }
    
    public ArrayList<Unit> getUnitList() {
        return unitList;
    }
    
    public void addTech(Tech t){
        ownedTech.add(t.getType());
    }
    
    public void addUnit(Unit u){
        unitList.add(u);
    }
    
    public void addCity(City c){
        cityList.add(c);
    }
    
}
