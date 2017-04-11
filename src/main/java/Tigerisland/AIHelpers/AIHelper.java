package Tigerisland.AIHelpers;

import Tigerisland.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class AIHelper {
    public Game map = new Game();
    private boolean tileMove;
    private boolean totoroMove;
    private boolean tigerMove;
    private boolean expandMove;
    private boolean foundMove;
    private boolean[] moves = new boolean[5];
    private Coordinate placeWhereTotoroCanBePlaced;
    private Coordinate placeWhereTigerCanBePlaced;
    private ExpandingParameters placeWhereSettlementCanBeExpanded;
    private Coordinate placeWhereSettlementCanBeFound;
    private TileParameters placeWhereTileCanBePlaced;
    private HashSet<Coordinate> visitedCoordinates;

    public void findCoordinateWhereTotoroCanBePlaced() {
        placeWhereTotoroCanBePlaced = null;
        visitedCoordinates = new HashSet<>();
        for (int id : map.getSettlements().keySet())
            if (settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(id)
                    && map.getSettlements().get(id).getPlayerID() == 1)
                for (Coordinate coordinate : map.getSettlements().get(id).bfs())
                    findNeighborsOfCoordinateWhereTotoroCanBePlaced(coordinate);
    }

    public void findCoordinateWhereTigerCanBePlaced() {
        placeWhereTigerCanBePlaced = null;
        visitedCoordinates = new HashSet<>();
        for (int id : map.getSettlements().keySet())
            if (settlementDoesNotContainTiger(id)
                    && map.getSettlements().get(id).getPlayerID() == map.getPlayer().getPlayerID())
                for (Coordinate coordinate : map.getSettlements().get(id).bfs())
                    findNeighborsOfCoordinateWhereTigerCanBePlaced(coordinate);

    }

    public void findPlaceWhereSettlementCanBeExpanded() {
        placeWhereSettlementCanBeExpanded = null;
        TreeSet<Integer> scores = new TreeSet<>();
        HashMap<Integer, ExpandingParameters> movesWithScores = new HashMap<>();
        for (int id : map.getSettlements().keySet()) {
            if (map.getSettlements().get(id).getPlayerID() == 1
                    && !map.getSettlements().get(id).hasTotoro()
                    && !map.getSettlements().get(id).hasTiger()) {
                for (TerrainType terrainType : map.getDifferentTerrainTypesInSettlement(id)) {
                    ExpandingParameters parameters = new ExpandingParameters(
                            map.getAnyCoordinateOfSameTerrainTypeInSettlement(id, terrainType), terrainType);
                    int points = map.getPointsSettlementExpansionWouldProduce(parameters.getCoordinate(), parameters.getTerrainType());
                    if (map.settlementCanBeExpanded(parameters.getCoordinate(), parameters.getTerrainType())) {
                        movesWithScores.put(points, parameters);
                        expandMove = true;
                    }
                }
            }
        }
        for (int score : movesWithScores.keySet())
            scores.add(score);
        if (scores.size() != 0)
            placeWhereSettlementCanBeExpanded = movesWithScores.get(scores.last());
    }

    public void findCoordinatesWhereSettlementCanBeFound() {
        placeWhereSettlementCanBeFound = null;
        visitedCoordinates = new HashSet<>();
        for (int id : map.getSettlements().keySet()) {
            if (map.getSettlements().get(id).getPlayerID() == 1) {
                for(Coordinate tempCoordinate : map.getSettlements().get(id).bfs()){
                    map.locator.findCounterClockwiseCoordinatesAroundCoordinate(tempCoordinate);
                    for(Coordinate c : map.locator.surroundingCoordinates){
                        if(!visitedCoordinates.contains(c) && map.settlementCanBeFound(c)){
                            placeWhereSettlementCanBeFound = c;
                            break;
                        }
                        visitedCoordinates.add(c);
                    }
                }
            }
        }

        for (Coordinate c : map.getBoard().keySet()) {
            map.builder.getDifferentSettlementIDsAroundCoordinate(c);

            for (int id : map.builder.differentSettlementIDsAroundCoordinate) {
                if (!map.getSettlements().containsKey(id) && map.getSettlements().get(id).getPlayerID() == 1
                        && map.settlementCanBeFound(c)) {
                    placeWhereSettlementCanBeFound = c;
                    foundMove = true;
                    break;
                }
            }
            if (map.settlementCanBeFound(c)) {
                placeWhereSettlementCanBeFound = c;
                foundMove = true;
                break;
            }
        }
    }

    private boolean settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(int id) {
        return map.getSettlements().get(id).getSize() >= 5
                && !map.getSettlements().get(id).hasTotoro();
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

    private void findNeighborsOfCoordinateWhereTotoroCanBePlaced(Coordinate coordinate) {
        map.locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : map.locator.surroundingCoordinates) {
            if (totoroCanBePlacedInCoordinate(neighborCoordinate)) {
                placeWhereTotoroCanBePlaced = neighborCoordinate;
                totoroMove = true;
            }
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean settlementDoesNotContainTiger(int id) {
        return !map.getSettlements().get(id).hasTiger();
    }

    private void findNeighborsOfCoordinateWhereTigerCanBePlaced(Coordinate coordinate) {
        map.locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : map.locator.surroundingCoordinates) {
            if (tigerCanBePlacedInCoordinate(neighborCoordinate)) {
                placeWhereTigerCanBePlaced = neighborCoordinate;
                tigerMove = true;
            }
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean totoroCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && map.totoroCanBePlaced(neighborCoordinate);
    }

    private boolean tigerCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && map.tigerCanBePlaced(neighborCoordinate);
    }

    public void findPlaceWhereTileCanBePlaced(TerrainType leftTerrain, TerrainType rightTerrain) {
        for (int id : map.getSettlements().keySet()) {
            if (map.getSettlements().get(id).getPlayerID() == 2
                    && map.getSettlements().get(id).getSize() > 3) {
                for (Coordinate coordinate : map.getSettlements().get(id).bfs()) {
                    map.locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
                    for(Coordinate tempCoordinate : map.locator.surroundingCoordinates) {
                        if(map.getBoard().containsKey(tempCoordinate) && map.getBoard().get(tempCoordinate).getTerrainType() == TerrainType.VOLCANO) {
                            for (Orientation orientation : Orientation.getOrientations()) {
                                if (map.tileCanNukeOtherTiles(new Tile(leftTerrain, rightTerrain), tempCoordinate, orientation)) {
                                    placeWhereTileCanBePlaced = new TileParameters(leftTerrain, rightTerrain, tempCoordinate, orientation);
                                    tileMove = true;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (placeWhereTileCanBePlaced == null) {
            int maxX = -1000, maxY = -1000;
            int minX = 1000, minY = 1000;
            Coordinate coordinate = new Coordinate(-1000, -1000);
            Coordinate possibleCoordinate = map.locator.overAndToTheLeftOfMain(coordinate);
            int randomDirection = (int) (Math.random() * 3) + 1;
            switch (randomDirection) {
                case 1:
                    for (Coordinate c : map.getBoard().keySet()) {
                        if (c.getXCoordinate() > maxX) {
                            maxX = c.getXCoordinate();
                            coordinate = c;
                        }
                    }
                    possibleCoordinate = map.locator.toTheRightOfMain(coordinate);
                    break;
                case 2:
                    for (Coordinate c : map.getBoard().keySet()) {
                        if (c.getYCoordinate() < minY) {
                            minY = c.getYCoordinate();
                            coordinate = c;
                        }
                    }
                    possibleCoordinate = map.locator.overAndToTheRightOfMain(coordinate);
                    break;
                case 3:
                    for (Coordinate c : map.getBoard().keySet()) {
                        if (c.getXCoordinate() < minX) {
                            minX = c.getXCoordinate();
                            coordinate = c;
                        }
                    }
                    possibleCoordinate = map.locator.toTheLeftOfMain(coordinate);
                    break;
                case 4:
                    for (Coordinate c : map.getBoard().keySet()) {
                        if (c.getYCoordinate() > maxY) {
                            maxY = c.getYCoordinate();
                            coordinate = c;
                        }
                    }
                    possibleCoordinate = map.locator.belowAndToTheLeftOfMain(coordinate);
                    break;
                default:
                    for (Coordinate c : map.getBoard().keySet()) {
                        if (c.getYCoordinate() < minY) {
                            minY = c.getYCoordinate();
                            coordinate = c;
                        }
                    }
                    possibleCoordinate = map.locator.overAndToTheRightOfMain(coordinate);
                    break;
            }

            for (Orientation orientation : Orientation.getOrientations()) {
                if (map.tileCanBePlacedOnLevelOne(new Tile(leftTerrain, rightTerrain), possibleCoordinate, orientation)) {
                    placeWhereTileCanBePlaced = new TileParameters(leftTerrain, rightTerrain, possibleCoordinate, orientation);
                    break;
                }
            }
        }
        tileMove = true;
    }

    public Coordinate getPlaceWhereTigerCanBePlaced() {
        findCoordinateWhereTigerCanBePlaced();
        return placeWhereTigerCanBePlaced;
    }

    public ExpandingParameters getPlaceWhereSettlementCanBeExpanded() {
        findPlaceWhereSettlementCanBeExpanded();
        return placeWhereSettlementCanBeExpanded;
    }

    public Coordinate getPlaceWhereSettlementCanBeFound() {
        findCoordinatesWhereSettlementCanBeFound();
        return placeWhereSettlementCanBeFound;
    }

    public Coordinate getPlaceWhereTotoroCanBePlaced() {
        findCoordinateWhereTotoroCanBePlaced();
        return placeWhereTotoroCanBePlaced;
    }

    public TileParameters getPlaceWhereTileCanBePlaced(TerrainType leftTerrain, TerrainType rightTerrain){
        findPlaceWhereTileCanBePlaced(leftTerrain, rightTerrain);
        return placeWhereTileCanBePlaced;
    }

    public boolean[] getMoves() {
        return moves;
    }

    public Set<Coordinate> getVisitedCoordinates() {
        return visitedCoordinates;
    }
}
