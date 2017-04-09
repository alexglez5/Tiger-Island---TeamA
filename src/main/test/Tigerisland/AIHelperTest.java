package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AIHelperTest{
    AIHelper helper;

    @Before
    public void initializeGameBoard() throws Exception {
        helper = new AIHelper();
        helper.map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCKY),
                new Coordinate(0, 0), Orientation.FromBottom);
    }

    @Test
    public void shouldReturnArrayListOfCoordinatesWhereTotoroCanBePlaced() throws Exception {
        helper.map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.GRASSLANDS, TerrainType.ROCKY),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);
        helper.map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.JUNGLE),
                new Coordinate(1, 3), Orientation.FromBottomLeft);

        helper.map.foundNewSettlement(new Coordinate(1, 1));
        helper.map.expandSettlement(new Coordinate(1, 1), TerrainType.ROCKY);

        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(0, 4)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(2, 2)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(2, 0)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(-1, 1)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(-1, 3)));
        Assert.assertEquals(helper.getPlacesWhereTotoroCanBePlaced().size(), 5);
    }

    @Test
    public void shouldReturnArrayListOfCoordinatesWhereTigerCanBePlaced() throws Exception {
        helper.map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.GRASSLANDS),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.GRASSLANDS, TerrainType.ROCKY),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        helper.map.placeTile(new Tile(TerrainType.ROCKY, TerrainType.LAKE),
                new Coordinate(2, 1), Orientation.FromBottom);

        helper.map.foundNewSettlement(new Coordinate(1,1));
        helper.map.getBoard().get(new Coordinate(2,0)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(1,0)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(0,0)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(0,1)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(-1,1)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(-1,2)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(0,2)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(-1,3)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(1,2)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(2,2)).setLevel(3);
        helper.map.getBoard().get(new Coordinate(2,1)).setLevel(3);

        helper.map.getBoard().get(new Coordinate(-1,1)).placeTiger();
        helper.map.getBoard().get(new Coordinate(-1,1)).setSettlementID(3);
        helper.map.getSettlements().put(3, new Settlement(new Coordinate(-1,1)));
        helper.map.getSettlements().get(3).placeTiger();

        Assert.assertTrue(helper.getPlacesWhereTigerCanBePlaced().contains(new Coordinate(0, 1)));
        Assert.assertTrue(helper.getPlacesWhereTigerCanBePlaced().contains(new Coordinate(0, 2)));
        Assert.assertTrue(helper.getPlacesWhereTigerCanBePlaced().contains(new Coordinate(1, 2)));
        Assert.assertTrue(helper.getPlacesWhereTigerCanBePlaced().contains(new Coordinate(2, 0)));
        Assert.assertEquals(helper.getPlacesWhereTigerCanBePlaced().size(), 4);
    }

    @After
    public void deallocateHexesInMap() throws Exception {
        helper.map.resetGame();
    }
}