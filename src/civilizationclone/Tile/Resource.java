package civilizationclone.Tile;

public enum Resource {

    //GOLD, PRODUCTION, FOOD, TECH, LUXURY, IMPROVEMENT
    WHEAT(0, 0, 3, 0, false, Improvement.FARM), // FARM RELATED
    RICE(0, 1, 2, 0, false, Improvement.FARM),
    CORN(0, 1, 2, 0, false, Improvement.FARM),
    COTTON(2, 1, 0, 0, false, Improvement.PLANTATION), // PLANTATION RELATED
    BANANA(2, 0, 1, 0, false, Improvement.PLANTATION),
    DYE(3, 0, 0, 1, true, Improvement.PLANTATION),
    IRON(0, 3, 0, 0, false, Improvement.MINE), //MINE RELATED
    MERCURY(0, 1, 0, 2, false, Improvement.MINE),
    GOLD(3, 1, 0, 0, true, Improvement.MINE),
    MILK(0, 1, 2, 0, false, Improvement.RANCH), //RANCH RELATED
    EGG(1, 0, 2, 0, false, Improvement.RANCH),
    TRUFFLE(3, 0, 1, 0, true, Improvement.RANCH),
    FISH(1, 0, 2, 0, false, Improvement.FISHING), // FISHING RELATED
    CRAB(2, 0, 1, 0, false, Improvement.FISHING),
    WHALE(3, 0, 1, 0, true, Improvement.FISHING);

    private int goldBonus;
    private int productionBonus;
    private int foodBonus;
    private int techBonus;
    private boolean isLuxury;
    private Improvement improvementType;

    private Resource(int goldBonus, int productionBonus, int foodBonus, int techBonus, boolean isLuxury, Improvement improvementType) {
        this.goldBonus = goldBonus;
        this.productionBonus = productionBonus;
        this.foodBonus = foodBonus;
        this.techBonus = techBonus;
        this.isLuxury = isLuxury;
        this.improvementType = improvementType;
    }

    public int getGoldBonus() {
        return goldBonus;
    }

    public int getProductionBonus() {
        return productionBonus;
    }

    public int getFoodBonus() {
        return foodBonus;
    }

    public int getTechBonus() {
        return techBonus;
    }

    public Improvement getImprovementType() {
        return improvementType;
    }

    public boolean isLuxury() {
        return isLuxury;
    }

}
