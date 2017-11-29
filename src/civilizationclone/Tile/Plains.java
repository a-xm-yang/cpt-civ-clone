
package civilizationclone.Tile;

public class Plains extends Tile {
    
    public Plains(){
        
        super(false, 1);
    }
    
    
    
    //TODO decide if resources will be determined when tile is created, or when map is created

    @Override
    public void improve() {
        this.setImprovement(Improvement.FARM); // Maybe check to see if there is a resource, and build a plantation instead
        super.improve();
        
    }

    @Override
    public void calcOutput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
