package Tigerisland;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexander Gonzalez on 4/2/2017.
 */
public class Settlement extends ActionHelper {
    //    protected static HashMap<Integer, ArrayList<Coordinate>> settlements = new HashMap<>();
    protected static ArrayList<Coordinate> settlementCoordinates = new ArrayList<>();
    protected boolean hasTotoro = false;
    protected boolean hasTiger = false;

    public void placeTotoro(){
        hasTotoro = true;
    }

    public void placeTiger(){
        hasTiger = true;
    }

    public static void addCoordinateToSettlement(Coordinate coordinate) {
        settlementCoordinates.add(coordinate);
    }

    //    public static boolean containsTotoro(int settlementID){
//        for(Coordinate coordinate : settlements.get(settlementID))
//            if(gameBoard.get(coordinate).hasTotoro())
//                return true;
//        return false;
//    }
//
//    public static boolean containsTiger(int settlementID){
//        for(Coordinate coordinate : settlements.get(settlementID))
//            if(gameBoard.get(coordinate).hasTiger())
//                return true;
//        return false;
//    }
//

}
