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

    public HashSet<Coordinate> getPlacesWhereTotoroCanBePlaced() {
        return placesWhereTotoroCanBePlaced;
    }

    //todo
    /*
    - functions that will return lists of coordinates for all the places
    one can:
        - place a tile in level one
        - nuke a tile at level n, n-1... 1
        - place a tiger
        - expand a settlement
        - found a settlement
    */

    public void getCoordinatesWhereTotoroCanBePlaced() {
        placesWhereTotoroCanBePlaced = new HashSet<>();
        visitedCoordinates = new HashSet<>();
        for (int id : settlements.keySet())
            if (settlementIsAtLeastSizeFive(id))
                for (Coordinate coordinate : settlements.get(id).settlementCoordinates)
                    findNeighborsOfCoordinateWhereTotoroCanBePlaced(coordinate);

    }

    private boolean settlementIsAtLeastSizeFive(int id) {
        return settlements.get(id).settlementCoordinates.size() >= 5;
    }

    private void findNeighborsOfCoordinateWhereTotoroCanBePlaced(Coordinate coordinate) {
        findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        for (Coordinate neighborCoordinate : counterClockwiseCoordinatesAroundCoordinate) {
            if (totoroCanBePlacedInCoordinate(neighborCoordinate))
                placesWhereTotoroCanBePlaced.add(neighborCoordinate);
            visitedCoordinates.add(neighborCoordinate);
        }
    }

    private boolean totoroCanBePlacedInCoordinate(Coordinate neighborCoordinate) {
        return !visitedCoordinates.contains(neighborCoordinate) && totoroCanBePlaced(neighborCoordinate);
    }

//    public void getCoordinatesWhereTigerCanBePlaced(){
//        placesWhereTigerCanBePlaced = new ArrayList<>();
//        boolean settlementHasTiger;
//        Set<Integer> iDsOfSettlementsThatDoNotContainATiger = new TreeSet<>();
//        for(int id : map.settlements.keySet()){
//            settlementHasTiger = false;
//            for(Coordinate coordinateInSettlement : map.settlements.get(id)){
//                if(map.gameBoard.get(coordinateInSettlement).hasTiger()){
//                    settlementHasTiger = true;
//                }
//            }
//            if(!settlementHasTiger){
//                for(Coordinate coordinateInSettlement : map.settlements.get(id)){
//                    map.helper.findCounterClockwiseCoordinatesAroundCoordinate(coordinateInSettlement);
//                    for(Coordinate neighborCoordinate : map.helper.counterClockwiseCoordinatesAroundCoordinate){
//                        if(map.tigerCanBePlaced(neighborCoordinate))
//                            placesWhereTigerCanBePlaced.add(neighborCoordinate);
//                    }
//                }
//            }
//        }
//    }
}
