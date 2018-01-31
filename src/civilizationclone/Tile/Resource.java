package civilizationclone.Tile;

public enum Resource {

    //GOLD, PRODUCTION, FOOD, TECH, LUXURY, IMPROVEMENT
    NONE(0, 0, 0, 0, false, null),
    WHEAT(0, 0, 2, 0, false, Improvement.FARM), // FARM RELATED
    RICE(0, 0, 2, 0, false, Improvement.FARM),
    COTTON(2, 0, 0, 0, false, Improvement.PLANTATION), // PLANTATION RELATED
    BANANA(1, 0, 1, 0, false, Improvement.PLANTATION),
    WINE(3, 0, 0, 1, true, Improvement.PLANTATION),
    IRON(0, 2, 0, 0, false, Improvement.MINE), //MINE RELATED
    MERCURY(0, 2, 0, 2, true, Improvement.MINE),
    CATTLE(0, 1, 1, 0, false, Improvement.RANCH), //RANCH RELATED
    SHEEP(1, 0, 1, 0, false, Improvement.RANCH),
    TRUFFLE(3, 0, 1, 0, true, Improvement.RANCH),
    FISH(0, 0, 2, 0, false, Improvement.FISHING), // FISHING RELATED
    CRAB(1, 0, 1, 0, false, Improvement.FISHING),
    WHALE(3, 0, 1, 0, true, Improvement.FISHING),
    STONE(0, 2, 0, 0, false, Improvement.MINE), // QUARRY RELATED 
    OIL(1, 0, 0, 0, false, Improvement.OILWELL); // OILWELL RELATED
    

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
