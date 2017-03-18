package Tigerisland;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {
    private static Map<Coordinate, Hex> gameBoard = new HashMap<>();

    public void placeTile(Tile tile){
        //Assuming terrains are below volcano
        gameBoard.put(new Coordinate(-1,1),
                tile.getLeftOfMainTerrain());
        gameBoard.put(new Coordinate(0,0),
                tile.getMainTerrain());
        gameBoard.put(new Coordinate(0,1),
                tile.getRightOfMainTerrain());
        //Coordinate trial = gameBoard.get(tile.getMainTerrain())
    }


    private Coordinate determineCoordinate(){
        //TODO based on position of two other terrains compared to volcano
        return new Coordinate(0,0);
    }

    public boolean checkForHex(Coordinate cord) {
        return (gameBoard.get(cord) != null);
    }

    //TODO counter to get id of tile
}
