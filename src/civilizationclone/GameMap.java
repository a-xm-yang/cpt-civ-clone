package civilizationclone;

import civilizationclone.Tile.*;

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
                grantResource(simplexNoise[i][k], getTile(i,k));
            }
        }

    }
    
    private void grantResource(float value, Tile tile){
        
        if (value < 0.9){
            int pseudoRandom = (int) ((value * 10000) % 10); 
            
            if (pseudoRandom < 4){
                
            }
            
        }
    }

    //GETTER & SETTER
    //<editor-fold>
    public Tile[][] getMap() {
        return map;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    //</editor-fold>
}
