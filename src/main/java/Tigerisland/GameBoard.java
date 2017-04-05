package Tigerisland;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */
public class GameBoard {

    protected static HashMap<Coordinate, Hex> gameBoard;

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
        if (!gameBoard.containsKey(cord))
            gameBoard.put(cord, hex);

        // check underlying hex level, check if a piece is covered
        else {
            Hex old = gameBoard.get(cord);
            hex.setLevel(old.getLevel() + 1);
            gameBoard.put(cord, hex);
        }
    }

    public ArrayList<Hex> getHexesFromCoordinateArray(Coordinate[] c) {
        ArrayList<Hex> hexes = new ArrayList<>();
        for (Coordinate cord : c) {
            if (gameBoard.containsKey(cord))
                hexes.add(gameBoard.get(cord));
        }
        return hexes;
    }

    public void clearBoard() {
        gameBoard.clear();
    }
}
