package civilizationclone;

public enum CityProject {

    //producitonCost, purchaseCost, techBonus, productionBonus, foodBonus, goldBonus
    GRANARY(80, 120, 0, 1, 5, 0),
    LIBRARY(100, 200, 8, 0, 0, 0),
    WORKSHOP(100, 200, 0, 8, 0, 0),
    MARKET(100, 200, 0, 0, 0, 8);

    //basic bonuses
    private int productionCost;
    private int purchaseCost;

    private int techBonus;
    private int productionBonus;
    private int foodBonus;
    private int goldBonus;

    private CityProject(int productionCost, int purchaseCost, int techBonus, int productionBonus, int foodBonus, int goldBonus) {
        this.productionCost = productionCost;
        this.purchaseCost = purchaseCost;
        this.techBonus = techBonus;
        this.productionBonus = productionBonus;
        this.foodBonus = foodBonus;
        this.goldBonus = goldBonus;
    }

    public int getGoldBonus() {
        return goldBonus;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }

    public int getTechBonus() {
        return techBonus;
    }

    public int getProductionBonus() {
        return productionBonus;
    }

    public int getFoodBonus() {
        return foodBonus;
    }

}
