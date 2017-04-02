//package Tigerisland;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Set;
//import java.util.TreeSet;
//
///**
// * Created by Alexander Gonzalez on 4/2/2017.
// */
//public class AIHelper {
//    private final int minimumSizeOfAdjacentSettlementToTotoro = 5;
//    private GameBoard gameBoard;
//    private ArrayList<Coordinate> placesWhereTotoroCanBePlaced;
//
//    public AIHel per(GameBoard gameBoard){
//        this.gameBoard = gameBoard;
//    }
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
//        Set<Integer> map = new TreeSet<>();
//        for(int id : gameBoard.settlements.keySet()){
//            if(gameBoard.settlements.get(id).size() >= minimumSizeOfAdjacentSettlementToTotoro){
//                map.add(id);
//            }
//        }
//
//        for(int id : map){
//            for(Coordinate coordinate : gameBoard.settlements.get(id)){
//
//                if()
//            }
//        }
//
//    }
//}
