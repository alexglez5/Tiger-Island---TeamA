package Tigerisland;

import Tigerisland.PlayerActions.ActionHelper;

import java.util.HashSet;

/**
 * Created by Alexander Gonzalez on 4/2/2017.
 */
public class AIHelper extends ActionHelper {
    private HashSet<Coordinate> placesWhereTotoroCanBePlaced;
    private HashSet<Coordinate> placesWhereTigerCanBePlaced;
    private HashSet<Coordinate> visitedCoordinates;


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
        placesWhereTotoroCanBePlaced = new HashSet<>();
        visitedCoordinates = new HashSet<>();
        for (int id : settlements.keySet())
            if (settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(id))
                for (Coordinate coordinate : settlements.get(id).settlementCoordinates)
                    findNeighborsOfCoordinateWhereTotoroCanBePlaced(coordinate);

    }

    private boolean settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(int id) {
        return settlements.get(id).settlementCoordinates.size() >= 5
                && settlementDoesNotContainTotoro(id);
    }

    private void findNeighborsOfCoordinateWhereTotoroCanBePlaced(Coordinate coordinate) {
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : surroundingCoordinates) {
            if (totoroCanBePlacedInCoordinate(neighborCoordinate))
                placesWhereTotoroCanBePlaced.add(neighborCoordinate);
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean settlementDoesNotContainTotoro(int id) {
        return !settlements.get(id).hasTotoro();
    }

    private boolean totoroCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && totoroCanBePlaced(neighborCoordinate);
    }

    public void findCoordinatesWhereTigerCanBePlaced() {
        placesWhereTigerCanBePlaced = new HashSet<>();
        visitedCoordinates = new HashSet<>();
        for (int id : settlements.keySet())
            if (settlementDoesNotContainTiger(id))
                for (Coordinate coordinate : settlements.get(id).settlementCoordinates)
                    findNeighborsOfCoordinateWhereTigerCanBePlaced(coordinate);

    }

    private boolean settlementDoesNotContainTiger(int id) {
        return !settlements.get(id).hasTiger();
    }

    private void findNeighborsOfCoordinateWhereTigerCanBePlaced(Coordinate coordinate) {
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : surroundingCoordinates) {
            if (tigerCanBePlacedInCoordinate(neighborCoordinate))
                placesWhereTigerCanBePlaced.add(neighborCoordinate);
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean tigerCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && tigerCanBePlaced(neighborCoordinate);
    }

    public HashSet<Coordinate> getPlacesWhereTotoroCanBePlaced() {
        return placesWhereTotoroCanBePlaced;
    }

    public HashSet<Coordinate> getPlacesWhereTigerCanBePlaced() {
        return placesWhereTigerCanBePlaced;
    }
}