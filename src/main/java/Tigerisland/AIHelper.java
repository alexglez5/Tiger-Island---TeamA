//package Tigerisland;
//
//import java.util.ArrayList;
//import java.util.Set;
//import java.util.TreeSet;
//
///**
// * Created by Alexander Gonzalez on 4/2/2017.
// */
//public class AIHelper {
//    private final int minimumSizeOfAdjacentSettlementToTotoro = 5;
//    private GameBoard map;
//    private ArrayList<Coordinate> placesWhereTotoroCanBePlaced;
//    private ArrayList<Coordinate> placesWhereTigerCanBePlaced;
//    private Boolean settlementHasTiger;
//
//    public AIHelper(GameBoard gameBoard){
//        this.map = gameBoard;
//    }
//
//    //todo
//    /*
//    - add 2D arraylist to GameBoard that will contain current levels in the game and
//        tileIDs of the tiles at that level (maybe skip level 1[will always be the last option])
//    - functions that will return arraylists of cooridnates for all the places
//    one can:
//        - place a tile in level one
//        - nuke a tile at level n, n-1... 1
//        - place a totoro (using hashtable mentioned above)
//        - place a tiger
//        - expand a settlement
//        - found a settlement
//    */
//    public void getCoordinatesWhereTotoroCanBePlaced(){
//        placesWhereTotoroCanBePlaced = new ArrayList<>();
//        Set<Integer> iDsOfSettlementsOfAtLeastSizeFive = new TreeSet<>();
//        for(int id : map.settlements.keySet()){
//            if(map.settlements.get(id).size() >= minimumSizeOfAdjacentSettlementToTotoro){
//                iDsOfSettlementsOfAtLeastSizeFive.add(id);
//            }
//        }
//
//        for(int id : iDsOfSettlementsOfAtLeastSizeFive){
//            for(Coordinate coordinate : map.settlements.get(id)){
//                map.helper.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
//                for(Coordinate neighborCoordinate : map.helper.counterClockwiseCoordinatesAroundCoordinate){
//                    if(map.totoroCanBePlaced(neighborCoordinate))
//                        placesWhereTotoroCanBePlaced.add(neighborCoordinate);
//                }
//            }
//        }
//    }
//
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
//}
