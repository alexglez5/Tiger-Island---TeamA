package Tigerisland;

import Tigerisland.PlayerActions.*;

import java.util.HashMap;

public class Game {
    public ActionHelper locator = new ActionHelper();
    private TilePlacer placer = new TilePlacer();
    private TilePlacementValidator tileValidator = new TilePlacementValidator();
    private Builder builder = new Builder();
    private BuildValidator buildValidator = new BuildValidator();
    private HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    private HashMap<Integer, Settlement> settlements = new HashMap<>();
    private Player player1 = new Player();
    private Player player2 = new Player();
    private int currentPlayerId = 1;

    public Game() {
        player1.setPlayerID(1);
        player2.setPlayerID(2);
    }

    public void setCurrentPlayer(int pid) throws Exception {
        if (pid == 1 || pid == 2)
            currentPlayerId = pid;
        else
            throw new Exception("Wrong player ID");
    }

    public void switchPlayers() {
        if (currentPlayerId == 1)
            currentPlayerId = 2;
        else
            currentPlayerId = 1;
    }

    public HashMap<Integer, Settlement> getSettlements() {
        return this.settlements;
    }

    public HashMap<Coordinate, Hex> getBoard() {
        return this.gameBoard;
    }

    public void placeStartingTile() {
        placer.setGameBoard(gameBoard);
        placer.setSettlements(settlements);
        placer.setPlayer(getPlayer());
        placer.placeOneStartingTile();
        updateGameBoard(placer.getGameBoard());
        updateSettlements(placer.getSettlements());
        updatePlayer(placer.getPlayer());
    }

    public Player getPlayer() {
        if (currentPlayerId == 1)
            return player1;
        else
            return player2;
    }

    public void updateGameBoard(HashMap<Coordinate, Hex> gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void updateSettlements(HashMap<Integer, Settlement> settlements) {
        this.settlements = settlements;
    }

    public void updatePlayer(Player player) {
        if (currentPlayerId == 1)
            player1 = player;
        else
            player2 = player;
    }

    // todo: Let's separate the validation and actual placing of tile in gameboard
    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.setGameBoard(gameBoard);
        placer.setPlayer(getPlayer());
        placer.setSettlements(settlements);

        tileValidator.setGameBoard(gameBoard);
        tileValidator.setPlayer(getPlayer());
        tileValidator.setSettlements(settlements);
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        if (tileValidator.tileCanBePlacedOnLevelOne())
            placer.placeTileOnMap();
        else if (tileValidator.tileCanNukeOtherTiles())
            placer.nuke();
        updateGameBoard(placer.getGameBoard());
        updateSettlements(placer.getSettlements());
        updatePlayer(placer.getPlayer());
    }

    public void foundNewSettlement(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinate);
        builder.foundNewSettlement();
        updateGameBoard(builder.getGameBoard());
        updateSettlements(builder.getSettlements());
        updatePlayer(builder.getPlayer());
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.expandSettlement();
        updateGameBoard(builder.getGameBoard());
        updateSettlements(builder.getSettlements());
        updatePlayer(builder.getPlayer());
    }

    public void placeTotoro(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setSettlements(settlements);
        updateSettlements(builder.getSettlements());
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinate);
        builder.placeTotoro();
        updateGameBoard(builder.getGameBoard());
        updateSettlements(builder.getSettlements());
        updatePlayer(builder.getPlayer());
    }

    public void placeTiger(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinate);
        builder.placeTiger();
        updateGameBoard(builder.getGameBoard());
        updateSettlements(builder.getSettlements());
        updatePlayer(builder.getPlayer());
    }

    public boolean tileCanBePlacedOnLevelOne(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.setGameBoard(gameBoard);
        placer.setSettlements(settlements);
        placer.setPlayer(getPlayer());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanBePlacedOnLevelOne();
    }

    public boolean tileCanNukeOtherTiles(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.setGameBoard(gameBoard);
        placer.setSettlements(settlements);
        placer.setPlayer(getPlayer());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanNukeOtherTiles();
    }

    public boolean settlementCanBeFound(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinate);
        return buildValidator.settlementCanBeFound();
    }

    public boolean settlementCanBeExpanded(Coordinate coordinate, TerrainType terrainType) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinate, terrainType);
        return buildValidator.settlementCanBeExpanded();
    }

    public boolean totoroCanBePlaced(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinate);
        return buildValidator.totoroCanBePlaced();
    }

    public boolean tigerCanBePlaced(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinate);
        return buildValidator.tigerCanBePlaced();
    }

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger(Coordinate coordinate) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinate);
        return buildValidator.atLeastOneAdjacentSettlementDoesNotContainATiger();
    }

    public void findCoordinatesOfPossibleSettlementExpansion(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.setGameBoard(gameBoard);
        builder.setSettlements(settlements);
        builder.setPlayer(getPlayer());
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.findCoordinatesOfPossibleSettlementExpansion();
        updateGameBoard(builder.getGameBoard());
    }



    public void resetGame() {
        gameBoard.clear();
        player1.resetScoreAndInventory();
        player2.resetScoreAndInventory();
        settlements.clear();
    }
    //TODO add acceptance test for placeTile on different levels
}
