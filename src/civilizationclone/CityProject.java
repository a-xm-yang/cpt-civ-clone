package civilizationclone;

public enum CityProject {

    //producitonCost, purchaseCost, techBonus, productionBonus, foodBonus, goldBonus
    GRANARY(65, 115, 0, 0, 2, 0),
    LIBRARY(80, 140, 3, 0, 0, 0),
    LIGHTHOUSE(105, 180, 0, 0, 2, 2),
    STABLE(105, 180, 0, 4, 0, 0),
    BARRACKS(105, 180, 0, 4, 0, 0),
    MARKET(105, 180, 0, 0, 0, 4),
    WORKSHOP(175, 305, 0, 5, 0, 0),
    UNIVERSITY(200, 350, 6, 0, 0, 0),
    BANK(275, 480, 0, 0, 0, 7),
    SHIPYARD(265, 460, 0, 0, 3, 3),
    FACTORY(380, 660, 0, 7, 0, 0),
    HOSPITAL(400, 700, 0, 0, 5, 0),
    STOCKEXCHANGE(380, 660, 0, 0, 0, 10),
    ANCIENTWALL(80, 0, 0, 0, 0, 0),
    MEDIEVALWALL(150, 0, 0, 0, 0, 0),
    RENAISSANCEWALL(250, 0, 0, 0, 0, 0);

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
