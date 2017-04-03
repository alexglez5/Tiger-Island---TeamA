package Tigerisland;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alexander Gonzalez on 4/2/2017.
 */
public class Settlement extends ActionHelper {
    public ArrayList<Coordinate> settlementCoordinates;
    protected boolean hasTotoro;
    protected boolean hasTiger;

    public Settlement(){
        settlementCoordinates = new ArrayList<>();
        hasTiger = false;
        hasTotoro = false;
    }

    public void placeTotoro(){
        hasTotoro = true;
    }

    public void placeTiger(){
        hasTiger = true;
    }

    public void addCoordinateToSettlement(Coordinate coordinate) {
        settlementCoordinates.add(coordinate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Settlement that = (Settlement) o;

        if (hasTotoro != that.hasTotoro) return false;
        if (hasTiger != that.hasTiger) return false;
        return settlementCoordinates != null ? settlementCoordinates.equals(that.settlementCoordinates) : that.settlementCoordinates == null;
    }

    @Override
    public int hashCode() {
        int result = settlementCoordinates != null ? settlementCoordinates.hashCode() : 0;
        result = 31 * result + (hasTotoro ? 1 : 0);
        result = 31 * result + (hasTiger ? 1 : 0);
        return result;
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
