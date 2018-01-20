package civilizationclone.Unit;

public enum UnitType {

    SETTLER(90,150, SettlerUnit.class),
    BUILDER(60, 120, BuilderUnit.class),
    SCOUT(40, 80, ScoutUnit.class), 
    WARRIOR(50, 110, WarriorUnit.class), 
    SLINGER(50, 110, SlingerUnit.class),
    HORSEMAN(60,120, HorsemanUnit.class),
    ARCHER(80,145, ArcherUnit.class);

    private int productionCost;
    private int purchaseCost;
    private Class correspondingClass;


    private UnitType(int productionCost, int purchaseCost, Class c) {
        this.correspondingClass = c;
        this.productionCost = productionCost;
        this.purchaseCost = purchaseCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }

    public Class getCorrespondingClass() {
        return correspondingClass;
    }
    

}
