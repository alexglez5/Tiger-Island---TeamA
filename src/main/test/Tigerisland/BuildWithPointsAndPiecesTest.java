package Tigerisland;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alexander Gonzalez on 3/31/2017.
 */
public class BuildWithPointsAndPiecesTest {
    GameBoard map;
    @Before
    public void setUpTests(){
        map = new GameBoard();
    }

    @Test
    public void foundSettlementAndCheckPlayerScoreAndVillagerUpdated(){
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky), new Coordinate(0,0), Orientation.FromBottom);
        map.foundNewSettlement(new Coordinate(0,1));
        Assert.assertEquals(map.player.getNumberOfVillagersLeft(), 19);
        Assert.assertEquals(map.player.getPoints(), 1);
    }
}
