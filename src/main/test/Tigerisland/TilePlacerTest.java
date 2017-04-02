package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander Gonzalez on 3/20/2017.
 */
public class TilePlacerTest {
    GameBoard map;

    @Before
    public void initializeGameBoard() throws Exception{
        map = new GameBoard();
    }

    @Test
    public void testPlacementFromBottomRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
    }

    @Test
    public void testPlacementFromBottomRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
    }

    @Test
    public void testPlacementFromTopRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromTopRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
    }

    @Test
    public void testPlacementFromTopRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromTop);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,-1)));
    }

    @Test
    public void testPlacementFromTopLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(0,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,0)));
    }

    @Test
    public void testPlacementFromBottomLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottomLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,1)));
    }

    @Test
    public void testAtLeastOneEdgeTouchesEdgeOfPreviouslyPlacedTileIfPlacingInLevelOne() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Jungle),
                new Coordinate(1,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,0)));

        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,-1), Orientation.FromTopRight);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(2,-2)));

        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Grasslands),
                new Coordinate(-1,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.gameBoard.containsKey(new Coordinate(-1,-1)));
    }

    @Test
    public void testTileIsNotPlacedIfNoEdgeTouchesPreviouslyPlacedTileIfPlacingInLevelOne() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);

        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(0,-2), Orientation.FromBottomLeft);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1, -2)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1,-1)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-2,0)));

        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Jungle),
                new Coordinate(3,0), Orientation.FromBottomLeft);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(-1,3)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(3,0)));
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(2,0)));
    }

    @Test
    public void testNukeIncreasesHexLevel() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(),2);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getLevel(),2);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,0)).getLevel(),2);
    }

    @Test
    public void testNukeOverwritesTerrainTypeOfHexes() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.Rocky);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getTerrainType(),
                TerrainType.Volcano);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,0)).getTerrainType(),
                TerrainType.Grasslands);
    }

    @Test
    public void testVolcanoIsPlacedOnTopOfAnotherVolcano() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,1), Orientation.FromTop);
        map.placeTile(new Tile(TerrainType.Jungle, TerrainType.Lake),
                new Coordinate(1,0), Orientation.FromBottom);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,0)).getTerrainType(),
                TerrainType.Rocky);
    }

    @Test
    public void testTileIsNotPerfectlyOnTopOfOtherTile() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.Lake);
    }

    @Test
    public void testTileCompletelyCoversHexesBeneathIt() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);

        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(1,0)));

        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomLeft);
        Assert.assertFalse(map.gameBoard.containsKey(new Coordinate(1,0)));

        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.Lake);
    }

    @Test
    public void testNukingIsNotDoneIfItReducesAnySettlementSizeToZero() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(0,1));
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertTrue(map.gameBoard.get(new Coordinate(0,1)).hasVillager());
        Assert.assertNotEquals(map.gameBoard.get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.Grasslands);
        Assert.assertNotEquals(map.gameBoard.get(new Coordinate(1,1)).getTerrainType(),
                TerrainType.Rocky);
    }

    @Test
    public void testTileIsNeverPlacedOnTopOfTotoro() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.gameBoard.get(new Coordinate(0,1)).placeTotoro();
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(), 1);

    }

    @Test
    public void testTileIsNeverPlacedOnTopOfTiger() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.gameBoard.get(new Coordinate(0,1)).placeTiger();
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(), 1);
    }

    //todo uncomment test and pass it
    @Test
    public void shouldSplitSettlementNuked() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.gameBoard.get(new Coordinate(-1,1)).placeVillagers();
        map.gameBoard.get(new Coordinate(0,1)).placeVillagers();
        map.gameBoard.get(new Coordinate(1,0)).placeVillagers();
        map.gameBoard.get(new Coordinate(2,0)).placeVillagers();

        map.gameBoard.get(new Coordinate(-1,1)).setSettlementID(3);
        map.gameBoard.get(new Coordinate(0,1)).setSettlementID(3);
        map.gameBoard.get(new Coordinate(1,0)).setSettlementID(3);
        map.gameBoard.get(new Coordinate(2,0)).setSettlementID(3);

        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

//        Assert.assertNotEquals(map.gameBoard.get(new Coordinate(-1,1)).getSettlementID(),
//                map.gameBoard.get(new Coordinate(2,0)).getSettlementID());
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
        map.getPlayer().resetScoreAndInventory();
    }
}