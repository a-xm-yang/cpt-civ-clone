package civilizationclone.Tile;

public enum Improvement{
    
    //GOLD, PRODUCTION, FOOD, TECH
    NONE(0,0,0,0),
    FARM(0,0,2,0), 
    MINE(0,2,0,0), 
    FISHING(1,0,1,0), 
    RANCH(0,1,1,0), 
    PLANTATION(2,0,0,0), 
    ACADEMY(0,0,0,2);
    
    private int goldBonus;
    private int productionBonus;
    private int foodBonus;
    private int techBonus;

    private Improvement(int goldBonus, int productionBonus, int foodBonus, int techBonus) {
        this.goldBonus = goldBonus;
        this.productionBonus = productionBonus;
        this.foodBonus = foodBonus;
        this.techBonus = techBonus;
    }

    public int getFoodBonus() {
        return foodBonus;
    }

    public int getGoldBonus() {
        return goldBonus;
    }

    public int getProductionBonus() {
        return productionBonus;
    }

    public int getScienceBonus() {
        return techBonus;
    }
    
    
}
