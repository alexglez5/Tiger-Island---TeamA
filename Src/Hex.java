/**
 * Created by NotKali on 3/8/2017.
 */

public class Hex {
    public enum TerrainType{
        VOLCANO, WATER, JUNGLE, PLAIN, MOUNTAIN;
    }
    public int level;
    private boolean occupied;
    private TerrainType terrain;

    private int TileIdentifier;

    public Hex(TerrainType terrain, int TileIdentifier, boolean occupied, int level) {
        this.terrain = terrain;
        this.TileIdentifier = TileIdentifier;
        this.occupied = occupied;
        this.level = level;
    }

    public boolean hasFeatures() {
        return (terrain!=null);
    }

    public int whichTile(){
        return TileIdentifier;
    }

    public boolean isEmpty(){
        return(!occupied);
    }

    public int whichLevel(){
        return level;
    }

    public void setTileIdentifier(int tileIdentifier) {
        TileIdentifier = tileIdentifier;
    }
}

