package Tigerisland;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class BuilderTest {
    GameBoard map;


    //TODO merge settlements after expansion?
    //todo adjacent settlement can't contain totoro

    @Before
    public void initializeGameBoard() throws Exception{
        map = new GameBoard();
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky),
=======
//        player = new Player();
        map.placeTile(new Tile(TerrainType.LAKE, TerrainType.ROCK),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
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
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
=======
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(0,0), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(1,0));
        Assert.assertFalse(map.gameBoard.get(new Coordinate(1,0)).hasVillager());
    }

    @Test
    public void testSettlementFullyExpands() throws Exception{
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
=======
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(2,1), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.ROCK);
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1,1)).hasVillager());
        Assert.assertEquals(map.gameBoard.get(new Coordinate(1,1)).getSettlementID(),
                new Coordinate(1,1).hashCode());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0,1)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1,1)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0 ,2)).hasVillager());
        Assert.assertTrue(map.gameBoard.get(new Coordinate(1 ,2)).hasVillager());
    }

    @Test
    public void testSettlementDoesNotExpandLongerThanExpected() throws Exception{
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
=======
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.ROCK);

        Assert.assertFalse(map.gameBoard.get(new Coordinate(-1,3)).hasVillager());
        Assert.assertFalse(map.gameBoard.get(new Coordinate(1,0)).hasVillager());
        Assert.assertFalse(map.gameBoard.get(new Coordinate(0,0)).hasVillager());
        Assert.assertFalse(map.gameBoard.get(new Coordinate(-1,1)).hasVillager());
    }

    @Test
    public void testTotoroIsProperlyPlaced() throws Exception{
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
=======
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.ROCK);
        map.gameBoard.get(new Coordinate(0,3)).placeVillagers();
        map.gameBoard.get(new Coordinate(0,3)).
                setSettlementID(map.gameBoard.get(new Coordinate(1,1)).getSettlementID());
        map.placeTotoro(new Coordinate(-1,3));

        Assert.assertTrue(map.gameBoard.get(new Coordinate(-1 ,3)).hasTotoro());
    }

    @Test
<<<<<<< HEAD
    public void shouldNotPlaceTotoroIfNoAdjacentSettlementHasAtLeastSizeFive() throws Exception{
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Lake),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
=======
    public void testTotoroIsNotPlacedIfAdjacentSettlementIsLessThanSizeFive() throws Exception{
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.LAKE),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.ROCK);
        map.gameBoard.get(new Coordinate(0,3)).placeVillagers();
        map.gameBoard.get(new Coordinate(0,3)).
                setSettlementID(map.gameBoard.get(new Coordinate(1,1)).getSettlementID());
        map.placeTotoro(new Coordinate(-1,3));
        Assert.assertFalse(map.gameBoard.get(new Coordinate(-1 ,3)).hasTotoro());
    }

    @Test
    public void testTotoroIsNotPlacedOnTopOfAVolcano() throws Exception{
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Lake),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
=======
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.LAKE),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.ROCK);
        map.gameBoard.get(new Coordinate(0,3)).placeVillagers();
        map.gameBoard.get(new Coordinate(0,3)).
                setSettlementID(map.gameBoard.get(new Coordinate(1,1)).getSettlementID());
        map.placeTotoro(new Coordinate(-1,2));

        Assert.assertFalse(map.gameBoard.get(new Coordinate(-1 ,2)).hasTotoro());
    }

    @Test
    public void testTotoroIsNotPlaceIfThereIsAnotherTotoroInSettlement() throws Exception{
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Jungle),
=======
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.JUNGLE),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(1,3), Orientation.FromBottomLeft);

        map.foundNewSettlement(new Coordinate(1,1));
        map.expandSettlement(new Coordinate(1,1), TerrainType.ROCK);
        map.gameBoard.get(new Coordinate(0,3)).placeVillagers();
        map.gameBoard.get(new Coordinate(0,3)).
                setSettlementID(map.gameBoard.get(new Coordinate(1,1)).getSettlementID());
        map.placeTotoro(new Coordinate(-1,3));
        Assert.assertTrue(map.gameBoard.get(new Coordinate(-1 ,3)).hasTotoro());

<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
=======
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(-2,2), Orientation.FromBottom);
        map.placeTotoro(new Coordinate(-2,3));
        Assert.assertFalse(map.gameBoard.get(new Coordinate(-2 ,3)).hasTotoro());
    }

    @Test
    public void testSettlementsAreMergedWhenTotoroIsPlacedBetweenThem() throws Exception{
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Grasslands, TerrainType.Rocky),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Lake),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
                new Coordinate(1,3), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.Jungle, TerrainType.Rocky),
=======
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,0), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.GRASS, TerrainType.ROCK),
                new Coordinate(-1,2), Orientation.FromBottomRight);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.LAKE),
                new Coordinate(2,1), Orientation.FromBottom);
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
                new Coordinate(1,3), Orientation.FromBottomLeft);
        map.placeTile(new Tile(TerrainType.JUNGLE, TerrainType.ROCK),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(-2,2), Orientation.FromBottom);

        map.foundNewSettlement(new Coordinate(1,1));
        map.foundNewSettlement(new Coordinate(-2,3));
        map.expandSettlement(new Coordinate(1,1), TerrainType.ROCK);
        map.gameBoard.get(new Coordinate(0,3)).placeVillagers();
        map.gameBoard.get(new Coordinate(0,3)).
                setSettlementID(map.gameBoard.get(new Coordinate(1,1)).getSettlementID());
        map.placeTotoro(new Coordinate(-1,3));

        Assert.assertTrue(map.gameBoard.get(new Coordinate(-1 ,3)).hasTotoro());
        Assert.assertEquals(map.gameBoard.get(new Coordinate( -2,3)).getSettlementID(),
                map.gameBoard.get(new Coordinate(1 ,1)).getSettlementID());
    }

    @Test
    public void testTigerIsProperlyPlacedIfTheLevelIsAtLeastThree() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.gameBoard.get(new Coordinate(0,1)).increaseLevel();
        map.gameBoard.get(new Coordinate(0,1)).increaseLevel();
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(), 3);
        map.placeTiger(new Coordinate(0,1));
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0 ,1)).hasTiger());
    }

    @Test
    public void testTigerIsNotPlacedIfLevelIsSmallerThanThree() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.gameBoard.get(new Coordinate(0,1)).increaseLevel();
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(), 2);
        map.placeTiger(new Coordinate(0,1));
        Assert.assertFalse(map.gameBoard.get(new Coordinate(0 ,1)).hasTiger());
    }

    @Test
    public void testTigerIsNotPlacedOnTopOfAVolcano() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
        map.gameBoard.get(new Coordinate(0,0)).increaseLevel();
        map.gameBoard.get(new Coordinate(0,0)).increaseLevel();
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,0)).getLevel(), 3);
        map.placeTiger(new Coordinate(0,0));
        Assert.assertFalse(map.gameBoard.get(new Coordinate(0 ,0)).hasTiger());
    }

    @Test
    public void testSettlementsAreMergedWhenTigerIsPlacedBetweenThem() throws Exception{
        map.foundNewSettlement(new Coordinate(-1,1));
<<<<<<< HEAD
        map.placeTile(new Tile(TerrainType.Rocky, TerrainType.Grasslands),
=======
        map.placeTile(new Tile(TerrainType.ROCK, TerrainType.GRASS),
>>>>>>> 76f06a5f8615fd7deca6e6d6f7e66eafe03b8f95
                new Coordinate(1,1), Orientation.FromTop);
        map.foundNewSettlement(new Coordinate(1,0));
        Assert.assertNotEquals(map.gameBoard.get(new Coordinate(-1,1)).getSettlementID(),
                map.gameBoard.get(new Coordinate(1,0)).getSettlementID());

        map.gameBoard.get(new Coordinate(0,1)).increaseLevel();
        map.gameBoard.get(new Coordinate(0,1)).increaseLevel();
        Assert.assertEquals(map.gameBoard.get(new Coordinate(0,1)).getLevel(), 3);
        map.placeTiger(new Coordinate(0,1));
        Assert.assertTrue(map.gameBoard.get(new Coordinate(0 ,1)).hasTiger());

        Assert.assertEquals(map.gameBoard.get(new Coordinate(-1,1)).getSettlementID(),
                map.gameBoard.get(new Coordinate(1,0)).getSettlementID());
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
        map.getPlayer().resetScoreAndInventory();
    }

    @After
    public void resetTileIds() throws Exception {
        Tile.setNumOfTilesCreated(0);
    }
}

