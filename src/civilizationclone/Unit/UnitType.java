package civilizationclone.Unit;

public enum UnitType {

    SETTLER(80, 140, SettlerUnit.class),
    BUILDER(50, 90, BuilderUnit.class),
    SCOUT(30, 55, ScoutUnit.class),
    //Melee
    WARRIOR(40, 70, WarriorUnit.class),
    SWORDSMAN(90, 190, SwordsmanUnit.class),
    MUSKETMAN(240, 420, MusketmanUnit.class),
    INFANTRY(400, 650, InfantryUnit.class),
    //Range
    SLINGER(35, 60, SlingerUnit.class),
    ARCHER(60, 110, ArcherUnit.class),
    CROSSBOWMAN(180, 320, CrossbowmanUnit.class),
    FIELDCANNON(330, 580, FieldCannonUnit.class),
    //Calvary
    HORSEMAN(80, 140, HorsemanUnit.class),
    KNIGHT(180, 315, KnightUnit.class),
    MOUNTEDFORCE(330, 580, MountedForceUnit.class);

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
