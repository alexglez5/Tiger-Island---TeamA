package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TilePlacer {
    protected static ActionHelper locator = new ActionHelper();
    protected Set<Integer> settlementIdsOfHexesInTile;
    private Tile tile;
    protected HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    protected Player player;
    protected HashMap<Integer, Settlement> settlements;

    public HashMap<Integer, Settlement> getSettlements(){
        return this.settlements;
    }
    public void setSettlements(HashMap<Integer, Settlement> settlements){
        this.settlements = settlements;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HashMap<Coordinate, Hex> getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(HashMap<Coordinate, Hex> gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void placeOneStartingTile() {
        gameBoard.put(new Coordinate(0, -1), new Hex(TerrainType.Jungle, 1));
        gameBoard.put(new Coordinate(1, -1), new Hex(TerrainType.Lake, 1));
        gameBoard.put(new Coordinate(0, 0), new Hex(TerrainType.Volcano, 1));
        gameBoard.put(new Coordinate(-1, 1), new Hex(TerrainType.Rocky, 1));
        gameBoard.put(new Coordinate(0, 1), new Hex(TerrainType.Grasslands, 1));
    }

    public void processParameters(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        locator.mainTerrainCoordinate = mainTerrainCoordinate;
        locator.orientation = terrainsOrientation;
        this.tile = tile;
        locator.updateXAndYCoordinateOfCurrentTerrain(mainTerrainCoordinate);
        locator.determineCoordinatesOfTerrainsNextToMainTerrainBasedOnTheirOrientation();
    }

    public void nuke() {
        int level = gameBoard.get(locator.mainTerrainCoordinate).getLevel();
        getDifferentSettlementIDsOfATile();
        placeTileOnMap();
        increaseLevel(level);
    }

    public Set<Integer> getDifferentSettlementIDsOfATile() {
        settlementIdsOfHexesInTile = new HashSet<>();
        if (terrainContainsAPiece(locator.leftOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(locator.leftOfMainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(locator.mainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(locator.mainTerrainCoordinate).getSettlementID());
        if (terrainContainsAPiece(locator.rightOfMainTerrainCoordinate))
            settlementIdsOfHexesInTile.add(gameBoard.get(locator.rightOfMainTerrainCoordinate).getSettlementID());

        return settlementIdsOfHexesInTile;
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


}