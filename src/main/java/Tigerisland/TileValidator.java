package Tigerisland;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nathanbarnavon on 4/2/17.
 */
public class TileValidator extends GameBoard {
    // 0 is A, 1 is B

    CoordinatesLocator locator = new CoordinatesLocator();

    public boolean canPlaceTile(Coordinate c, int orientation) {
        if (checkHexesAtSameLevel(c, orientation)) {
            // check placement criteria for nuking
            if (gameBoard.containsKey(c)) {
                // check main coordinate is on top of volcano
                if (gameBoard.get(c).getTerrainType() != TerrainType.VOLCANO)
                    return false;

                // check tile will cover multiple tiles
                if (!expandsMultipleTiles(c, orientation)) {
                    return false;
                }

                // todo: check tile doesn't cover entire settlement
                return true;

                // todo: check tile doesn't cover totoro or tiger
            }
            // check placement criteria for level one
            else {
                return checkConnectingToGameBoard(c, orientation);
            }
        }

        return false;
    }

    public boolean checkHexesAtSameLevel(Coordinate c, int orientation) {
        Coordinate[] nearby = locator.produceCoordinatesFromOrientation(c, orientation);

        // if all coordinates have an existing hex, make sure they are at the same level
        if (gameBoard.containsKey(c) && gameBoard.containsKey(nearby[0]) && gameBoard.containsKey(nearby[1])) {
            if (gameBoard.get(c).getLevel() == gameBoard.get(nearby[0]).getLevel() &&
                    gameBoard.get(nearby[0]).getLevel() == gameBoard.get(nearby[1]).getLevel())
                return true;
            else
                return false;
        }

        // they either all have existing hexes, or all have non-existing hexes
        else if (!gameBoard.containsKey(c) && !gameBoard.containsKey(nearby[0]) && !gameBoard.containsKey(nearby[1]))
            return true;

        return false;
    }

    // look at adjacent coordinates, and see if a hex exists
    public boolean checkConnectingToGameBoard(Coordinate c, int orientation) {
        Coordinate[] connected = locator.produceCoordinatesFromOrientation(c, orientation);
        Coordinate[] adjacent = locator.produceClockwiseNeighborCoordinates(c);
        // check the volcano hex
        ArrayList<Hex> hexes = getHexesFromCoordinateArray(adjacent);
        if (!hexes.isEmpty())
            return true;
        adjacent = locator.produceClockwiseNeighborCoordinates(connected[0]);
        hexes = getHexesFromCoordinateArray(adjacent);
        if (!hexes.isEmpty())
            return true;
        adjacent = locator.produceClockwiseNeighborCoordinates(connected[0]);
        hexes = getHexesFromCoordinateArray(adjacent);
        if (!hexes.isEmpty())
            return true;
        return false;
    }

    public boolean expandsMultipleTiles(Coordinate c, int orientation) {
        Coordinate[] connected = locator.produceCoordinatesFromOrientation(c, orientation);
        if (gameBoard.get(c).getTileID() == gameBoard.get(connected[0]).getTileID() &&
                gameBoard.get(connected[0]).getTileID() == gameBoard.get(connected[1]).getTileID()){
            return false;
        }
        else
            return true;
    }

    //todo: check for underlying settlement clearing


}
