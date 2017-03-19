package Tigerisland;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {
    private static Map<Coordinate, Hex> gameBoard = new HashMap<>();

    public void placeTile(Tile tile, Orientation orientation){
        Coordinate [] coordinates = determineOrientation(orientation);
        //Assuming terrains are below volcano
        gameBoard.put(coordinates[0],
                tile.getLeftOfMainTerrain());
        gameBoard.put(coordinates[1],
                tile.getMainTerrain());
        gameBoard.put(coordinates[2],
                tile.getRightOfMainTerrain());
        //Coordinate trial = gameBoard.get(tile.getMainTerrain())
    }


    private Coordinate[] determineOrientation(Orientation orientation){
        //TODO based on position of two other terrains compared to volcano
        Coordinate [] coordinates = {
                new Coordinate(-1, 1),
                new Coordinate(0, 0),
                new Coordinate(0, 1)
        };
        return coordinates;
    }

    public boolean checkForHex(Coordinate cord) {
        return (gameBoard.get(cord) != null);
    }

    //TODO counter to get id of tile
}
