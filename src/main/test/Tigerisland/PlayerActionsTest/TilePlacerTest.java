package Tigerisland.PlayerActionsTest;

import Tigerisland.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,0)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,1)));
    }

    @Test
    public void testPlacementFromBottomRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,0)));
    }

    @Test
    public void testPlacementFromTopRightRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromTopRight);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,-1)));
    }

    @Test
    public void testPlacementFromTopRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromTop);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,-1)));
    }

    @Test
    public void testPlacementFromTopLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(0,-1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,0)));
    }

    @Test
    public void testPlacementFromBottomLeftRotation()throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottomLeft);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,0)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,1)));
    }

    @Test
    public void testAtLeastOneEdgeTouchesEdgeOfPreviouslyPlacedTileIfPlacingInLevelOne() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Jungle),
                new Coordinate(1,0), Orientation.FromBottomRight);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,0)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(2,0)));

        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,-1), Orientation.FromTopRight);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(2,-1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(1,-1)));
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(2,-2)));

        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Grasslands),
                new Coordinate(-1,0), Orientation.FromTopLeft);
        Assert.assertTrue(map.getBoard().containsKey(new Coordinate(-1,-1)));
    }

    @Test
    public void testTileIsNotPlacedIfNoEdgeTouchesPreviouslyPlacedTileIfPlacingInLevelOne() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);

        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(0,-2), Orientation.FromBottomLeft);
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(-1, -2)));
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(-1,-1)));
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(-2,0)));

        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Jungle),
                new Coordinate(3,0), Orientation.FromBottomLeft);
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(-1,3)));
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(3,0)));
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(2,0)));
    }

    @Test
    public void testNukeIncreasesHexLevel() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(),2);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,0)).getLevel(),2);
        Assert.assertEquals(map.getBoard().get(new Coordinate(1,0)).getLevel(),2);
    }

    @Test
    public void testNukeOverwritesTerrainTypeOfHexes() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.Rocky);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,0)).getTerrainType(),
                TerrainType.Volcano);
        Assert.assertEquals(map.getBoard().get(new Coordinate(1,0)).getTerrainType(),
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
        Assert.assertEquals(map.getBoard().get(new Coordinate(1,0)).getTerrainType(),
                TerrainType.Rocky);
    }

    @Test
    public void testTileIsNotPerfectlyOnTopOfOtherTile() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1,1)).getTerrainType(),
                TerrainType.Lake);
    }

    @Test
    public void testTileCompletelyCoversHexesBeneathIt() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);

        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(1,0)));

        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomLeft);
        Assert.assertFalse(map.getBoard().containsKey(new Coordinate(1,0)));

        Assert.assertEquals(map.getBoard().get(new Coordinate(-1,1)).getTerrainType(),
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

        Assert.assertTrue(map.getBoard().get(new Coordinate(0,1)).hasVillager());
        Assert.assertNotEquals(map.getBoard().get(new Coordinate(0,1)).getTerrainType(),
                TerrainType.Grasslands);
        Assert.assertNotEquals(map.getBoard().get(new Coordinate(1,1)).getTerrainType(),
                TerrainType.Rocky);
    }

    @Test
    public void testTileIsNeverPlacedOnTopOfTotoro() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.getBoard().get(new Coordinate(0,1)).placeTotoro();
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(), 1);

    }

    @Test
    public void testTileIsNeverPlacedOnTopOfTiger() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.getBoard().get(new Coordinate(0,1)).placeTiger();
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);

        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(), 1);
    }

    @Test
    public void shouldSplitSettlementNukedByTileWithFromBottomRotation() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.getBoard().get(new Coordinate(-1,1)).placeVillagers();
        map.getBoard().get(new Coordinate(0,1)).placeVillagers();
        map.getBoard().get(new Coordinate(1,1)).placeVillagers();
        map.getBoard().get(new Coordinate(2,0)).placeVillagers();
        map.getBoard().get(new Coordinate(-1,1)).setSettlementID(3);
        map.getBoard().get(new Coordinate(0,1)).setSettlementID(3);
        map.getBoard().get(new Coordinate(1,1)).setSettlementID(3);
        map.getBoard().get(new Coordinate(2,0)).setSettlementID(3);

        map.getSettlements().put(3, new Settlement());
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(-1,1));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(0,1));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(1,1));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(2,0));

        Assert.assertEquals(map.getSettlements().size(),1);
        map.placer.processParameters(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);
        map.placer.determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
        map.placer.nuke();
        Assert.assertNotEquals(map.getBoard().get(new Coordinate(-1,1)).getSettlementID(),
                map.getBoard().get(new Coordinate(2,0)).getSettlementID());
        Assert.assertEquals(map.getSettlements().size(),2);
    }

    @Test
    public void shouldSplitSettlementNukedByTileInTopLeftRotation() throws Exception{
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,-2), Orientation.FromTopRight);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Jungle),
                new Coordinate(1,-2), Orientation.FromTop);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Jungle),
                new Coordinate(2,-4), Orientation.FromTopLeft);

        map.getBoard().get(new Coordinate(1,-4)).placeVillagers();
        map.getBoard().get(new Coordinate(0,-3)).placeVillagers();
        map.getBoard().get(new Coordinate(0,-2)).placeVillagers();
        map.getBoard().get(new Coordinate(1,-3)).placeVillagers();
        map.getBoard().get(new Coordinate(2,-3)).placeVillagers();

        map.getBoard().get(new Coordinate(1,-4)).setSettlementID(3);
        map.getBoard().get(new Coordinate(0,-3)).setSettlementID(3);
        map.getBoard().get(new Coordinate(0,-2)).setSettlementID(3);
        map.getBoard().get(new Coordinate(1,-3)).setSettlementID(3);
        map.getBoard().get(new Coordinate(2,-3)).setSettlementID(3);

        map.getSettlements().put(3, new Settlement());
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(1,-4));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(0,-3));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(0,-2));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(1,-3));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(2,-3));

        map.placer.processParameters(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,-2), Orientation.FromTopLeft);
        map.placer.determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
        map.placer.nuke();

        Assert.assertNotEquals(map.getBoard().get(new Coordinate(0,-3)).getSettlementID(),
                map.getBoard().get(new Coordinate(2,-3)).getSettlementID());
        Assert.assertEquals(map.getSettlements().size(),2);
    }

    //Todo add more tests to spliting settlements (nuking in all rotations)

    @Test
    public void shouldNotSplitSettlementNukedIfSettlementIsStillConnectedThroughATopPath() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(2,-2), Orientation.FromBottom);

        map.getBoard().get(new Coordinate(0,0)).placeVillagers();
        map.getBoard().get(new Coordinate(1,-1)).placeVillagers();
        map.getBoard().get(new Coordinate(2,-1)).placeVillagers();
        map.getBoard().get(new Coordinate(1,0)).placeVillagers();
        map.getBoard().get(new Coordinate(0,0)).setSettlementID(3);
        map.getBoard().get(new Coordinate(1,-1)).setSettlementID(3);
        map.getBoard().get(new Coordinate(2,-1)).setSettlementID(3);
        map.getBoard().get(new Coordinate(1,0)).setSettlementID(3);

        map.getSettlements().put(3, new Settlement());
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(0,0));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(1,-1));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(2,-1));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(1,0));

        map.placer.processParameters(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottom);
        map.placer.determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
        map.placer.nuke();
        Assert.assertEquals(map.getSettlements().size(),1);
        Assert.assertEquals(map.getSettlements().get(3).settlementCoordinates.size(), 3);
    }

    @Test
    public void shouldNotSplitSettlementNukedIfSettlementIsStillConnectedThroughABottomPath() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,1), Orientation.FromTopLeft);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(2,0), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(-1,3), Orientation.FromTop);

        map.getBoard().get(new Coordinate(-1,1)).placeVillagers();
        map.getBoard().get(new Coordinate(-1,2)).placeVillagers();
        map.getBoard().get(new Coordinate(0,2)).placeVillagers();
        map.getBoard().get(new Coordinate(1,1)).placeVillagers();
        map.getBoard().get(new Coordinate(1,0)).placeVillagers();
        map.getBoard().get(new Coordinate(-1,1)).setSettlementID(3);
        map.getBoard().get(new Coordinate(-1,2)).setSettlementID(3);
        map.getBoard().get(new Coordinate(0,2)).setSettlementID(3);
        map.getBoard().get(new Coordinate(1,1)).setSettlementID(3);
        map.getBoard().get(new Coordinate(1,0)).setSettlementID(3);

        map.getSettlements().put(3, new Settlement());
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(-1,1));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(-1,2));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(0,2));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(1,1));
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(1,0));

        map.placer.processParameters(new Tile(TerrainType.Rocky, TerrainType.Rocky),
                new Coordinate(0,1), Orientation.FromTop);
        map.placer.determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
        map.placer.nuke();
        for(Coordinate c : map.getSettlements().get(3).settlementCoordinates){
            System.out.println(c.getXCoordinate() + "," + c.getYCoordinate());
        }
        Assert.assertEquals(map.getSettlements().size(),2);
        Assert.assertEquals(map.getSettlements().get(3).settlementCoordinates.size(), 3);
    }

    //todo maybe tests rotations independently (see how they affect the isToTheRight things

    @After
    public void deallocateHexesInMap() throws Exception{
        map.getBoard().clear();
        map.getSettlements().clear();
        map.getPlayer().resetScoreAndInventory();
    }
}