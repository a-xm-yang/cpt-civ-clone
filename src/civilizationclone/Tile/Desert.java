
package civilizationclone.Tile;

public class Desert extends Tile{

    public Desert() {
        super(false, 1);
    }

    @Override
    public void improve() {
        this.setImprovement(Improvement.FARM);
    }
    
    
    
    
}
