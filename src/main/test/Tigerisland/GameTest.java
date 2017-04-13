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
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,-1)).getTerrainType(), TerrainType.JUNGLE);
        Assert.assertEquals(map.getBoard().get(new Coordinate(1,-1)).getTerrainType(), TerrainType.LAKE);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,0)).getTerrainType(), TerrainType.VOLCANO);
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1,1)).getTerrainType(), TerrainType.ROCK);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getTerrainType(), TerrainType.GRASS);
    }

    @Test
    public void testTerrainTypeOfEveryHexOfTilePlaced() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,0)).getTerrainType(),
                TerrainType.VOLCANO);
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.ROCK);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.LAKE);
    }

    @Test
    public void testEveryHexIsPlacedAtTheRightIndex() throws Exception{
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
                new Coordinate(0,2), Orientation.FromBottom);
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1,3)).getTerrainType(),
                TerrainType.ROCK);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,2)).getTerrainType(),
                TerrainType.VOLCANO);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,3)).getTerrainType(),
                TerrainType.LAKE);
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.getBoard().clear();
    }

    @After
    public void resetTileIds() throws Exception {
        Tile.setNumOfTilesCreated(0);
    }
}
