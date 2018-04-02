package civilizationclone.Tile;

public enum Improvement {

    //GOLD, PRODUCTION, FOOD, TECH
    NONE(0, 0, 0, 0),
    FARM(0, 0, 2, 0),
    MINE(0, 2, 0, 0),
    QUARRY(0, 2, 0, 0),
    OILWELL(3, 3, 0, 0),
    FISHING(1, 0, 1, 0),
    RANCH(0, 1, 1, 0),
    PLANTATION(2, 0, 0, 0),
    ACADEMY(0, 0, 0, 2);

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
