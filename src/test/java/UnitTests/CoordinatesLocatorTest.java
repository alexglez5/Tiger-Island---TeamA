package UnitTests;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nathanbarnavon on 4/4/17.
 */
public class CoordinatesLocatorTest {

    CoordinatesLocator locator = new CoordinatesLocator();
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

    @Test
    public void testFindingOrientations() {
        Coordinate c = new Coordinate(0,0,0);
        Coordinate[] partOfTile = locator.produceCoordinatesFromOrientation(c, 1);
        Assert.assertTrue(game.checkHexLocation(partOfTile[0]));
        Assert.assertTrue(game.checkHexLocation(partOfTile[1]));
        partOfTile = locator.produceCoordinatesFromOrientation(c, 2);
        Assert.assertTrue(game.checkHexLocation(partOfTile[0]));
        Assert.assertFalse(game.checkHexLocation(partOfTile[1]));
        partOfTile = locator.produceCoordinatesFromOrientation(c, 3);
        Assert.assertFalse(game.checkHexLocation(partOfTile[0]));
        Assert.assertTrue(game.checkHexLocation(partOfTile[1]));
        partOfTile = locator.produceCoordinatesFromOrientation(c, 4);
        Assert.assertTrue(game.checkHexLocation(partOfTile[0]));
        Assert.assertTrue(game.checkHexLocation(partOfTile[1]));
        partOfTile = locator.produceCoordinatesFromOrientation(c, 5);
        Assert.assertTrue(game.checkHexLocation(partOfTile[0]));
        Assert.assertFalse(game.checkHexLocation(partOfTile[1]));
        partOfTile = locator.produceCoordinatesFromOrientation(c, 6);
        Assert.assertFalse(game.checkHexLocation(partOfTile[0]));
        Assert.assertTrue(game.checkHexLocation(partOfTile[1]));

    }

    @After
    public void teardown() {
        game.clearBoard();
    }
}
