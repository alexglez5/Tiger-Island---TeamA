package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.*;

public class TilePlacer {
    protected ActionHelper locator = new ActionHelper();
    protected Set<Integer> settlementIdsOfHexesUnderTile;
    protected HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    protected Player player;
    protected HashMap<Integer, Settlement> settlements;
    protected Tile tile;

    public void updtateComponents(ComponentsDTO dto) {
        this.gameBoard = dto.getGameBoard();
        this.settlements = dto.getSettlements();
        this.player = dto.getPlayer();
        this.locator = dto.getLocator();
    }

    public ComponentsDTO getComponents() {
        return new ComponentsDTO(this.gameBoard, this.settlements, this.getPlayer(), this.locator);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void placeOneStartingTile() {
        gameBoard.put(new Coordinate(0, -1), new Hex(TerrainType.JUNGLE, 1));
        gameBoard.put(new Coordinate(1, -1), new Hex(TerrainType.LAKE, 1));
        gameBoard.put(new Coordinate(0, 0), new Hex(TerrainType.VOLCANO, 1));
        gameBoard.put(new Coordinate(-1, 1), new Hex(TerrainType.ROCK, 1));
        gameBoard.put(new Coordinate(0, 1), new Hex(TerrainType.GRASS, 1));
    }

    public void processParameters(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        locator.mainTerrainCoordinate = mainTerrainCoordinate;
        locator.orientation = terrainsOrientation;
        this.tile = tile;
        locator.updateXAndYCoordinateOfCurrentTerrain(mainTerrainCoordinate);
        locator.determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
    }

    public void nuke() {
        int level = 0;
        if(gameBoard.containsKey(locator.mainTerrainCoordinate))
            level = gameBoard.get(locator.mainTerrainCoordinate).getLevel();
        getDifferentSettlementIDsOfATile();
        placeTileOnMap();
        increaseLevel(level);
        removeFromSettlement();
        splitSettlements();
    }

    public Set<Integer> getDifferentSettlementIDsOfATile() {
        settlementIdsOfHexesUnderTile = new HashSet<>();
        if (terrainContainsAPiece(locator.leftOfMainTerrainCoordinate))
            settlementIdsOfHexesUnderTile.add(gameBoard.get(locator.leftOfMainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(locator.mainTerrainCoordinate))
            settlementIdsOfHexesUnderTile.add(gameBoard.get(locator.mainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(locator.rightOfMainTerrainCoordinate))
            settlementIdsOfHexesUnderTile.add(gameBoard.get(locator.rightOfMainTerrainCoordinate).getSettlementID());
        return settlementIdsOfHexesUnderTile;
    }

    public void placeTileOnMap() {
        gameBoard.put(locator.leftOfMainTerrainCoordinate, tile.getLeftOfMainTerrain());
        gameBoard.put(locator.mainTerrainCoordinate, tile.getMainTerrain());
        gameBoard.put(locator.rightOfMainTerrainCoordinate, tile.getRightOfMainTerrain());
    }

    private void increaseLevel(int previousLevel) {
        gameBoard.get(locator.mainTerrainCoordinate).setLevel(previousLevel + 1);
        gameBoard.get(locator.leftOfMainTerrainCoordinate).setLevel(previousLevel + 1);
        gameBoard.get(locator.rightOfMainTerrainCoordinate).setLevel(previousLevel + 1);
    }

    public boolean terrainContainsAPiece(Coordinate terrainCoordinate) {
        return gameBoard.containsKey(terrainCoordinate)
                && (gameBoard.get(terrainCoordinate).hasVillager()
                || gameBoard.get(terrainCoordinate).hasTotoro()
                || gameBoard.get(terrainCoordinate).hasTiger());
    }

    private void removeFromSettlement() {
        for (int sid : settlementIdsOfHexesUnderTile) {
            // for each settlementId, check if the underlying coordinate is a part of that settlement
            // then, delete it if it exists.
            if(settlements.containsKey(sid)){
                if (settlements.get(sid).contains(locator.leftOfMainTerrainCoordinate))
                    settlements.get(sid).removeFromSettlement(locator.leftOfMainTerrainCoordinate);
                if (settlements.get(sid).contains(locator.rightOfMainTerrainCoordinate))
                    settlements.get(sid).removeFromSettlement(locator.rightOfMainTerrainCoordinate);
            }
        }
    }

    public void splitSettlements() {
        for (int sid: settlementIdsOfHexesUnderTile) {
            // for each settlement id, while the bfs does not return the full size, get all the coordinates of that
            // bfs and put it in its own settlement.
            Set<Coordinate> connectedComponent = new HashSet<>();
            if(settlements.containsKey(sid))
                connectedComponent = settlements.get(sid).bfs();
            else
                return;

            boolean componentContainsCoordinateHashCode = false;

            while (connectedComponent.size() != settlements.get(sid).getSize()) {
                // get the first coordinate of the connected component
                Iterator<Coordinate> i = connectedComponent.iterator();

                if (i.hasNext()) {

                    Coordinate checkComp = i.next();

                    do {
                        if (checkComp.hashCode() == sid)
                            componentContainsCoordinateHashCode = true;
                        if (i.hasNext())
                            checkComp = i.next();
                    } while (i.hasNext());

                    // get another iterator
                    i = connectedComponent.iterator();
                    Coordinate firstCord = i.next();

                    // first check if the firstCord of this connected component was the original settlement id.
                    // If it was, then set a flag to skip this iteration entirely, and get another random ordering
                    // where the firstCord wont be picked first
                    if (!componentContainsCoordinateHashCode) {

                        // remove it from the current settlement
                        settlements.get(sid).removeFromSettlement(firstCord);

                        // found a new settlement with it (with a unique id) and update the hex settlementID
                        settlements.put(firstCord.hashCode(), new Settlement(firstCord));
                        settlements.get(firstCord.hashCode()).setPlayerID(settlements.get(sid).getPlayerID());
                        gameBoard.get(firstCord).setSettlementID(firstCord.hashCode());

                        // check the coordinate for tiger or totoro to set the flag in new settlement
                        if (gameBoard.get(firstCord).hasTotoro()) {
                            settlements.get(firstCord.hashCode()).placeTotoro();
                            settlements.get(sid).removeTotoro();
                        }
                        if (gameBoard.get(firstCord).hasTiger()) {
                            settlements.get(firstCord.hashCode()).placeTiger();
                            settlements.get(sid).removeTiger();
                        }

                        // iterate through the rest of the connected component, remove from current settlement, add to
                        // this new settlement, and update the gameboard
                        // Iterator<Coordinate> i = connectedComponent.iterator();
                        while (i.hasNext()) {
                            Coordinate nextCord = i.next();
                            settlements.get(sid).removeFromSettlement(nextCord);
                            if(settlements.containsKey(firstCord.hashCode()))
                                settlements.get(firstCord.hashCode()).addToSettlement(nextCord);
                            if(gameBoard.containsKey(nextCord))
                                gameBoard.get(nextCord).setSettlementID(firstCord.hashCode());

                            // make sure to set the flags for totoro and tiger in newly split settlement and remove
                            // from the original split settlement
                            if (gameBoard.containsKey(nextCord) && gameBoard.get(nextCord).hasTotoro()) {
                                settlements.get(firstCord.hashCode()).placeTotoro();
                                settlements.get(sid).removeTotoro();
                            }
                            if (gameBoard.containsKey(nextCord) && gameBoard.get(nextCord).hasTiger()) {
                                settlements.get(firstCord.hashCode()).placeTiger();
                                settlements.get(sid).removeTiger();
                            }
                        }
                    }

                    // run another bfs to test for while condition
                    connectedComponent = settlements.get(sid).bfs();
                    componentContainsCoordinateHashCode = false;
                }
            }
        }
    }
}