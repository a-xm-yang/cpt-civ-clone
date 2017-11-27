
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
    
    
}
