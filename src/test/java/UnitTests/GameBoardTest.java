package UnitTests;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by nathanbarnavon on 4/1/17.
 */
public class GameBoardTest {

    GameBoard map;
    Hex hex;
    Coordinate cord;

    @Before
    public void initialize() throws Exception {
        map = new GameBoard();
        hex = new Hex(TerrainType.ROCK, 1);
        cord = new Coordinate(5,0,-3);
    }

    @Test
    public void testGameBoardCreation() throws Exception {
        Assert.assertNotNull(map.getGameBoard());
    }

    @Test
    public void testHexExistsAtLocationAfterInsert() throws Exception {
        map.placeHex(hex, cord);
        Assert.assertTrue(map.checkForHex(cord));
    }

    @Test
    public void testRetrievalOfHexFromGameBoard() throws Exception {
        map.placeHex(hex, cord);
        Hex test = map.getHex(cord);
        Assert.assertNotNull(test);
    }

    @After
    public void cleanup() throws Exception {
        map.clearBoard();
    }

}
