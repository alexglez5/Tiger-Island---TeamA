package Tigerisland.AIHelpers;
import Tigerisland.Coordinate;
import Tigerisland.Game;

import java.util.ArrayList;
import java.util.HashSet;

public class AIHelper {
    private boolean tileMove;
    private boolean totoroMove;
    private boolean tigerMove;
    private boolean expandMove;
    private boolean foundMove;
    private boolean[] moves = new boolean[5];

    public void findPossibleMoves() {
        //call all the functions to produce what they need
    }


    public Game map = new Game();
    private ArrayList<Coordinate> placesWhereTotoroCanBePlaced;
    private ArrayList<Coordinate> placesWhereTigerCanBePlaced;
    private ArrayList<ExpandingParameters> placesWhereSettlementCanBeExpanded;
    private ArrayList<Coordinate> placesWhereSettlementCanBeFound;
    private ArrayList<TileParameters> placesWhereTileCanBePlaced;
//    private Coordinate placeWhereTotoroCanBePlaced;
//    private Coordinate placeWhereTigerCanBePlaced;
//    private ExpandingParameters placesWhereSettlementCanBeExpanded;
//    private ArrayList<Coordinate placesWhereSettlementCanBeFound;
//    private ArrayList<TileParameters> placesWhereTileCanBePlaced;
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

//    public void findCoordinatesWhereSettlementCanBeFound(){
//
//    }

    public void findCoordinatesWhereTotoroCanBePlaced() {
        placesWhereTotoroCanBePlaced = new ArrayList<>();
        visitedCoordinates = new HashSet<>();
        for (int id : map.getSettlements().keySet())
            if (settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(id))
                for (Coordinate coordinate : map.getSettlements().get(id).bfs())
                    findNeighborsOfCoordinateWhereTotoroCanBePlaced(coordinate);
    }

    private boolean settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(int id) {
        return map.getSettlements().get(id).getSize() >= 5
                && !map.getSettlements().get(id).hasTotoro();
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
//        map.buildValidator.processParameters(neighborCoordinate);
        return !visitedCoordinates.contains(neighborCoordinate) && map.totoroCanBePlaced(neighborCoordinate);
    }

    public void findCoordinatesWhereTigerCanBePlaced() {
        placesWhereTigerCanBePlaced = new ArrayList<>();
        visitedCoordinates = new HashSet<>();
        for (int id : map.getSettlements().keySet())
            if (settlementDoesNotContainTiger(id))
                for (Coordinate coordinate : map.getSettlements().get(id).bfs())
                    findNeighborsOfCoordinateWhereTigerCanBePlaced(coordinate);

    }

    private boolean settlementDoesNotContainTiger(int id) {
        return !map.getSettlements().get(id).hasTiger();
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

    public ArrayList<TileParameters> getPlacesWhereTileCanBePlaced() {
        return placesWhereTileCanBePlaced;
    }

    public ArrayList<ExpandingParameters> getPlacesWhereSettlementCanBeExpanded() {
        return placesWhereSettlementCanBeExpanded;
    }

    public ArrayList<Coordinate> getPlacesWhereSettlementCanBeFound() {
        return placesWhereSettlementCanBeFound;
    }

    public Coordinate getPlaceWhereTotoroCanBePlaced() {
        findCoordinatesWhereTotoroCanBePlaced();
        return placesWhereTotoroCanBePlaced.get(0);
    }

    public Coordinate getPlaceWhereTigerCanBePlaced() {
        findCoordinatesWhereTigerCanBePlaced();
        return placesWhereTigerCanBePlaced.get(0);
    }

    public TileParameters getPlaceWhereTileCanBePlaced() {
        return placesWhereTileCanBePlaced.get(0);
    }

    public ExpandingParameters getPlaceWhereSettlementCanBeExpanded() {
        return placesWhereSettlementCanBeExpanded.get(0);
    }

    public Coordinate getPlaceWhereSettlementCanBeFound() {
        return placesWhereSettlementCanBeFound.get(0);
    }

    public boolean[] getMoves() {
        return moves;
    }
}