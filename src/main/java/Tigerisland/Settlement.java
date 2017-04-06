package Tigerisland;

import Tigerisland.PlayerActions.ActionHelper;

import java.util.*;

public class Settlement extends Game {

    ActionHelper cordLoc = helper;
    private int size;
    private HashMap<Coordinate, ArrayList<Coordinate>> edges = new HashMap<>();
    private static int createdSettlements = 0;
    private int settlementID;
    private boolean hasTotoro;
    private boolean hasTiger;

    public Settlement(Coordinate cord) {
        createdSettlements++;
        if(gameBoard.containsKey(cord))
            settlementID = gameBoard.get(cord).getSettlementID();
        hasTotoro = false;
        hasTiger = false;
        size = 1;
        edges.put(cord, new ArrayList<Coordinate>());
    }

    public int getSize() { return size; }

    public boolean contains(Coordinate c) {
        return edges.containsKey(c);
    }

    public void addToSettlement(Coordinate cord) {
        cordLoc.findCounterClockwiseCoordinatesAroundCoordinate(cord);
        edges.put(cord, new ArrayList<Coordinate>());
        for (Coordinate c : cordLoc.surroundingCoordinates) {
            // if the neighboring coordinate is already part of the settlement
            if (edges.containsKey(c)) {
                // get the edges of that neighbor coordinate and add our new coordinate (vice versa)
                edges.get(c).add(cord);
                edges.get(cord).add(c);
            }
        }
        size++;
        if(gameBoard.containsKey(cord))
            settlementID = gameBoard.get(cord).getSettlementID();
    }

    public void removeFromSettlement(Coordinate cord) {
        cordLoc.findCounterClockwiseCoordinatesAroundCoordinate(cord);
        edges.remove(cord);
        for (Coordinate c : cordLoc.surroundingCoordinates) {
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

    public boolean hasTotoro() {
        return hasTotoro;
    }

    public void placeTotoro() {
        this.hasTotoro = hasTotoro;
    }

    public boolean hasTiger() {
        return hasTiger;
    }

    public void placeTiger() {
        this.hasTiger = hasTiger;
    }

    public int getSettlementID() {
        return settlementID;
    }
}