package Tigerisland;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {
    protected static Map<Coordinate, Hex> gameBoard = new HashMap<>();
    private TilePlacer placer;

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        placer = new TilePlacer();
        placer.placeTile(tile, mainTerrainCoordinate, terrainsOrientation);
    }

    //TODO add acceptance test for placeTile on different levels
    //TODO
}
