package Tigerisland;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {
    private static Map<Coordinate, Hex> gameBoard = new HashMap<>();

    //Takes a tile and an array of coordinates
    // *
    public void placeTile(Tile tile, Coordinate [] newTileCoordinates){
        //Assuming terrains are below volcano
        gameBoard.put(newTileCoordinates[0],
                tile.getLeftOfMainTerrain());
        gameBoard.put(newTileCoordinates[1],
                tile.getMainTerrain());
        gameBoard.put(newTileCoordinates[2],
                tile.getRightOfMainTerrain());
        //Coordinate trial = gameBoard.get(tile.getMainTerrain())
    }

    private Coordinate[] determineOrientation(){
        //TODO based on position of two other terrains compared to volcano
        return null;
    }

    public boolean checkForHex(Coordinate cord) {
        return (gameBoard.get(cord) != null);
    }

    //TODO counter to get id of tile
}
