package civilizationclone.Unit;

public enum UnitType {

    SETTLER(90,150),
    BUILDER(60, 120),
    SCOUT(40, 80), 
    WARRIOR(50, 110), 
    SLINGER(50, 110), 
    HORSEMAN(60,130),
    ARCHER(80,145);

    private int productionCost;
    private int purchaseCost;

    private UnitType(int productionCost, int purchaseCost) {
        this.productionCost = productionCost;
        this.purchaseCost = purchaseCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }

}
