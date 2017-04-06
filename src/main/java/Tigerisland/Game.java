package Tigerisland;

import Tigerisland.PlayerActions.*;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private static TilePlacer placer = new TilePlacer();
    protected static ActionHelper helper = new ActionHelper();
    private static TilePlacementValidator tileValidator = new TilePlacementValidator();
    private static Builder builder = new Builder();
    private static BuildValidator buildValidator = new BuildValidator();
    public static Map<Coordinate, Hex> gameBoard = new HashMap<>();
    private static Player currentPlayer = new Player();
//    private static Player player1;
//    private static Player player2;

//    public Game() {
//        player1 = new Player();
//        player1.setPlayerID(1);
//        player2 = new Player();
//        player2.setPlayerID(2);
//        updatePlayer(player1.getPlayerID());
//    }

    public Map<Coordinate, Hex> getBoard() {
        return this.gameBoard;
    }

    public Player getPlayer() {
        return currentPlayer;
    }

//    public void updatePlayer(int pid) {
//        if (player1.getPlayerID() == pid)
//            currentPlayer = player1;
//        else
//            currentPlayer = player2;
//    }

    public void placeStartingTile() {
        placer.placeOneStartingTile();
    }

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        if (tileValidator.tileCanBePlacedOnLevelOne()) {
            placer.placeTileOnMap();
        }
        else if (tileValidator.tileCanNukeOtherTiles()) {
            placer.nuke();
        }
    }

    public void foundNewSettlement(Coordinate coordinate) {
        builder.processParameters(coordinate);
        builder.foundNewSettlement();
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.expandSettlement();
    }

    public void placeTotoro(Coordinate coordinate) {
        builder.processParameters(coordinate);
        builder.placeTotoro();
    }

    public void placeTiger(Coordinate coordinate) {
        builder.processParameters(coordinate);
        builder.placeTiger();
    }

    public boolean tileCanBePlacedOnLevelOne(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanBePlacedOnLevelOne();
    }

    public boolean tileCanNukeOtherTiles(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanNukeOtherTiles();
    }

    public boolean settlementCanBeFound(Coordinate coordinate) {
        builder.processParameters(coordinate);
        return buildValidator.settlementCanBeFound();
    }

    public boolean settlementCanBeExpanded(Coordinate coordinate, TerrainType terrainType) {
        builder.processParameters(coordinate, terrainType);
        return buildValidator.settlementCanBeExpanded();
    }

    public boolean totoroCanBePlaced(Coordinate coordinate) {
        builder.processParameters(coordinate);
        return buildValidator.totoroCanBePlaced();
    }

    public boolean tigerCanBePlaced(Coordinate coordinate) {
        builder.processParameters(coordinate);
        return buildValidator.tigerCanBePlaced();
    }

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger(Coordinate coordinate) {
        builder.processParameters(coordinate);
        return buildValidator.atLeastOneAdjacentSettlementDoesNotContainATiger();
    }

    public boolean terrainContainsAPiece(Coordinate terrainCoordinate) {
        return gameBoard.containsKey(terrainCoordinate)
                && (gameBoard.get(terrainCoordinate).hasVillager()
                || gameBoard.get(terrainCoordinate).hasTotoro()
                || gameBoard.get(terrainCoordinate).hasTiger());
    }

    public void findCoordinatesOfPossibleSettlementExpansion(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.findCoordinatesOfPossibleSettlementExpansion();
    }

    //TODO add acceptance test for placeTile on different levels
}
