package Tigerisland;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {
    protected static Map<Coordinate, Hex> gameBoard = new HashMap<>();

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        TilePlacer placer = new TilePlacer();
        placer.placeTile(tile, mainTerrainCoordinate, terrainsOrientation);
    }

    //TODO delete commented code below
//    public void placeTile(Tile tile, Orientation orientation){
//        Coordinate [] coordinates = determineOrientation(orientation);
//        //Assuming terrains are below volcano
//        gameBoard.put(coordinates[0],
//                tile.getLeftOfMainTerrain());
//        gameBoard.put(coordinates[1],
//                tile.getMainTerrain());
//        gameBoard.put(coordinates[2],
//                tile.getRightOfMainTerrain());
//        //Coordinate trial = gameBoard.get(tile.getMainTerrain())
//    }

//    private Coordinate[] determineOrientation(Orientation orientation){
//        Coordinate [] coordinates = {
//                new Coordinate(-1, 1),
//                new Coordinate(0, 0),
//                new Coordinate(0, 1)
//        };
//        return coordinates;
//    }
//
    public boolean checkForHex(Coordinate cord) {
        System.out.println(gameBoard.get(cord) != null);
        return (gameBoard.get(cord) != null);
    }

    //TODO counter to get id of tile
}
