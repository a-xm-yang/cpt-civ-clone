
package civilizationclone.Tile;

public enum Improvement{
    FARM(0,-1,3), MINE(1,2,-1), FISHING(1,0,2), RANCH(-1,2,1), PLANTATION(3,0,-1);
    
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
