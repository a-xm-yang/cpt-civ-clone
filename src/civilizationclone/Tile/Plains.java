
package civilizationclone.Tile;

public class Plains extends Tile {
    
    public Plains(){
        
        super(false, 1);
    }
    
    
    
    //TODO decide if resources will be determined when tile is created, or when map is created

    @Override
    public void improve() {
        this.setImprovement(Improvement.FARM); // Maybe check to see if there is a resource, and build a plantation instead
        
    }
}
