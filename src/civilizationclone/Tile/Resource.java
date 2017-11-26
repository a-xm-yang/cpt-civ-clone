
package civilizationclone.Tile;

import civilizationclone.TechType;

public class Resource {
    
    private double rarity;
    private int goldBonus;
    private int scienceBonus;
    private int productionBonus;
    private TechType tech;

    public Resource(double rarity, int goldBonus, int scienceBonus, int productionBonus) {
        this.rarity = rarity;
        this.goldBonus = goldBonus;
        this.scienceBonus = scienceBonus;
        this.productionBonus = productionBonus;
    }

    public double getRarity() {
        return rarity;
    }

    public void setRarity(double rarity) {
        this.rarity = rarity;
    }

    public int getGoldBonus() {
        return goldBonus;
    }

    public void setGoldBonus(int goldBonus) {
        this.goldBonus = goldBonus;
    }

    public int getScienceBonus() {
        return scienceBonus;
    }

    public void setScienceBonus(int scienceBonus) {
        this.scienceBonus = scienceBonus;
    }

    public int getProductionBonus() {
        return productionBonus;
    }

    public void setProductionBonus(int productionBonus) {
        this.productionBonus = productionBonus;
    }

    public TechType getTech() {
        return tech;
    }

    public void setTech(TechType tech) {
        this.tech = tech;
    }
    
    
}




