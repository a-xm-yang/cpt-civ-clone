
package civilizationclone.Tile;

public class Desert extends Tile{

    public Desert() {
        super(false, 1);
    }

    @Override
    public void improve() {
        this.setImprovement(Improvement.FARM);
        super.improve();
    }

    @Override
    public void calcOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
