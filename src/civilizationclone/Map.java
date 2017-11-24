package civilizationclone;

import civilizationclone.Tile.Tile;
import static java.lang.Math.sqrt;

public class Map {

    public Tile[][] map;
    public float[][] simplexNoise;
    public float[][] radialGradient;

    public Map() {
        this.map = new Tile[160][160];
    }

    public void generateMap() {
        simplexNoise = generateSimplexNoise(this.map.length, this.map[0].length);
        radialGradient = generateRadialGradient(map.length, map[0].length);

        for (int i = 0; i < radialGradient.length; i++) {
            for (int k = 0; k < radialGradient[i].length; k++) {
                simplexNoise[i][k] -= radialGradient[i][k];
            }
        }

        for (int i = 0; i < simplexNoise.length; i++) {
            for (int k = 0; k < simplexNoise[i].length; k++) {
                System.out.print(simplexNoise[i][k] + " ");
            }
            System.out.println("");
        }
    }

    public float[][] generateSimplexNoise(int width, int height) {

        //ramdom seed
        SimplexNoise sn = new SimplexNoise(300);

        float[][] simplexnoise = new float[width][height];
        float frequency = 0.04f;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                simplexnoise[x][y] = (float) sn.noise(x * frequency, y * frequency);
                simplexnoise[x][y] = (simplexnoise[x][y] + 1) / 2f;   //generate values between 0 and 1
            }
        }

        return simplexnoise;
    }

    public float[][] generateRadialGradient(int width, int height) {

        float[][] radialGradient = new float[width][height];

        int centerX = width / 2;
        int centerY = height / 2;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float distanceX = (centerX - x) * (centerX - x);
                float distanceY = (centerY - y) * (centerY - y);

                float distanceToCenter = (float) sqrt(distanceX + distanceY);

                distanceToCenter = distanceToCenter / 90f;

                radialGradient[x][y] = distanceToCenter;
            }
        }

        return radialGradient;
    }
}
