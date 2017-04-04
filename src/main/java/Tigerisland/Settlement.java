package Tigerisland;

import Tigerisland.PlayerActions.ActionHelper;

import java.util.ArrayList;

/**
 * Created by Alexander Gonzalez on 4/2/2017.
 */
public class Settlement extends ActionHelper {
    public ArrayList<Coordinate> settlementCoordinates;
    private boolean hasTotoro;
    private boolean hasTiger;

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

    public boolean hasTiger(){
        return hasTiger;
    }

    public boolean hasTotoro(){
        return hasTotoro;
    }
}
