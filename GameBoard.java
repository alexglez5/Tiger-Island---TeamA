import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {
    private static Map<Hex, Coordinate> gameBoard = new HashMap<>();

    public void placeTile(Tile tile){
        //Assuming terrains are below volcano
        gameBoard.put(tile.getLeftOfMainTerrain(),
                new Coordinate(-1,1));
        gameBoard.put(tile.getMainTerrain(),
                new Coordinate(0,0));
        gameBoard.put(tile.getRightOfMainTerrain(),
                new Coordinate(0,1));
    }

    private Coordinate determineCoordinate(){
        //TODO based on position of two other terrains compared to volcano
        return new Coordinate(0,0);
    }
}
