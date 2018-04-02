package civilizationclone.Unit;

public enum UnitType {

    //CIVILIAN/RECON
    SETTLER(80, 140, SettlerUnit.class),
    BUILDER(50, 90, BuilderUnit.class),
    SCOUT(30, 55, ScoutUnit.class),
    //Melee
    WARRIOR(40, 70, WarriorUnit.class),
    SWORDSMAN(90, 190, SwordsmanUnit.class),
    MUSKETMAN(240, 420, MusketmanUnit.class),
    INFANTRY(360, 640, InfantryUnit.class),
    //Range
    SLINGER(35, 60, SlingerUnit.class),
    ARCHER(60, 110, ArcherUnit.class),
    CROSSBOWMAN(180, 320, CrossbowmanUnit.class),
    FIELDCANNON(330, 580, FieldCannonUnit.class),
    //Calvary
    HORSEMAN(80, 140, HorsemanUnit.class),
    KNIGHT(180, 315, KnightUnit.class),
    MOUNTEDFORCE(330, 580, MountedForceUnit.class),
    //Siege
    CATAPULT(120, 210, CatapultUnit.class),
    BOMBARD(280, 490, BombardUnit.class),
    //NAVAL
    GALLEY(65, 115, GalleyUnit.class),
    QUADRIREME(120, 210, QuadriremeUnit.class),
    CARAVEL(240, 420, CaravelUnit.class),
    IRONCLAD(380, 660, IroncladUnit.class);

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
    
    @Override
    public String toString() {
        String s = name();

        if (s.contains("_")) {
            s = s.replaceAll("_", " ");
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            s = s.substring(0, s.indexOf(" ") + 1) + s.substring(s.indexOf(" ") + 1, s.indexOf(" ") + 2).toUpperCase() + s.substring(s.indexOf(" ") + 2);
        } else {
            s = s.toLowerCase();
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
        }

        return s;
    }


}
