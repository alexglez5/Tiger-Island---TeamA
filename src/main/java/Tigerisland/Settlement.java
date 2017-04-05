package Tigerisland;

import java.util.ArrayList;

public class Settlement {

    private int size;
    private ArrayList<Coordinate> locations;
    private static int createdSettlements = 0;
    private int settlementID;

    public Settlement(Coordinate cord) {
        createdSettlements++;
        settlementID = createdSettlements;
        int size = 1;
        locations.add(cord);
    }

    public int getSize() { return size; }

    public void addHex(Coordinate cord) {
        size++;
        locations.add(cord);
    }

    public void removeHex(Coordinate cord) {
        size--;
        locations.remove(cord);
    }

    public Coordinate removeElement() {
        Coordinate x = locations.remove(0);
        size--;
        return x;
    }

}
