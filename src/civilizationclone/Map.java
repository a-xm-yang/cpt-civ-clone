package civilizationclone;
 
import civilizationclone.Tile.Tile;
 
 
public class Map {
   
    public Tile[][] map;
    public float[][] simplexNoise;
 
    public Map() {
        this.map = new Tile[80][40];
    }
   
    public void generateMap(){
        simplexNoise = generateSimplexNoise(this.map.length,this.map[0].length);
 
        for(int i=0;i<simplexNoise.length;i++){
            for(int k=0;k<simplexNoise[i].length;k++){
                System.out.print(simplexNoise[i][k] + " ");
            }
            System.out.println("");
        }
    }
   
    public float[][] generateSimplexNoise(int width, int height){
        
        //ramdom seed
        SimplexNoise sn = new SimplexNoise(1333);
        
        float[][] simplexnoise = new float[width][height];
        float frequency = 0.04f;
     
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                simplexnoise[x][y] = (float) sn.noise(x * frequency,y * frequency);
                simplexnoise[x][y] = (simplexnoise[x][y] + 1) / 2;   //generate values between 0 and 1
            }
        }
     
        return simplexnoise;
   }
}