package civilizationclone;

import civilizationclone.Tile.*;
import civilizationclone.Unit.Unit;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameMap {

    private Tile[][] map;
    private int size;

    public enum MapSize {
        LARGE, MEDIUM, SMALL;
    }

    public GameMap(MapSize m, int seed) {

        size = 0;

        switch (m) {
            case SMALL:
                size = 20;
                break;
            case MEDIUM:
                size = 40;
                break;
            case LARGE:
                size = 80;
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

        //Initialize the map with a pseudo random number taken randomly from the 5th digit, and use that to determine terrain
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
                getTile(i, k).calcOutput();
            }
        }

    }

    private void grantResource(float value, Tile tile) {

        //if value is greater than 0.7 and is not a mountain, grant a random resource according to pseudorandom calculation
        if (value > 0.75) {
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
                            tile.setResource(Resource.RICE);
                            break;
                        case 3:
                            tile.setResource(Resource.COTTON);
                            break;
                        case 4:
                            tile.setResource(Resource.BANANA);
                            break;
                        case 5:
                            tile.setResource(Resource.WINE);
                            break;
                        case 6:
                            tile.setResource(Resource.SHEEP);
                            break;
                        case 7:
                            tile.setResource(Resource.CATTLE);
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
                    if (pseudoRandom < 5) {
                        tile.setResource(Resource.FISH);
                    } else if (pseudoRandom < 8) {
                        tile.setResource(Resource.CRAB);
                    } else {
                        tile.setResource(Resource.WHALE);
                    }
                } //</editor-fold>
                //possibility for Hills
                //<editor-fold>
                else if (tile instanceof Hills) {
                    if (pseudoRandom < 1) {
                        tile.setResource(Resource.CATTLE);
                    } else if (pseudoRandom < 2) {
                        tile.setResource(Resource.SHEEP);
                    } else if (pseudoRandom < 3) {
                        tile.setResource(Resource.TRUFFLE);
                    } else if (pseudoRandom < 4) {
                        tile.setResource(Resource.OIL);
                    } else if (pseudoRandom < 7) {
                        tile.setResource(Resource.STONE);
                    } else if (pseudoRandom < 9) {
                        tile.setResource(Resource.IRON);
                    } else {
                        tile.setResource(Resource.MERCURY);
                    }
                }
                //</editor-fold>
            }
        }
    }
    //</editor-fold>

    //GETTER & SETTER
    //<editor-fold>
    public Set<Tile> getVisibleTiles(Point[] allPositions) {
        
        //Get all the visible tiles that the current player can see, according to all the tiles it has things on
        Set<Point> allVisibleRange = new HashSet<>();

        for (Point p : allPositions) {
            allVisibleRange.addAll(getRange(p, 2));
        }

        Set<Tile> allVisibleTiles = new HashSet<>();

        for (Point p : allVisibleRange) {
            allVisibleTiles.add(getTile(p.x, p.y));
        }

        return allVisibleTiles;
    }

    public Tile[][] getMap() {
        return map;
    }

    public int getSize() {
        return size;
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    public Tile getTile(Point p) {
        return map[p.x][p.y];
    }

    public Tile[] getTiles(ArrayList<Point> list) {

        //get an array of tiles according to an array of points
        ArrayList<Tile> tileList = new ArrayList<>();

        for (Point p : list) {
            tileList.add(getTile(p));
        }

        return tileList.toArray(new Tile[tileList.size()]);
    }

    public Point getPoint(Tile t) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.getTile(i, j) == t) {
                    return new Point(i, j);
                }
            }
        }

        return null;
    }

    public ArrayList<Point> getRange(Point p, int range) {

        //this methods goes around in a cylinder fashion
        int offset = 1, start = 0;

        ArrayList<Point> boundaries = new ArrayList<>();

        if (p.x % 2 == 0) {
            offset = 0;
        }

        //Top Half and Middle
        for (int i = p.x - range; i <= p.x; i++) { //Make two loops: one for top half, other for bottom half
            start = ((Math.abs(i - p.x) + offset) / 2) + (p.y - range);
            for (int n = start; n < start + range * 2 + 1 - (p.x - i); n++) {
                if (i >= 0 && i < size) {
                    if (n < 0) {
                        boundaries.add(new Point(i, size + n)); //Add the new point to an array list of boundaries
                    } else if (n >= size) {
                        boundaries.add(new Point(i, n - size));
                    } else {
                        boundaries.add(new Point(i, n));
                    }
                }
            }
        }

        //Bottom Half
        for (int i = p.x + 1; i <= p.x + range; i++) {
            start = ((Math.abs(i - p.x) + offset) / 2) + (p.y - range);
            for (int n = start; n < start + range * 2 + 1 - (i - p.x); n++) {
                if (i >= 0 && i < size) {
                    if (n < 0) {
                        boundaries.add(new Point(i, size + n)); //Add the new point to an array list of boundaries
                    } else if (n >= size) {
                        boundaries.add(new Point(i, n - size));
                    } else {
                        boundaries.add(new Point(i, n));
                    }
                }
            }
        }

        return boundaries;
    }

    public boolean canSpawn(Point p) {
        
        //see if a unit can spawn in this locatoin in the beginning of the game according to tile, unit, and adjacency situations
        if (getTile(p) instanceof Ocean || getTile(p) instanceof Mountain || getTile(p.x, p.y + 1) instanceof Ocean || getTile(p.x, p.y + 1) instanceof Mountain 
                && !getTile(p).hasUnit() && !getTile(p.x, p.y + 1).hasUnit()) {
            return false;
        }
        
        for (Tile t: getTiles(getRange(p, 3))){
            if (t.hasUnit()){
                return false;
            }
        }
        

        return true;
    }

    //</editor-fold>
}
