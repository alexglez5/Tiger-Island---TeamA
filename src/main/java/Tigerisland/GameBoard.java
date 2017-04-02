package Tigerisland;

import java.util.HashMap;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {


    private HashMap<Coordinate, Hex> gameBoard;

    public GameBoard() {
        gameBoard = new HashMap<>();
    }

    public HashMap<Coordinate, Hex> getGameBoard() {
        return gameBoard;
    }

    public boolean checkForHex(Coordinate cord) {
        boolean existence = false;
        if (gameBoard.get(cord) != null)
            existence = true;
        return existence;
    }

    public Hex getHex(Coordinate cord) {
        Hex hex = gameBoard.get(cord);
        return hex;
    }

    public void placeHex(Hex hex, Coordinate cord) {
        gameBoard.put(cord, hex);
    }

    public void clearBoard() {
        gameBoard.clear();
    }
}
