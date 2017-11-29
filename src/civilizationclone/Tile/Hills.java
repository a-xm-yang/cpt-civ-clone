
package civilizationclone.Tile;

public class Hills extends Tile{
    
    public Hills(){
        super(false, 2);
    }

    @Override
    public void improve() {
        this.setImprovement(Improvement.MINE);
        super.improve();
    }

    @Override
    public void calcOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
