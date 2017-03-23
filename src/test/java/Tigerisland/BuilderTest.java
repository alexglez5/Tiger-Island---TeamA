package Tigerisland;

import cucumber.api.java.en.When;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class BuilderTest {
    GameBoard map;

    @Before
    public void initializeGameBoard() throws Exception{
        map = new GameBoard();
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromBottom);
    }

    @Test
    public void testFoundNewSettlementUpdatesHasVillagerStatusInMap() throws Exception {
        map.foundNewSettlement(new Coordinate(-1,1));
        Assert.assertTrue(map.gameBoard.get(new Coordinate(-1,1)).hasVillager());
    }

    @Test
    public void testVillagerIsNotPlacedOnTopOfVolcano() throws Exception{
        map.foundNewSettlement(new Coordinate(0,0));
        Assert.assertFalse(map.gameBoard.get(new Coordinate(0,0)).hasVillager());
    }

    @Test
    public void testVillagerIsPlacedOnLevelOneWhenFindingNewSettlement() throws Exception{
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands, 3),
                new Coordinate(0,0), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(1,0));
        Assert.assertFalse(map.gameBoard.get(new Coordinate(1,0)).hasVillager());
    }

    @Test
    public void testSettlementFullyExpands() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky, 3),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1,1)).hasVillager());
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,1)).getSettlementID(),
                new Coordinate(1,1).hashCode());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0,1)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1,1)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0 ,2)).hasVillager());
    }

    @Test
    public void testSettlementDoesNotExpandLongerThanExpected() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky, 3),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);

        Assert.assertFalse(map.gameBoard.get(new Coordinate(-1,3)).hasVillager());
        Assert.assertFalse(map.gameBoard.get(new Coordinate(1,0)).hasVillager());
        Assert.assertFalse(map.gameBoard.get(new Coordinate(0,0)).hasVillager());
        Assert.assertFalse(map.gameBoard.get(new Coordinate(-1,1)).hasVillager());
    }

    @Test
    public void testTotoroIsPlacedAdjacentToSettlementOfSizeFive() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky, 3),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake, 4),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle, 5),
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        int settlementID = map.gameBoard.get(new Coordinate(1,1)).getSettlementID();
        map.placeTotoro(new Coordinate(-1,3), settlementID);

        Assert.assertTrue(map.gameBoard.get(new Coordinate(0,1)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1,1)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0 ,2)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1 ,2)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0 ,3)).hasVillager());

        Assert.assertTrue(map.gameBoard.get(new Coordinate(1 ,2)).getSettlementID() ==
                map.gameBoard.get(new Coordinate(1 ,1)).getSettlementID());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(-1 ,3)).hasTotoro());
    }

    @Test
    public void testTotoroIsNotPlacedIfAdjacentSettlementIsLessThanSizeFive() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Lake, 3),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake, 4),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle, 5),
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        int settlementID = map.gameBoard.get(new Coordinate(1,1)).getSettlementID();
        map.placeTotoro(new Coordinate(-1,3), settlementID);

        Assert.assertTrue(map.gameBoard.get(new Coordinate(0,1)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1,1)).hasVillager());
        Assert.assertFalse(map.gameBoard.get(new Coordinate(0 ,2)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1 ,2)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0 ,3)).hasVillager());

        Assert.assertFalse(map.gameBoard.get(new Coordinate(-1 ,3)).hasTotoro());
    }

    @Test
    public void testSettlementsAreMergedWhenTotoroIsPlacedBetweenThem() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands, 2),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky, 3),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake, 4),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands, 5),
                new Coordinate(1,3), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.Jungle, TerrainType.Rocky, 6),
                new Coordinate(-2,2), Orientation.FromBottom);

        map.foundNewSettlement(new Coordinate(1,1));
        map.foundNewSettlement(new Coordinate(-2,3));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        int settlementID = map.gameBoard.get(new Coordinate(1,1)).getSettlementID();
        map.placeTotoro(new Coordinate(-1,3), settlementID);

        Assert.assertTrue(map.gameBoard.get(new Coordinate(-2 ,3)).hasVillager());

        Assert.assertEquals(map.gameBoard.get(new Coordinate( -2,3)).getSettlementID(),
                map.gameBoard.get(new Coordinate(1 ,1)).getSettlementID());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(-1 ,3)).hasTotoro());
    }


    //TODO test there is not other Totoro in settlement
    //TODO test settlements are properly merged

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
    }
}

