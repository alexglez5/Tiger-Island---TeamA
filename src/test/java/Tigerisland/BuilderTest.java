package Tigerisland;

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
    }

    @Test
    public void testFoundNewSettlementUpdatesHasVillagerStatusInMap() throws Exception {
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(-1,1));
        Assert.assertTrue(map.gameBoard.get(new Coordinate(-1,1)).hasVillager());
    }

    @Test
    public void testVillagerIsNotPlacedOnTopOfVolcano() throws Exception{
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0,0));
        Assert.assertFalse(map.gameBoard.get(new Coordinate(0,0)).hasVillager());
    }

    @After
    public void deallocateHexesInMap() throws Exception{
        map.gameBoard.clear();
    }
}