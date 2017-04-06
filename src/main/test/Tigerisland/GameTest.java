package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
    Game map;

    @Before
    public void initializeGameBoard() throws Exception{
        map = new Game();
    }

    @Test
    public void shouldPlaceOneStartingTile(){
        map.placeStartingTile();
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,-1)).getTerrainType(), TerrainType.Jungle);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,-1)).getTerrainType(), TerrainType.Lake);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getTerrainType(), TerrainType.Volcano);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getTerrainType(), TerrainType.Rocky);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getTerrainType(), TerrainType.Grasslands);
    }

    @Test
    public void testTerrainTypeOfEveryHexOfTilePlaced() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getTerrainType(),
                TerrainType.Volcano);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.Lake);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.Rocky);
    }

    @Test
    public void testEveryHexIsPlacedAtTheRightIndex() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,2), Orientation.FromBottom);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,3)).getTerrainType(),
                TerrainType.Lake);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,2)).getTerrainType(),
                TerrainType.Volcano);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,3)).getTerrainType(),
                TerrainType.Rocky);
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
