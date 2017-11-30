package civilizationclone;

import civilizationclone.Tile.*;
import java.util.Random;

public class GameMap {

    private Tile[][] map;
    private float[][] simplexNoise;
    private int seed;

    public enum MapSize {
        BIG, MEDIUM, SMALL;
    }

    public GameMap(MapSize m, int seed) {

        switch (m) {
            case SMALL:
                simplexNoise = MapGenerator.generateMap(40, 40, seed);
                map = new Tile[40][40];
                break;
            case MEDIUM:
                simplexNoise = MapGenerator.generateMap(80, 80, seed);
                map = new Tile[80][80];
                break;
            case BIG:
                simplexNoise = MapGenerator.generateMap(120, 120, seed);
                map = new Tile[120][120];
                break;
        }

        this.seed = seed;

        initializeMap();
    }

    private void initializeMap() {

        float value = 0f;

        for (int i = 0; i < simplexNoise.length; i++) {
            for (int k = 0; k < simplexNoise[i].length; k++) {
                
                value = simplexNoise[i][k];
                if (value < 0.3) {
                    map[i][k] = new Ocean();
                } else if (value < 0.8) {
                   
                    int pseudoRandom = (((int)(value * 10000)) % 10);
                    
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

    //GETTER & SETTER
    //<editor-fold>
    public Tile[][] getMap() {
        return map;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    public float[][] getSimplexNoise() {
        return simplexNoise;
    }

    //</editor-fold>
}
