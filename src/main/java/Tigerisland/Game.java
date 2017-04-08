package Tigerisland;

import Tigerisland.PlayerActions.*;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private static TilePlacer placer = new TilePlacer();
    public static ActionHelper locator = new ActionHelper();
    private static TilePlacementValidator tileValidator = new TilePlacementValidator();
    private static Builder builder = new Builder();
    private static BuildValidator buildValidator = new BuildValidator();
    private HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    private static Player currentPlayer = new Player();

    public void updateGameBoard(HashMap<Coordinate, Hex> gameBoard){
        this.gameBoard = gameBoard;
    }

    public HashMap<Coordinate, Hex> getBoard() {
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
        placer.setGameBoard(gameBoard);
        placer.placeOneStartingTile();
        updateGameBoard(placer.getGameBoard());
    }

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.setGameBoard(gameBoard);
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        if (tileValidator.tileCanBePlacedOnLevelOne())
            placer.placeTileOnMap();
        else if (tileValidator.tileCanNukeOtherTiles())
            placer.nuke();
        updateGameBoard(placer.getGameBoard());
    }

    public void foundNewSettlement(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinate);
        builder.foundNewSettlement();
        updateGameBoard(builder.getGameBoard());
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.expandSettlement();
        updateGameBoard(builder.getGameBoard());
    }

    public void placeTotoro(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinate);
        builder.placeTotoro();
        updateGameBoard(builder.getGameBoard());
    }

    public void placeTiger(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinate);
        builder.placeTiger();
        updateGameBoard(builder.getGameBoard());
    }

    public boolean tileCanBePlacedOnLevelOne(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.setGameBoard(gameBoard);
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanBePlacedOnLevelOne();
    }

    public boolean tileCanNukeOtherTiles(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.setGameBoard(gameBoard);
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanNukeOtherTiles();
    }

    public boolean settlementCanBeFound(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinate);
        return buildValidator.settlementCanBeFound();
    }

    public boolean settlementCanBeExpanded(Coordinate coordinate, TerrainType terrainType) {
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinate, terrainType);
        return buildValidator.settlementCanBeExpanded();
    }

    public boolean totoroCanBePlaced(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinate);
        return buildValidator.totoroCanBePlaced();
    }

    public boolean tigerCanBePlaced(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinate);
        return buildValidator.tigerCanBePlaced();
    }

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
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
        builder.setGameBoard(gameBoard);
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.findCoordinatesOfPossibleSettlementExpansion();
        updateGameBoard(builder.getGameBoard());
    }

    //TODO add acceptance test for placeTile on different levels
}
