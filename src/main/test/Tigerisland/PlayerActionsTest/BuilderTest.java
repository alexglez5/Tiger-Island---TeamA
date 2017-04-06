package Tigerisland.PlayerActionsTest;

import Tigerisland.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertFalse(map.settlementCanBeFound(new Coordinate(0,0)));
    }

    @Test
    public void testVillagerIsPlacedOnLevelOneWhenFindingNewSettlement() throws Exception{
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(0,0), Orientation.FromBottomRight);
        Assert.assertFalse(map.settlementCanBeFound(new Coordinate(1,0)));
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
        Assert.assertEquals(map.getPlayer().getSettlements().size(), 1);
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
        Assert.assertFalse(map.totoroCanBePlaced(new Coordinate(-1,3)));
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
        Assert.assertFalse(map.totoroCanBePlaced(new Coordinate(-1,2)));
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
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-2,2), Orientation.FromBottom);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        map.placeTotoro(new Coordinate(-1,3));
        Assert.assertTrue(map.getBoard().get(new Coordinate(-1 ,3)).hasTotoro());
        Assert.assertFalse(map.totoroCanBePlaced(new Coordinate(-2,3)));
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
        map.foundNewSettlement(new Coordinate(-2,3));

        int oldId = map.getBoard().get(new Coordinate(-2 ,3)).getSettlementID();

        map.expandSettlement(new Coordinate(1,1), TerrainType.Rocky);
        map.placeTotoro(new Coordinate(-1,3));

        Assert.assertTrue(map.getBoard().get(new Coordinate(-1 ,3)).hasTotoro());
        Assert.assertEquals(map.getBoard().get(new Coordinate( -2,3)).getSettlementID(),
                map.getBoard().get(new Coordinate(1 ,1)).getSettlementID());
        int newId = map.getBoard().get(new Coordinate(1 ,1)).getSettlementID();
        Assert.assertNotEquals(oldId, newId);
        Assert.assertEquals(map.getPlayer().getSettlements().size(), 1);
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
        Assert.assertFalse(map.tigerCanBePlaced(new Coordinate(0,1)));
    }

    @Test
    public void testTigerIsNotPlacedOnTopOfAVolcano() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.getBoard().get(new Coordinate(0,0)).setLevel(3);
        Assert.assertEquals(map.getBoard().get(new Coordinate(0,0)).getLevel(), 3);
        Assert.assertFalse(map.tigerCanBePlaced(new Coordinate(0,0)));
    }

    @Test
    public void shouldNotPlaceTigerIfTheOnlyAdjacentSettlementContainsATiger() throws Exception{
        Settlement addedSettlement = new Settlement(new Coordinate(-1,1));
        map.getBoard().get(new Coordinate(-1,1)).setLevel(3);
        map.getBoard().get(new Coordinate(0,1)).setLevel(3);
        map.getPlayer().addSettlement(addedSettlement);
        map.getBoard().get(new Coordinate(-1,1)).setSettlementID(addedSettlement.getSettlementID());
        map.getPlayer().findSettlement(addedSettlement.getSettlementID()).placeTiger();
        Assert.assertFalse(map.tigerCanBePlaced(new Coordinate(0,1)));
    }

    @Test
    public void testSettlementsAreMergedWhenTigerIsPlacedBetweenThem() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,1), Orientation.FromTop);
        map.foundNewSettlement(new Coordinate(1,0));
        Assert.assertNotEquals(map.getBoard().get(new Coordinate(-1,1)).getSettlementID(),
                map.getBoard().get(new Coordinate(1,0)).getSettlementID());

        map.getBoard().get(new Coordinate(0,1)).setLevel(3);
        map.placeTiger(new Coordinate(0,1));
        Assert.assertTrue(map.getBoard().get(new Coordinate(0 ,1)).hasTiger());

        Assert.assertEquals(map.getBoard().get(new Coordinate(-1,1)).getSettlementID(),
                map.getBoard().get(new Coordinate(1,0)).getSettlementID());
    }

    @Test
    public void cannotPlaceTigerInHexThatHasPieces() throws Exception {
        map.foundNewSettlement(new Coordinate(-1,1));
        map.getBoard().get(new Coordinate(0,1)).setLevel(3);
        map.getBoard().get(new Coordinate(0,1)).placeVillagers();
        Assert.assertFalse(map.tigerCanBePlaced(new Coordinate(0,1)));
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.getBoard().clear();
        map.getPlayer().resetScoreAndInventory();
        map.getPlayer().getSettlements().clear();
    }
}

