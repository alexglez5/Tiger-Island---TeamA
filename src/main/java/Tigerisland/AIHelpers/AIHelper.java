package Tigerisland.AIHelpers;

import Tigerisland.*;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.HashMap;
import java.util.HashSet;
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

    public void findPossibleMoves() {
        findCoordinateWhereTotoroCanBePlaced();
        findCoordinateWhereTigerCanBePlaced();
        findPlaceWhereSettlementCanBeExpanded();
        setArrayOfMoveOptions();
    }

    public void findPlaceWhereTileCanBePlaced(TerrainType leftTerrain, TerrainType rightTerrain){
        for(int id : map.getSettlements().keySet()){
            if(map.getSettlements().get(id).getPlayerID() != map.getCurrentPlayerId()
                    && map.getSettlements().get(id).getSize() > 3){
               for(Coordinate coordinate : map.getSettlements().get(id).bfs()){
                   for(Orientation orientation : Orientation.getOrientations()){
                       if(map.tileCanNukeOtherTiles(new Tile(leftTerrain, rightTerrain), coordinate, orientation)){
                           placeWhereTileCanBePlaced = new TileParameters(leftTerrain, rightTerrain, coordinate, orientation);
                           break;
                       }
                   }
               }
            }
        }

        int maxX = -1000, maxY = -1000;
        int minX = 1000, minY = 10000;

        Coordinate coordinate = new Coordinate(-1000,-10000);
        Coordinate possibleCoordinate = map.locator.overAndToTheLeftOfMain(coordinate);
        int randomDirection = (int)(Math.random() * 4) + 1;
        switch (randomDirection){
            case 1:
                for(Coordinate c : map.getBoard().keySet()){
                    if(c.getXCoordinate() > maxX) {
                        maxX = c.getXCoordinate();
                        coordinate = c;
                    }
                }
                possibleCoordinate = map.locator.toTheRightOfMain(coordinate);
            case 2:
                for(Coordinate c : map.getBoard().keySet()){
                    if(c.getYCoordinate() > maxY) {
                        maxY = c.getYCoordinate();
                        coordinate = c;
                    }
                }
                possibleCoordinate = map.locator.overAndToTheRightOfMain(coordinate);
            case 3:
                for(Coordinate c : map.getBoard().keySet()){
                    if(c.getXCoordinate() < minX) {
                        minX = c.getXCoordinate();
                        coordinate = c;
                    }
                }
                possibleCoordinate = map.locator.toTheLeftOfMain(coordinate);
            case 4:
                for(Coordinate c : map.getBoard().keySet()){
                    if(c.getYCoordinate() < minY) {
                        minY = c.getYCoordinate();
                        coordinate = c;
                    }
                }
                possibleCoordinate = map.locator.belowAndToTheLeftOfMain(coordinate);
            default:
                for(Coordinate c : map.getBoard().keySet()){
                    if(c.getYCoordinate() < minY) {
                        minY = c.getYCoordinate();
                        coordinate = c;
                    }
                }
        }

        for(Orientation orientation : Orientation.getOrientations()){
            if(map.tileCanNukeOtherTiles(new Tile(leftTerrain, rightTerrain), possibleCoordinate, orientation)){
                placeWhereTileCanBePlaced = new TileParameters(leftTerrain, rightTerrain, possibleCoordinate, orientation);
                break;
            }
        }
    }

    public void findCoordinateWhereTotoroCanBePlaced() {
        visitedCoordinates = new HashSet<>();
        for (int id : map.getSettlements().keySet())
            if (settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(id))
                for (Coordinate coordinate : map.getSettlements().get(id).bfs())
                    findNeighborsOfCoordinateWhereTotoroCanBePlaced(coordinate);
    }

    public void findCoordinateWhereTigerCanBePlaced() {
        visitedCoordinates = new HashSet<>();
        for (int id : map.getSettlements().keySet())
            if (settlementDoesNotContainTiger(id))
                for (Coordinate coordinate : map.getSettlements().get(id).bfs())
                    findNeighborsOfCoordinateWhereTigerCanBePlaced(coordinate);

    }

    private void setArrayOfMoveOptions() {
        moves[0] = tileMove;
        moves[1] = totoroMove;
        moves[2] = tigerMove;
        moves[3] = expandMove;
        moves[4] = foundMove;
    }

    private boolean settlementIsAtLeastSizeFiveAndDoesNotContainTotoro(int id) {
        return map.getSettlements().get(id).getSize() >= 5
                && !map.getSettlements().get(id).hasTotoro();
    }

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

    //todo
    /*
    - functions that will return lists of coordinates for all the places
    one can:
        - place a tile in level one
        - nuke a tile at level n, n-1... 1
        - expand a settlement
        - found a settlement
    */

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

    public Coordinate getPlaceWhereTigerCanBePlaced() {
        return placeWhereTigerCanBePlaced;
    }

    public ExpandingParameters getPlaceWhereSettlementCanBeExpanded() {
        return placeWhereSettlementCanBeExpanded;
    }

    public Coordinate getPlaceWhereSettlementCanBeFound() {
        return placeWhereSettlementCanBeFound;
    }

    public TileParameters getPlaceWhereTileCanBePlaced() {
        return placeWhereTileCanBePlaced;
    }

    public void findPlaceWhereSettlementCanBeExpanded() {
        TreeSet<Integer> scores = new TreeSet<>();
        HashMap<Integer, ExpandingParameters> movesWithScores = new HashMap<>();
        for (int id : map.getSettlements().keySet()) {
            if (map.getSettlements().get(id).getPlayerID() == map.getCurrentPlayerId()) {
                for (TerrainType terrainType : map.getDifferentTerrainTypesInSettlement(id)) {
                    ExpandingParameters parameters = new ExpandingParameters(
                            map.getAnyCoordinateOfSameTerrainTypeInSettlement(id, terrainType), terrainType);
                    if (map.settlementCanBeExpanded(map.getAnyCoordinateOfSameTerrainTypeInSettlement(id,
                            terrainType), terrainType)) {
                        movesWithScores.put(map.getPointsSettlementExpansionWouldProduce(map.
                                getAnyCoordinateOfSameTerrainTypeInSettlement(id, terrainType), terrainType), parameters);
                        expandMove = true;
                    }
                }
            }
        }
        for (int score : movesWithScores.keySet())
            scores.add(score);
        placeWhereSettlementCanBeExpanded = movesWithScores.get(scores.last());
    }

    public void findCoordinatesWhereSettlementCanBeFound() {
        visitedCoordinates = new HashSet<>();
        for (Coordinate c : map.getBoard().keySet()) {
            if (map.settlementCanBeFound(c)) {
                placeWhereSettlementCanBeFound = c;
                foundMove = true;
                break;
            }
        }
    }

    public Coordinate getPlaceWhereTotoroCanBePlaced() {
        return placeWhereTotoroCanBePlaced;
    }

    public Coordinate getPlacesWhereTigerCanBePlaced() {
        return placeWhereTigerCanBePlaced;
    }

    public TileParameters getPlacesWhereTileCanBePlaced() {
        return placeWhereTileCanBePlaced;
    }

    public ExpandingParameters getPlacesWhereSettlementCanBeExpanded() {
        return placeWhereSettlementCanBeExpanded;
    }

    public Coordinate getPlacesWhereSettlementCanBeFound() {
        return placeWhereSettlementCanBeFound;
    }

    public boolean[] getMoves() {
        return moves;
    }
}
