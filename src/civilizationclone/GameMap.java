package civilizationclone;

import civilizationclone.Tile.*;
import java.awt.Point;
import java.util.Set;

public class GameMap {

    private Tile[][] map;

    public enum MapSize {
        BIG, MEDIUM, SMALL;
    }

    public GameMap(MapSize m, int seed) {

        int size = 0;

        switch (m) {
            case SMALL:
                size = 40;
                break;
            case MEDIUM:
                size = 80;
                break;
            case BIG:
                size = 120;
                break;
        }

        //Random map generation
        map = new Tile[size][size];
        initializeMap(MapGenerator.generateMap(size, size, seed));
        initializeResource(MapGenerator.generateResourceMap(size, size, seed + 500));

    }

    //MAP INITIALIZAGION
    //<editor-fold>
    private void initializeMap(float[][] simplexNoise) {

        float value = 0f;

        for (int i = 0; i < simplexNoise.length; i++) {
            for (int k = 0; k < simplexNoise[i].length; k++) {
                value = simplexNoise[i][k];
                if (value < 0.3) {
                    map[i][k] = new Ocean();
                } else if (value < 0.8) {

                    int pseudoRandom = (((int) (value * 10000)) % 10);

                    if (pseudoRandom < 2) {
                        map[i][k] = new Hills();
                    } else {
                        map[i][k] = new Plains();
                    }

                } else if (value < 0.90) {
                    map[i][k] = new Desert();
                } else {
                    map[i][k] = new Mountain();
                }

            }
        }

    }

    private void initializeResource(float[][] simplexNoise) {

        for (int i = 0; i < simplexNoise.length; i++) {
            for (int k = 0; k < simplexNoise[i].length; k++) {
                grantResource(simplexNoise[i][k], getTile(i, k));
            }
        }

    }

    private void grantResource(float value, Tile tile) {


        //if value is greater than 0.7 and is not a mountain, grant a random resource according to pseudorandom calculation
        if (value > 0.72) {
            if (!(tile instanceof Mountain)) {

                int pseudoRandom = (int) ((value * 10000) % 10);

                //possibility for plains
                //<editor-fold>
                if (tile instanceof Plains) {
                    switch (pseudoRandom) {
                        case 0:
                            tile.setResource(Resource.RICE);
                            break;
                        case 1:
                            tile.setResource(Resource.WHEAT);
                            break;
                        case 2:
                            tile.setResource(Resource.CORN);
                            break;
                        case 3:
                            tile.setResource(Resource.COTTON);
                            break;
                        case 4:
                            tile.setResource(Resource.BANANA);
                            break;
                        case 5:
                            tile.setResource(Resource.DYE);
                            break;
                        case 6:
                            tile.setResource(Resource.EGG);
                            break;
                        case 7:
                            tile.setResource(Resource.MILK);
                            break;
                        case 8:
                            tile.setResource(Resource.TRUFFLE);
                            break;
                        default:
                            tile.setResource(Resource.WHEAT);
                    }
                } //</editor-fold>
                //possibility for Ocean
                //<editor-fold>
                else if (tile instanceof Ocean) {
                  if (pseudoRandom < 8){
                      tile.setResource(Resource.WHALE);
                  } else if (pseudoRandom < 5){
                      tile.setResource(Resource.CRAB);
                  } else if (pseudoRandom < 1){
                      tile.setResource(Resource.FISH);
                  }
                }
                //</editor-fold>
                //possibility for Hills
                //<editor-fold>
                else if (tile instanceof Hills){
                    if (pseudoRandom < 1){
                        tile.setResource(Resource.MILK);
                    } else if (pseudoRandom < 2){
                        tile.setResource(Resource.EGG);
                    } else if (pseudoRandom < 3){
                        tile.setResource(Resource.TRUFFLE);
                    } else if (pseudoRandom < 5){
                        tile.setResource(Resource.MERCURY);
                    } else if (pseudoRandom < 9){
                        tile.setResource(Resource.IRON);
                    } else {
                        tile.setResource(Resource.GOLD);
                    }
                }
                //</editor-fold>
                
                tile.calcOutput();
            }
        }
    }
    //</editor-fold>
    
    //GETTER & SETTER
    //<editor-fold>
    public Set<Tile> getVisibleTiles(Point[] allPositions){
        return null;
    }
    
    public Tile[][] getMap() {
        return map;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }
    
    

    //</editor-fold>
}
