package Tigerisland.PlayerActionsTest;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class BuilderTest {
    Game map;

    @Before
    public void initializeGameBoard() throws Exception{
        map = new Game();
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
                new Coordinate(0,0), Orientation.FromBottom);
    }

    @Test
    public void testFoundNewSettlementUpdatesHasVillagerStatusInMap() throws Exception {
        map.foundNewSettlement(new Coordinate(-1,1));
        Assert.assertTrue(map.getBoard().get(new Coordinate(-1,1)).hasVillager());
    }

    @Test
    public void testVillagerIsNotPlacedOnTopOfVolcano() throws Exception{
        map.foundNewSettlement(new Coordinate(0,0));
        Assert.assertFalse(map.getBoard().get(new Coordinate(0,0)).hasVillager());
    }

    @Test
    public void testVillagerIsPlacedOnLevelOneWhenFindingNewSettlement() throws Exception{
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,0), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(1,0));
        Assert.assertFalse(map.getBoard().get(new Coordinate(1,0)).hasVillager());
    }

    @Test
    public void testSettlementFullyExpands() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        Assert.assertTrue(map.getBoard().get(new Coordinate(1,1)).hasVillager());
        Assert.assertEquals(map.getBoard().get(new Coordinate(1,1)).getSettlementID(),
                new Coordinate(1,1).hashCode());
        Assert.assertTrue(map.getBoard().get(new Coordinate(0,1)).hasVillager());
        Assert.assertTrue(map.getBoard().get(new Coordinate(1,1)).hasVillager());
        Assert.assertTrue(map.getBoard().get(new Coordinate(0 ,2)).hasVillager());
        Assert.assertTrue(map.getBoard().get(new Coordinate(1 ,2)).hasVillager());
    }

    @Test
    public void testSettlementDoesNotExpandLongerThanExpected() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);

        Assert.assertFalse(map.getBoard().get(new Coordinate(-1,3)).hasVillager());
        Assert.assertFalse(map.getBoard().get(new Coordinate(1,0)).hasVillager());
        Assert.assertFalse(map.getBoard().get(new Coordinate(0,0)).hasVillager());
        Assert.assertFalse(map.getBoard().get(new Coordinate(-1,1)).hasVillager());
    }

    @Test
    public void shouldMergeSettlementWhenFoundingNewSettlement(){
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(-1,1));
        map.foundNewSettlement(new Coordinate(1,1));
        map.foundNewSettlement(new Coordinate(0,1));
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1, 1)).getSettlementID()
                            ,map.getBoard().get(new Coordinate(0, 1)).getSettlementID());
        Assert.assertEquals(map.getBoard().get(new Coordinate(-1, 1)).getSettlementID()
                            , map.getBoard().get(new Coordinate(1, 1)).getSettlementID());
        Assert.assertEquals(map.getSettlements().size(), 1);
    }

    @Test
    public void cannotFoundSettlementInHexThatHasPieces() throws Exception {
        map.foundNewSettlement(new Coordinate(0,1));
        Assert.assertFalse(map.settlementCanBeFound(new Coordinate(0,1)));
    }

    @Test
    public void testTotoroIsProperlyPlaced() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        map.placeTotoro(new Coordinate(-1,3));

        Assert.assertTrue(map.getBoard().get(new Coordinate(-1 ,3)).hasTotoro());
    }

    @Test
    public void shouldNotPlaceTotoroIfNoAdjacentSettlementHasAtLeastSizeFive() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Lake),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        map.getBoard().get(new Coordinate(0,3)).placeVillagers();
        map.getBoard().get(new Coordinate(0,3)).
                setSettlementID(map.getBoard().get(new Coordinate(1,1)).getSettlementID());
        map.placeTotoro(new Coordinate(-1,3));
        Assert.assertFalse(map.getBoard().get(new Coordinate(-1 ,3)).hasTotoro());
    }

    @Test
    public void testTotoroIsNotPlacedOnTopOfAVolcano() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Lake),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        map.getBoard().get(new Coordinate(0,3)).placeVillagers();
        map.getBoard().get(new Coordinate(0,3)).
                setSettlementID(map.getBoard().get(new Coordinate(1,1)).getSettlementID());
        map.placeTotoro(new Coordinate(-1,2));

        Assert.assertFalse(map.getBoard().get(new Coordinate(-1 ,2)).hasTotoro());
    }

    @Test
    public void testTotoroIsNotPlaceIfThereIsAnotherTotoroInSettlement() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        map.getBoard().get(new Coordinate(0,3)).placeVillagers();
        map.getBoard().get(new Coordinate(0,3)).
                setSettlementID(map.getBoard().get(new Coordinate(1,1)).getSettlementID());
        map.placeTotoro(new Coordinate(-1,3));
        Assert.assertTrue(map.getBoard().get(new Coordinate(-1 ,3)).hasTotoro());

        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-2,2), Orientation.FromBottom);
        map.placeTotoro(new Coordinate(-2,3));
        Assert.assertFalse(map.getBoard().get(new Coordinate(-2 ,3)).hasTotoro());
    }

    @Test
    public void testSettlementsAreMergedWhenTotoroIsPlacedBetweenThem() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,3), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.Jungle, TerrainType.Rocky),
                new Coordinate(-2,2), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(1,1));

        Assert.assertEquals(map.getSettlements().size(),1);
        map.foundNewSettlement(new Coordinate(-2,3));
        int oldId = map.getBoard().get(new Coordinate(-2 ,3)).getSettlementID();
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        map.placeTotoro(new Coordinate(-1,3));

        Assert.assertTrue(map.getBoard().get(new Coordinate(-1 ,3)).hasTotoro());
        Assert.assertEquals(map.getBoard().get(new Coordinate( -2,3)).getSettlementID(),
                map.getBoard().get(new Coordinate(1 ,1)).getSettlementID());
        int newId = map.getBoard().get(new Coordinate(1 ,1)).getSettlementID();
        Assert.assertNotEquals(oldId, newId);
        Assert.assertEquals(map.getSettlements().get(newId).settlementCoordinates.size(), 7);
        Assert.assertEquals(map.getSettlements().size(), 1);
    }

    @Test
    public void cannotPlaceTotoroInHexThatHasPieces() throws Exception {
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        map.foundNewSettlement(new Coordinate(-1,3));

        Assert.assertFalse(map.totoroCanBePlaced(new Coordinate(-1,3)));
    }

    @Test
    public void testTigerIsProperlyPlacedIfTheLevelIsAtLeastThree() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.getBoard().get(new Coordinate(0,1)).increaseLevel();
        map.getBoard().get(new Coordinate(0,1)).increaseLevel();
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(), 3);
        map.placeTiger(new Coordinate(0,1));
        Assert.assertTrue(map.getBoard().get(new Coordinate(0 ,1)).hasTiger());
    }

    @Test
    public void testTigerIsNotPlacedIfLevelIsSmallerThanThree() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.getBoard().get(new Coordinate(0,1)).increaseLevel();
         Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(), 2);
        map.placeTiger(new Coordinate(0,1));
        Assert.assertFalse(map.getBoard().get(new Coordinate(0 ,1)).hasTiger());
    }

    @Test
    public void testTigerIsNotPlacedOnTopOfAVolcano() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.getBoard().get(new Coordinate(0,0)).increaseLevel();
        map.getBoard().get(new Coordinate(0,0)).increaseLevel();
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,0)).getLevel(), 3);
        map.placeTiger(new Coordinate(0,0));
        Assert.assertFalse(map.getBoard().get(new Coordinate(0 ,0)).hasTiger());
    }

    @Test
    public void shouldNotPlaceTigerIfTheOnlyAdjacentSettlementContainsATiger() throws Exception{
        map.getBoard().get(new Coordinate(-1,1)).setLevel(3);
        map.getBoard().get(new Coordinate(0,1)).setLevel(3);
        map.getSettlements().put(3, new Settlement());
        map.getBoard().get(new Coordinate(-1,1)).setSettlementID(3);
        map.getSettlements().get(3).addCoordinateToSettlement(new Coordinate(-1,1));
        map.getSettlements().get(3).placeTiger();
        map.placeTiger(new Coordinate(0,1));
        Assert.assertFalse(map.getBoard().get(new Coordinate(0,1)).hasTiger());
    }

    @Test
    public void testSettlementsAreMergedWhenTigerIsPlacedBetweenThem() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,1), Orientation.FromTop);
        map.foundNewSettlement(new Coordinate(1,0));
        Assert.assertNotEquals(map.getBoard().get(new Coordinate(-1,1)).getSettlementID(),
                map.getBoard().get(new Coordinate(1,0)).getSettlementID());

        map.getBoard().get(new Coordinate(0,1)).increaseLevel();
        map.getBoard().get(new Coordinate(0,1)).increaseLevel();
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(), 3);
        map.placeTiger(new Coordinate(0,1));
        Assert.assertTrue(map.getBoard().get(new Coordinate(0 ,1)).hasTiger());

        Assert.assertEquals(map.getBoard().get(new Coordinate(-1,1)).getSettlementID(),
                map.getBoard().get(new Coordinate(1,0)).getSettlementID());
    }

    @Test
    public void cannotPlaceTigerInHexThatHasPieces() throws Exception {
        map.foundNewSettlement(new Coordinate(-1,1));
        map.getBoard().get(new Coordinate(0,1)).increaseLevel();
        map.getBoard().get(new Coordinate(0,1)).increaseLevel();
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,1)).getLevel(), 3);
        map.getBoard().get(new Coordinate(0,1)).placeVillagers();
        map.placeTiger(new Coordinate(0,1));
        Assert.assertFalse(map.getBoard().get(new Coordinate(0 ,1)).hasTiger());
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.getBoard().clear();
        map.getPlayer().resetScoreAndInventory();
        map.getSettlements().clear();
    }
}

