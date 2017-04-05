package UnitTests;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nathanbarnavon on 4/2/17.
 */
public class TileTest {

    GameBoard map;
    Tile tile;
    Hex hex;

    @Before
    public void setup() throws Exception {
        map = new GameBoard();
    }

    @Test
    public void testIncrementOfNumberOfTilesCreated() throws Exception {
        int toCheck = Tile.getNumberOfTilesCreated();
        tile = new Tile(TerrainType.GRASS, TerrainType.ROCK);
        Assert.assertEquals(tile.getTileID(), toCheck + 1);
    }

    @After
    public void teardown() {
        map.clearBoard();
    }

}
