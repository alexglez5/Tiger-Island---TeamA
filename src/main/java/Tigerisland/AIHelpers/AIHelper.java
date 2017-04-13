package Tigerisland.AIHelpers;

import Tigerisland.*;

import java.util.*;

public class AIHelper {
//    public Game map = new Game();
//    private Coordinate placeWhereTotoroCanBePlaced;
//    private Coordinate placeWhereTigerCanBePlaced;
//    private ExpandingParameters placeWhereSettlementCanBeExpanded;
//    private Coordinate placeWhereSettlementCanBeFound;
//    private TileParameters placeWhereTileCanBePlaced;
//    private HashSet<Coordinate> visitedCoordinates;

    public Game map = new Game();
    private Coordinate placeWhereTotoroCanBePlaced;
    private Coordinate placeWhereTigerCanBePlaced;
    private ExpandingParameters placeWhereSettlementCanBeExpanded;
    private Coordinate placeWhereSettlementCanBeFound;
    private TileParameters placeWhereTileCanBePlaced;
    private HashSet<Coordinate> visitedCoordinates;
    public boolean opponentNukes = false;
    TreeSet<Integer> sizes = new TreeSet<>();
    HashMap<Integer, ExpandingParameters> movesWithSizes = new HashMap<>();

    public void flagOpponentNukes(){
        opponentNukes = true;
        System.out.println("Opponents is nuking");
    }

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
                    && map.getSettlements().get(id).getPlayerID() == 1)
                for (Coordinate coordinate : map.getSettlements().get(id).bfs())
                    findNeighborsOfCoordinateWhereTigerCanBePlaced(coordinate);

    }

    public void findPlaceWhereSettlementCanBeExpanded() {
        placeWhereSettlementCanBeExpanded = null;
        findAllPossiblePairsOfSizeAndExpansionLocations();
        if(opponentNukes && sizes.size() > 0){
            placeWhereSettlementCanBeExpanded = movesWithSizes.get(sizes.last());
        }
        else {
            if (sizes.contains(3))
                placeWhereSettlementCanBeExpanded = movesWithSizes.get(3);
            else if (sizes.contains(2))
                placeWhereSettlementCanBeExpanded = movesWithSizes.get(2);
            else if (sizes.contains(1))
                placeWhereSettlementCanBeExpanded = movesWithSizes.get(1);
        }
    }

    private void findAllPossiblePairsOfSizeAndExpansionLocations() {
        sizes = new TreeSet<>();
        movesWithSizes = new HashMap<>();
        for (int id : map.getSettlements().keySet()) {
            if (map.getSettlements().get(id).getPlayerID() == 1
                    && !map.getSettlements().get(id).hasTotoro()
                    && !map.getSettlements().get(id).hasTiger()) {
                for (TerrainType terrainType : map.getDifferentTerrainTypesInSettlement(id)) {
                    ExpandingParameters parameters = new ExpandingParameters(
                            map.getAnyCoordinateOfSameTerrainTypeInSettlement(id, terrainType), terrainType);
                    int size = map.getCoordinatesOfPossibleSettlementExpansion(parameters.getCoordinate(), parameters.getTerrainType()).size();
                    if (map.settlementCanBeExpanded(parameters.getCoordinate(), parameters.getTerrainType()))
                        movesWithSizes.put(size, parameters);
                }
            }
        }
        for (int size : movesWithSizes.keySet())
            sizes.add(size);
    }

//    public void findPlaceWhereSettlementCanBeExpanded() {
//        placeWhereSettlementCanBeExpanded = null;
//        TreeSet<Integer> sizes = new TreeSet<>();
//        HashMap<Integer, ExpandingParameters> movesWithScores = new HashMap<>();
//        for (int id : map.getSettlements().keySet()) {
//            if (map.getSettlements().get(id).getPlayerID() == 1
//                    && !map.getSettlements().get(id).hasTotoro()
//                    && !map.getSettlements().get(id).hasTiger()) {
//                for (TerrainType terrainType : map.getDifferentTerrainTypesInSettlement(id)) {
//                    ExpandingParameters parameters = new ExpandingParameters(
//                            map.getAnyCoordinateOfSameTerrainTypeInSettlement(id, terrainType), terrainType);
//                    int size = map.getCoordinatesOfPossibleSettlementExpansion(parameters.getCoordinate(), parameters.getTerrainType()).size();
//                    if (map.settlementCanBeExpanded(parameters.getCoordinate(), parameters.getTerrainType())) {
//                        movesWithScores.put(size, parameters);
//                    }
//                }
//            }
//        }
//        for (int size : movesWithScores.keySet())
//            sizes.add(size);
//        if(sizes.contains(3)) {
//            placeWhereSettlementCanBeExpanded = movesWithScores.get(3);
//            return;
//        }
//        else if(sizes.contains(2)) {
//            placeWhereSettlementCanBeExpanded = movesWithScores.get(2);
//            return;
//        }
//        else if(sizes.contains(1)) {
//            placeWhereSettlementCanBeExpanded = movesWithScores.get(1);
//            return;
//        }
//    }

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
                            return;
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
                    return;
                }
            }
            if (map.settlementCanBeFound(c)) {
                placeWhereSettlementCanBeFound = c;
                return;
            }
        }
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
            // check if we can nuke our own totoro settlement to cause an advantageous split
            if (map.getSettlements().get(id).getPlayerID() == 1
                    && map.getSettlements().get(id).hasTotoro()
                    && map.getSettlements().get(id).getSize() > 4) {

                Coordinate totoro = new Coordinate(0,0);
                Coordinate toNuke = new Coordinate(0,0);
                // find the coordinate that has the totoro
                for (Coordinate t : map.getSettlements().get(id).bfs()) {
                    if (map.getBoard().get(t).hasTotoro())
                        totoro = t;
                }
                // look at the surrounding coordinates of the totoro, and check if there is only one connecting hex
                ArrayList<Coordinate> edgesNextToTotoro = map.getSettlements().get(id).getEdgesFromCoordinate(totoro);
                if (edgesNextToTotoro.size() == 1) {
                    // get the coordinate to nuke
                    toNuke = edgesNextToTotoro.get(0);

                    // get the surrounding coordinates of this one
                    map.locator.findCounterClockwiseCoordinatesAroundCoordinate(toNuke);

                    // check all surrounding coordinates to find an orientation that can nuke our toNuke coordinate
                    for (Coordinate tempCoordinate : map.locator.surroundingCoordinates) {
                        if (map.getBoard().containsKey(tempCoordinate) && map.getBoard().get(tempCoordinate).getTerrainType() == TerrainType.VOLCANO) {
                            // find the orientation that would nuke the toNuke coordinate
                            for (Orientation orientation : Orientation.getOrientations()) {
                                map.locator.setMainCoordinateAndOrientation(tempCoordinate, orientation);
                                map.locator.determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
                                if (map.tileCanNukeOtherTiles(new Tile(leftTerrain, rightTerrain), tempCoordinate, orientation)
                                        && (map.locator.leftOfMainTerrainCoordinate.equals(toNuke)
                                        || map.locator.rightOfMainTerrainCoordinate.equals(toNuke))) {
                                    placeWhereTileCanBePlaced = new TileParameters(leftTerrain, rightTerrain, tempCoordinate, orientation);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }


        for (int id : map.getSettlements().keySet()) {
            if (map.getSettlements().get(id).getPlayerID() == 2
                    && map.getSettlements().get(id).getSize() > 3) {
                for (Coordinate coordinate : map.getSettlements().get(id).bfs()) {
                    map.locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
                    for (Coordinate tempCoordinate : map.locator.surroundingCoordinates) {
                        if (map.getBoard().containsKey(tempCoordinate) && map.getBoard().get(tempCoordinate).getTerrainType() == TerrainType.VOLCANO) {
                            for (Orientation orientation : Orientation.getOrientations()) {
                                if (map.tileCanNukeOtherTiles(new Tile(leftTerrain, rightTerrain), tempCoordinate, orientation)) {
                                    placeWhereTileCanBePlaced = new TileParameters(leftTerrain, rightTerrain, tempCoordinate, orientation);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        int maxX = -1000, maxY = -1000;
        int minX = 1000, minY = 1000;
        Coordinate coordinate = new Coordinate();
        Coordinate possibleCoordinate = new Coordinate();
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
                return;
            }
        }
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

    public Set<Coordinate> getVisitedCoordinates() {
        return visitedCoordinates;
    }
}
