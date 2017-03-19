package Tigerisland;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by NotKali on 3/19/2017.
 */
public class WhenPlayerPlacesATile {
    private GameBoard map = new GameBoard();
    private Tile newTile = new Tile(TerrainType.Grasslands, TerrainType.Jungle);

    @Before
    public void setup () {

    }
    @Test
    public void shouldPlaceTile () {
        newTile.setTileID(1);
        Coordinate [] firstTile = {new Coordinate(0,0), new Coordinate(0,-1), new Coordinate(1,-1)};
        map.placeTile(newTile, firstTile);
        assertTrue (map.checkForHex(firstTile[1]));
    }
}
