package Tigerisland;

import org.junit.After;
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
    public void testTerrainTypeOfEveryHexOfTilePlaced() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getTerrainType(),
                TerrainType.VOLCANO);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.LAKE);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.ROCK);
    }

    @Test
    public void testEveryHexIsPlacedAtTheRightIndex() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,2), Orientation.FromBottom);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,3)).getTerrainType(),
                TerrainType.LAKE);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,2)).getTerrainType(),
                TerrainType.VOLCANO);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,3)).getTerrainType(),
                TerrainType.ROCK);
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
    }

    @After
    public void resetTileIds() throws Exception {
        Tile.setNumOfTilesCreated(0);
    }
}
