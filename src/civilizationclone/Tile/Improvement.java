
package civilizationclone.Tile;

public enum Improvement{
    
    //GOLD, PRODUCTION, FOOD
    FARM(0,0,3), MINE(0,3,0), FISHING(1,0,2), RANCH(0,2,1), PLANTATION(3,0,0), ROAD(0,0,0);
    
    private int goldBonus;
    private int productionBonus;
    private int foodBonus;

    private Improvement(int goldBonus, int productionBonus, int foodBonus) {
        this.goldBonus = goldBonus;
        this.productionBonus = productionBonus;
        this.foodBonus = foodBonus;
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
}
