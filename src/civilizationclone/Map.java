package civilizationclone;

import civilizationclone.Tile.Tile;
import static java.lang.Math.sqrt;

public class Map {

    public Tile[][] map;
    public float[][] simplexNoise;
    public float[][] radialGradient;

    public Map() {
        this.map = new Tile[80][80];
        simplexNoise = MapGenerator.generateMap(80, 80, 333);
    }

 
}
