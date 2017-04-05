package UnitTests;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nathanbarnavon on 4/4/17.
 */
public class TileValidatorTest {

    Game app = new Game();
    TileValidator validator = new TileValidator();

    @Before
    public void setup() {
        app.placeFirstTile();
    }

    @Test
    public void shouldFailWhenTilePlayedOnDifferentLevels() {
        Assert.assertFalse(validator.checkHexesAtSameLevel(new Coordinate(1,-1,0), 1));
    }

    @Test
    public void shouldPassWhenTilePlayedOnSameLevels() {
        Assert.assertTrue(validator.checkHexesAtSameLevel(new Coordinate(0,0,0),1));
    }

    @Test
    public void shouldPassWhenTilePlayedOnAllEmpty() {
        Assert.assertTrue(validator.checkHexesAtSameLevel(new Coordinate(2,-2,0), 4));
    }

    @Test
    public void shouldPassWhenTileIsPlacedAdjacent() {
        Assert.assertTrue(validator.checkConnectingToGameBoard(new Coordinate(1,-1,0),2));
        Assert.assertTrue(validator.checkConnectingToGameBoard(new Coordinate(1,-3,2), 1));
    }

    @Test
    public void shouldFailWhenTileIsPlacedSeparated() {
        Assert.assertFalse(validator.checkConnectingToGameBoard(new Coordinate(1,-3,2), 3));
    }

    @Test
    public void shouldFailWhenTileCoversOnlyOneTile() {
        Assert.assertFalse(validator.expandsMultipleTiles(new Coordinate(0,0,0), 1));
    }

    @Test
    public void shouldFailWhenNotPlacedOnVolcano() {
        Assert.assertFalse(validator.canPlaceTile(new Coordinate(0,1,-1), 3));
    }
    @After
    public void teardown() {
        app.clearBoard();
    }

}
