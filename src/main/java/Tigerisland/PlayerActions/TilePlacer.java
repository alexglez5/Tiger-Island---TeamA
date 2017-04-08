package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TilePlacer {
    protected static ActionHelper locator = new ActionHelper();
    protected Set<Integer> settlementIdsOfHexesUnderTile;
    protected HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    protected Player player;
    protected HashMap<Integer, Settlement> settlements;
    private Tile tile;

    public void updtateComponents(ComponentsDTO dto) {
        this.gameBoard = dto.getGameBoard();
        this.settlements = dto.getSettlements();
        this.player = dto.getPlayer();
    }

    public ComponentsDTO getComponents() {
        return new ComponentsDTO(this.gameBoard, this.settlements, this.getPlayer());
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HashMap<Integer, Settlement> getSettlements() {
        return this.settlements;
    }

    public void setSettlements(HashMap<Integer, Settlement> settlements) {
        this.settlements = settlements;
    }

    public void placeOneStartingTile() {
        gameBoard.put(new Coordinate(0, -1), new Hex(TerrainType.JUNGLE, 1));
        gameBoard.put(new Coordinate(1, -1), new Hex(TerrainType.LAKE, 1));
        gameBoard.put(new Coordinate(0, 0), new Hex(TerrainType.VOLCANO, 1));
        gameBoard.put(new Coordinate(-1, 1), new Hex(TerrainType.ROCKY, 1));
        gameBoard.put(new Coordinate(0, 1), new Hex(TerrainType.GRASSLANDS, 1));
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
        removeFromSettlement();
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
            if (settlements.get(sid).contains(locator.leftOfMainTerrainCoordinate))
                settlements.get(sid).removeFromSettlement(locator.leftOfMainTerrainCoordinate);
            if (settlements.get(sid).contains(locator.rightOfMainTerrainCoordinate))
                settlements.get(sid).removeFromSettlement(locator.rightOfMainTerrainCoordinate);
        }
    }
}