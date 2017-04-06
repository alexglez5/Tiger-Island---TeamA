package Tigerisland;

import Tigerisland.PlayerActions.ActionHelper;

import java.util.HashSet;

public class AIHelper extends Game {
    private HashSet<Coordinate> placesWhereTotoroCanBePlaced;
    private HashSet<Coordinate> placesWhereTigerCanBePlaced;
    private HashSet<Coordinate> visitedCoordinates;
    ActionHelper locator = new ActionHelper();

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
        for (Settlement s : getPlayer().getSettlements())
            if (settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(s.getSettlementID()))
                for (Coordinate coordinate : s.bfs())
                    findNeighborsOfCoordinateWhereTotoroCanBePlaced(coordinate);
    }

    private boolean settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(int id) {
        return getPlayer().findSettlement(id).getSize() >= 5
                && settlementDoesNotContainTotoro(id);
    }

    private void findNeighborsOfCoordinateWhereTotoroCanBePlaced(Coordinate coordinate) {
        locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : locator.surroundingCoordinates) {
            if (totoroCanBePlacedInCoordinate(neighborCoordinate))
                placesWhereTotoroCanBePlaced.add(neighborCoordinate);
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean settlementDoesNotContainTotoro(int id) {
        return !getPlayer().findSettlement(id).hasTotoro();
    }

    private boolean totoroCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && totoroCanBePlaced(neighborCoordinate);
    }

    public void findCoordinatesWhereTigerCanBePlaced() {
        placesWhereTigerCanBePlaced = new HashSet<>();
        visitedCoordinates = new HashSet<>();
        for (Settlement s : getPlayer().getSettlements())
            if (settlementDoesNotContainTiger(s.getSettlementID()))
                for (Coordinate coordinate : s.bfs())
                    findNeighborsOfCoordinateWhereTigerCanBePlaced(coordinate);

    }

    private boolean settlementDoesNotContainTiger(int id) {
        return !getPlayer().findSettlement(id).hasTiger();
    }

    private void findNeighborsOfCoordinateWhereTigerCanBePlaced(Coordinate coordinate) {
        locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : locator.surroundingCoordinates) {
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