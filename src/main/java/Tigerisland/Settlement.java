package Tigerisland;

import java.util.ArrayList;

public class Settlement {

    private int size;
    private ArrayList<Coordinate> locations;

    public Settlement(Coordinate cord) {
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

    public Coordinate removeLocation() {
        Coordinate x = locations.remove(0);
        size--;
        return x;
    }

}
