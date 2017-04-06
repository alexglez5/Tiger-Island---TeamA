package Tigerisland;

import Tigerisland.PlayerActions.ActionHelper;

import java.util.ArrayList;
import java.util.HashSet;

public class AIHelper {
    public Game map;
    private ArrayList<Coordinate> placesWhereTotoroCanBePlaced;
    private ArrayList<Coordinate> placesWhereTigerCanBePlaced;
    private HashSet<Coordinate> visitedCoordinates;

    public AIHelper(Game map){
        this.map = map;
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
        for (Settlement settlement : map.getPlayer().getSettlements())
            if (settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(settlement.getSettlementID()))
                for (Coordinate coordinate : settlement.bfs())
                    findNeighborsOfCoordinateWhereTotoroCanBePlaced(coordinate);
    }

    private boolean settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(int id) {
        return map.getPlayer().findSettlement(id).getSize() >= 5
                && settlementDoesNotContainTotoro(id);
    }

    private void findNeighborsOfCoordinateWhereTotoroCanBePlaced(Coordinate coordinate) {
        map.locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : map.locator.surroundingCoordinates) {
            if (totoroCanBePlacedInCoordinate(neighborCoordinate))
                placesWhereTotoroCanBePlaced.add(neighborCoordinate);
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean settlementDoesNotContainTotoro(int id) {
        return !map.getPlayer().findSettlement(id).hasTotoro();
    }

    private boolean totoroCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && map.totoroCanBePlaced(neighborCoordinate);
    }

    public void findCoordinatesWhereTigerCanBePlaced() {
        placesWhereTigerCanBePlaced = new ArrayList<>();
        visitedCoordinates = new HashSet<>();
        for (Settlement settlement : map.getPlayer().getSettlements())
            if (settlementDoesNotContainTiger(settlement.getSettlementID()))
                for (Coordinate coordinate : settlement.bfs())
                    findNeighborsOfCoordinateWhereTigerCanBePlaced(coordinate);

    }

    private boolean settlementDoesNotContainTiger(int id) {
        return !map.getPlayer().findSettlement(id).hasTiger();
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