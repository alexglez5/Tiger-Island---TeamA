package UnitTests;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nathanbarnavon on 4/2/17.
 */
public class TileValidatorTest {

    Game game = new Game();
    TileValidator validator = new TileValidator();

    @Before
    public void setup() {
        game.placeFirstTile();
    }

    @Test
    public void testPlacementOnLevelOne() {
        Assert.assertTrue(validator.canPlaceTileOnLevelOne(new Coordinate(1,-1,0), 2));
    }

    @After
    public void teardown() {
        game.clearBoard();
    }
}
