
package civilizationclone.Tile;

public class Ocean extends Tile{
    
    public Ocean(){
        
        super(true, 1);
    }

    @Override
    public void improve() {
        this.setImprovement(Improvement.FISHING);
        super.improve();
    }

    @Override
    public void calcOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
