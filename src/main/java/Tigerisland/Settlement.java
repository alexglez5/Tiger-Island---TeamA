package Tigerisland;

import java.util.*;

public class Settlement {

    CoordinatesLocator cordLoc = new CoordinatesLocator();
    private int size;
    private HashMap<Coordinate, ArrayList<Coordinate>> edges = new HashMap<>();
    private static int createdSettlements = 0;
    private int settlementID;

    public Settlement(Coordinate cord) {
        createdSettlements++;
        settlementID = createdSettlements;
        size = 1;
        edges.put(cord, new ArrayList<Coordinate>());
    }

    public int getSize() { return size; }

    public boolean contains(Coordinate c) {
        return edges.containsKey(c);
    }

    public void addToSettlement(Coordinate cord) {
        Coordinate[] potentialNeighbors = cordLoc.produceClockwiseNeighborCoordinates(cord);
        edges.put(cord, new ArrayList<Coordinate>());
        for (Coordinate c :potentialNeighbors) {
            // if the neighboring coordinate is already part of the settlement
            if (edges.containsKey(c)) {
                // get the edges of that neighbor coordinate and add our new coordinate (vice versa)
                edges.get(c).add(cord);
                edges.get(cord).add(c);
            }
        }
        size++;
    }

    public void removeFromSettlement(Coordinate cord) {
        Coordinate[] potentialNeighbors = cordLoc.produceClockwiseNeighborCoordinates(cord);
        edges.remove(cord);
        for (Coordinate c : potentialNeighbors) {
            if (edges.containsKey(c))
                edges.get(c).remove(cord);
        }
        size--;
    }

    public Set<Coordinate> bfs() {
        // set up bfs
        Set<Coordinate> visited = new HashSet<Coordinate>();
        Set<Coordinate> elements = edges.keySet();
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.add(elements.iterator().next());

        while(!queue.isEmpty()) {
            // get the first thing in the queue
            Coordinate current = queue.remove();
            visited.add(current);

            // get the vertices that are connected to this element and not in visited
            for (Coordinate c : edges.get(current)) {
                if (!visited.contains(c)) {
                    queue.add(c);
                }
            }
        }
        return visited;
    }
}
