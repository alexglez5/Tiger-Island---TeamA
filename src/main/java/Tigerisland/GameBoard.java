package Tigerisland;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {
    protected static Map<Coordinate, Hex> gameBoard = new HashMap<>();
    private static TilePlacer placer = new TilePlacer();
    private static Builder builder = new Builder();


    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        placer.placeTile(tile, mainTerrainCoordinate, terrainsOrientation);
    }

    public void foundNewSettlement(Coordinate coordinate){
        builder.foundNewSettlement(coordinate);
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType){
        builder.expandSettlement(coordinateOfAnyHexInSettlement, terrainType);
    }

    public void placeTotoro(Coordinate coordinate){
        builder.placeTotoro(coordinate);
    }

    //TODO add acceptance test for placeTile on different levels
}
