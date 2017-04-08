package Tigerisland;

import Tigerisland.PlayerActions.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
//    public AIHelper helper = new AIHelper();
    public ActionHelper locator = new ActionHelper();
    private TilePlacer placer = new TilePlacer();
    private TilePlacementValidator tileValidator = new TilePlacementValidator();
    private Builder builder = new Builder();
    private BuildValidator buildValidator = new BuildValidator();
    HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
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

    public void updateComponents(ComponentsDTO dto){
        updateGameBoard(dto.getGameBoard());
        updateSettlements(dto.getSettlements());
        updatePlayer(dto.getPlayer());
    }

    public ComponentsDTO getComponents(){
        return new ComponentsDTO(this.gameBoard, this.settlements, this.getPlayer());
    }

    public void placeStartingTile() {
        placer.updtateComponents(this.getComponents());
        placer.placeOneStartingTile();
        this.updateComponents(placer.getComponents());
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
        placer.updtateComponents(this.getComponents());
        tileValidator.updtateComponents(this.getComponents());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);

        if (tileValidator.tileCanBePlacedOnLevelOne())
            placer.placeTileOnMap();
        else if (tileValidator.tileCanNukeOtherTiles())
            placer.nuke();
        this.updateComponents(placer.getComponents());
    }

    public void foundNewSettlement(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        builder.foundNewSettlement();
        this.updateComponents(builder.getComponents());
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.updtateComponents(this.getComponents());
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.expandSettlement();
        this.updateComponents(builder.getComponents());
    }

    public void placeTotoro(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        builder.placeTotoro();
        this.updateComponents(builder.getComponents());
    }

    public void placeTiger(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        builder.placeTiger();
        this.updateComponents(builder.getComponents());
    }

    public boolean tileCanBePlacedOnLevelOne(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.updtateComponents(this.getComponents());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanBePlacedOnLevelOne();
    }

    public boolean tileCanNukeOtherTiles(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanNukeOtherTiles();
    }

    public boolean settlementCanBeFound(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        return buildValidator.settlementCanBeFound();
    }

    public boolean settlementCanBeExpanded(Coordinate coordinate, TerrainType terrainType) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinate, terrainType);
        return buildValidator.settlementCanBeExpanded();
    }

    public boolean totoroCanBePlaced(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        return buildValidator.totoroCanBePlaced();
    }

    public boolean tigerCanBePlaced(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        return buildValidator.tigerCanBePlaced();
    }

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        return buildValidator.atLeastOneAdjacentSettlementDoesNotContainATiger();
    }

    public void findCoordinatesOfPossibleSettlementExpansion(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.updtateComponents(this.getComponents());
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.findCoordinatesOfPossibleSettlementExpansion();
    }

    public boolean isSettlementSplit(Settlement s) {
        return tileValidator.checkForSplit(s);
    }

    public void resetGame() {
        gameBoard.clear();
        player1.resetScoreAndInventory();
        player2.resetScoreAndInventory();
        settlements.clear();
    }
    //TODO add acceptance test for placeTile on different levels
}
