package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AIHelperTest{
    Game map;
    AIHelper helper;

    @Before
    public void initializeGameBoard() throws Exception {
        map = new Game();
        helper = new AIHelper(map);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0, 0), Orientation.FromBottom);
    }

    @Test
    public void shouldReturnArrayListOfCoordinatesWhereTotoroCanBePlaced() throws Exception {
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2, 1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
                new Coordinate(1, 3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1, 1));
        map.expandSettlement(new Coordinate(1, 1), TerrainType.Rocky);

        for (Coordinate c : helper.getPlacesWhereTotoroCanBePlaced()){
            System.out.println(c.getXCoordinate() + "," + c.getYCoordinate());
        }

//        helper.findCoordinatesWhereTotoroCanBePlaced();
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(0, 4)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(2, 2)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(2, 0)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(-1, 1)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(-1, 3)));
        Assert.assertEquals(helper.getPlacesWhereTotoroCanBePlaced().size(), 5);
    }

    @Test
    public void shouldReturnArrayListOfCoordinatesWhereTigerCanBePlaced() throws Exception {
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1, 0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1, 2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2, 1), Orientation.FromBottom);

        map.foundNewSettlement(new Coordinate(1,1));
        map.getBoard().get(new Coordinate(2,0)).setLevel(3);
        map.getBoard().get(new Coordinate(1,0)).setLevel(3);
        map.getBoard().get(new Coordinate(0,0)).setLevel(3);
        map.getBoard().get(new Coordinate(0,1)).setLevel(3);
        map.getBoard().get(new Coordinate(-1,1)).setLevel(3);
        map.getBoard().get(new Coordinate(-1,2)).setLevel(3);
        map.getBoard().get(new Coordinate(0,2)).setLevel(3);
        map.getBoard().get(new Coordinate(-1,3)).setLevel(3);
        map.getBoard().get(new Coordinate(1,2)).setLevel(3);
        map.getBoard().get(new Coordinate(2,2)).setLevel(3);
        map.getBoard().get(new Coordinate(2,1)).setLevel(3);

        map.getBoard().get(new Coordinate(-1,1)).placeTiger();
        map.getBoard().get(new Coordinate(-1,1)).setSettlementID(3);
        map.getPlayer().addSettlement(new Settlement(new Coordinate(-1,1)));
//        settlements.put(3, new Settlement());
        map.getPlayer().findSettlement(3).addToSettlement(new Coordinate(-1,1));
        map.getPlayer().findSettlement(3).placeTiger();

        helper.findCoordinatesWhereTigerCanBePlaced();
        Assert.assertTrue(helper.getPlacesWhereTigerCanBePlaced().contains(new Coordinate(0, 1)));
        Assert.assertTrue(helper.getPlacesWhereTigerCanBePlaced().contains(new Coordinate(0, 2)));
        Assert.assertTrue(helper.getPlacesWhereTigerCanBePlaced().contains(new Coordinate(1, 2)));
        Assert.assertTrue(helper.getPlacesWhereTigerCanBePlaced().contains(new Coordinate(2, 0)));
        Assert.assertEquals(helper.getPlacesWhereTigerCanBePlaced().size(), 4);
    }

    @After
    public void deallocateHexesInMap() throws Exception {
        map.getBoard().clear();
        map.getPlayer().resetScoreAndInventory();
        map.getPlayer().getSettlements().clear();
    }
}