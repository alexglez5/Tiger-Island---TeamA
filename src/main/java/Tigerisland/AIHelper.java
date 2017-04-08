package Tigerisland;
import Tigerisland.PlayerActions.ComponentsDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AIHelper {
    public Game map = new Game();
    private HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    private Player player;
    private HashMap<Integer, Settlement> settlements;
    private ArrayList<Coordinate> placesWhereTotoroCanBePlaced;
    private ArrayList<Coordinate> placesWhereTigerCanBePlaced;
    private HashSet<Coordinate> visitedCoordinates;

    public void updtateComponents(ComponentsDTO dto) {
        this.gameBoard = dto.getGameBoard();
        this.settlements = dto.getSettlements();
        this.player = dto.getPlayer();
    }

    //todo
    /*
    - functions that will return lists of coordinates for all the places
    one can:
        - place a tile in level one
        - nuke a tile at level n, n-1... 1
        - expand a settlement
        - found a settlement
    */

    public void findCoordinatesWhereTotoroCanBePlaced() {
        placesWhereTotoroCanBePlaced = new ArrayList<>();
        visitedCoordinates = new HashSet<>();
        for (int id : settlements.keySet())
            if (settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(id))
                for (Coordinate coordinate : settlements.get(id).bfs())
                    findNeighborsOfCoordinateWhereTotoroCanBePlaced(coordinate);
    }

    private boolean settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(int id) {
        return settlements.get(id).getSize() >= 5
                && !settlements.get(id).hasTotoro();
    }

    private void findNeighborsOfCoordinateWhereTotoroCanBePlaced(Coordinate coordinate) {
        map.locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : map.locator.surroundingCoordinates) {
            if (totoroCanBePlacedInCoordinate(neighborCoordinate))
                placesWhereTotoroCanBePlaced.add(neighborCoordinate);
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean totoroCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && map.totoroCanBePlaced(neighborCoordinate);
    }

    public void findCoordinatesWhereTigerCanBePlaced() {
        placesWhereTigerCanBePlaced = new ArrayList<>();
        visitedCoordinates = new HashSet<>();
        for (int id : settlements.keySet())
            if (settlementDoesNotContainTiger(id))
                for (Coordinate coordinate : settlements.get(id).bfs())
                    findNeighborsOfCoordinateWhereTigerCanBePlaced(coordinate);

    }

    private boolean settlementDoesNotContainTiger(int id) {
        return !settlements.get(id).hasTiger();
    }

    private void findNeighborsOfCoordinateWhereTigerCanBePlaced(Coordinate coordinate) {
        map.locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : map.locator.surroundingCoordinates) {
            if (tigerCanBePlacedInCoordinate(neighborCoordinate))
                placesWhereTigerCanBePlaced.add(neighborCoordinate);
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean tigerCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && map.tigerCanBePlaced(neighborCoordinate);
    }

    public ArrayList<Coordinate> getPlacesWhereTotoroCanBePlaced() {
        findCoordinatesWhereTotoroCanBePlaced();
        return placesWhereTotoroCanBePlaced;
    }

    public ArrayList<Coordinate> getPlacesWhereTigerCanBePlaced() {
        findCoordinatesWhereTigerCanBePlaced();
        return placesWhereTigerCanBePlaced;
    }
}