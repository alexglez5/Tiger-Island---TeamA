package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander Gonzalez on 4/4/2017.
 */
public class AIHelperTest {
    GameBoard map;
    AIHelper helper;

    @Before
    public void initializeGameBoard() throws Exception {
        map = new GameBoard();
        helper = new AIHelper();
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

        helper.getCoordinatesWhereTotoroCanBePlaced();
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(0, 4)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(2, 2)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(2, 0)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(-1, 1)));
        Assert.assertTrue(helper.getPlacesWhereTotoroCanBePlaced().contains(new Coordinate(-1, 3)));
    }

    @After
    public void deallocateHexesInMap() throws Exception {
        map.getBoard().clear();
        map.getPlayer().resetScoreAndInventory();
        map.getSettlements().clear();
    }
}