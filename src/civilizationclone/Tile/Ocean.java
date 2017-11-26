
package civilizationclone.Tile;

public class Ocean extends Tile{
    
    public Ocean(){
        
        super(true, 1);
    }

    @Override
    public void improve() {
        this.setImprovement(Improvement.FISHING);
    }
    
    
}
