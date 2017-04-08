package Tigerisland;

import Tigerisland.PlayerActions.ActionHelper;

import java.util.*;

public class Settlement extends Game {
    private int size;
    private HashMap<Coordinate, ArrayList<Coordinate>> edges = new HashMap<>();
    private int settlementID;
    private boolean hasTotoro;
    private boolean hasTiger;
    protected HashMap<Coordinate, Hex> gameBoard = new HashMap<>();

    public void setGameBoard(HashMap<Coordinate, Hex> gameBoard){
        this.gameBoard = gameBoard;
    }
    public HashMap<Coordinate, Hex> getGameBoard(){
        return gameBoard;
    }

    public Settlement(Coordinate cord) {
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
        locator.findCounterClockwiseCoordinatesAroundCoordinate(cord);
        edges.put(cord, new ArrayList<Coordinate>());
        for (Coordinate c : locator.surroundingCoordinates) {
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
        locator.findCounterClockwiseCoordinatesAroundCoordinate(cord);
        edges.remove(cord);
        for (Coordinate c : locator.surroundingCoordinates) {
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