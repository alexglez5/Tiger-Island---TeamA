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
    public void testHexesPlacementWithRotation1()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 1);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
    }

    @Test
    public void testHexesPlacementWithRotation2()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 2);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
    }

    @Test
    public void testHexesPlacementWithRotation3()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 3);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
    }

    @Test
    public void testHexesPlacementWithRotation4()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 4);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,-1)));
    }

    @Test
    public void testHexesPlacementWithRotation5()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 5);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,0)));
    }

    @Test
    public void testHexesPlacementWithRotation6()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), 6);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
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
