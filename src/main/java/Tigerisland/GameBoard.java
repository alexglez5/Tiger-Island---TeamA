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

    public void nuke(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        placer = new TilePlacer();
        placer.nuke(tile, mainTerrainCoordinate, terrainsOrientation);
    }

    //TODO add checks so that tile is is not placed perfectly on top of another
    //TODO checks so that tile is only placed on full ground
}
