package Tigerisland;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 3/19/2017.
 */

public class GameBoardTest {
    GameBoard map;

    @Before
    public void initializeGameBoard() throws Exception{
        map = new GameBoard();
    }

    @Test
    public void testHexesArePlacedAtTheRightCoordinate()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 1);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(2,3)));
    }

    @Test
    public void testHexesAreNotPlacedAtTheWrongCoordinate()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 1);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(1,0)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1,0)));
    }

    @Test
    public void testTerrainTypeOfEveryHexOfTilePlaced() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 1);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getTerrainType(),
                TerrainType.Volcano);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.Lake);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.Rocky);
    }
}
