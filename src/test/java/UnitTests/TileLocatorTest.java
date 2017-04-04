package UnitTests;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nathanbarnavon on 4/4/17.
 */
public class TileLocatorTest {

    TileLocator locator = new TileLocator();
    Game game = new Game();

    @Before
    public void setup() {
        game.placeFirstTile();
    }

    @Test
    public void testFindingTilesAroundACoordinate() {
        Coordinate cord = new Coordinate(0,0,0);
        Coordinate[] adjacent = locator.produceClockwiseNeighborCoordinates(cord);
        Assert.assertTrue(game.checkHexLocation(adjacent[0]));
        Assert.assertFalse(game.checkHexLocation(adjacent[1]));
        Assert.assertTrue(game.checkHexLocation(adjacent[2]));
        Assert.assertTrue(game.checkHexLocation(adjacent[3]));
        Assert.assertFalse(game.checkHexLocation(adjacent[4]));
        Assert.assertTrue(game.checkHexLocation(adjacent[5]));
    }

    

    @After
    public void teardown() {
        game.clearBoard();
    }
}
