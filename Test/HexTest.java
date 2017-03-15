import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by NotKali on 3/14/2017.
 */
public class HexTest {
    private Hex.TerrainType terrain = Hex.TerrainType.VOLCANO;
    private int TileIdentifier = 1;
    private boolean occupied = false;
    private int level = 1;

    Hex firstTile = new Hex(terrain, TileIdentifier, occupied, level);

    @Test
    public void hasFeatures() throws Exception {
        assertTrue(firstTile.hasFeatures());
    }

    @Test
    public void hasTileIdentifier() throws Exception{
        assertTrue(firstTile.whichTile() == 1);
    }

    @Test
    public void checksIsEmpty(){
        assertTrue(firstTile.isEmpty());
    }

    @Test
    public void checkWhichLevel(){
        assertTrue(firstTile.whichLevel() == 1);
    }
}

