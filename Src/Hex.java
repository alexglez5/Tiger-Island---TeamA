/**
 * Created by NotKali on 3/8/2017.
 */

public class Hex {
    public enum TerrainType{
        VOLCANO, WATER, JUNGLE, PLAIN, MOUNTAIN;
    }

    private TerrainType terrain;

    public Hex(TerrainType terrain) {
        this.terrain = terrain;
    }

    public boolean hasFeatures() {
        return (terrain!=null);
    }
}

