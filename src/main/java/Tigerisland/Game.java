package Tigerisland;

import Tigerisland.PlayerActions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Game {
    public ActionHelper locator = new ActionHelper();
    private TilePlacer placer = new TilePlacer();
    private TilePlacementValidator tileValidator = new TilePlacementValidator();
    public Builder builder = new Builder();
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

    public int getCurrentPlayerId(){
        return currentPlayerId;
    }

    public void setCurrentPlayer(int pid) {
        currentPlayerId = pid;
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
        updateLocator(dto.getLocator());
    }

    private void updateLocator(ActionHelper locator) {
        this.locator = locator;
    }

    public ComponentsDTO getComponents(){
        return new ComponentsDTO(this.gameBoard, this.settlements, this.getPlayer(), this.locator);
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

    public void printGameBoard(){
       /* System.out.print("GameBoard size: " + getBoard().size() + " ==> ");
        for(Coordinate c : getBoard().keySet()){
            System.out.print(c.getXCoordinate() + ", " + c.getYCoordinate() + getBoard().get(c).hasVillager() +" | " );
       }
        System.out.println("p1" + player1.getNumberOfVillagersLeft() + "\n");
        System.out.println("p1" + player1.getNumberOfVillagersLeft() + "\n");*/
    }

    public Coordinate getAnyCoordinateOfSameTerrainTypeInSettlement(int settlementID, TerrainType terrainType){
        for(Coordinate c : getSettlements().get(settlementID).bfs()){
            if(getBoard().get(c).getTerrainType() == terrainType)
                return c;
        }
        return new Coordinate(-100,-100);
    }

    public ArrayList<TerrainType> getDifferentTerrainTypesInSettlement(int id){
        ArrayList<TerrainType> types = new ArrayList<>();
        for(Coordinate coordinate : getSettlements().get(id).bfs())
            if(!types.contains(getBoard().get(coordinate).getTerrainType()))
                types.add(getBoard().get(coordinate).getTerrainType());
        return types;
    }

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.updtateComponents(this.getComponents());
        tileValidator.updtateComponents(this.getComponents());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        tileValidator.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);

        if (tileValidator.tileCanBePlacedOnLevelOne())
            placer.placeTileOnMap();
        else if (tileValidator.tileCanNukeOtherTiles()) {
            placer.nuke();
        }
        this.updateComponents(tileValidator.getComponents());
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
        tileValidator.updtateComponents(this.getComponents());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        tileValidator.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanBePlacedOnLevelOne();
    }

    public boolean tileCanNukeOtherTiles(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.updtateComponents(this.getComponents());
        tileValidator.updtateComponents(this.getComponents());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        tileValidator.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanNukeOtherTiles();
    }

    public boolean tileCanBePlaced(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.updtateComponents(this.getComponents());
        tileValidator.updtateComponents(this.getComponents());
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        tileValidator.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return tileValidator.tileCanNukeOtherTiles() || tileValidator.tileCanBePlacedOnLevelOne();
    }


    public boolean settlementCanBeFound(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        buildValidator.processParameters(coordinate);
        return buildValidator.settlementCanBeFound();
    }

    public boolean settlementCanBeExpanded(Coordinate coordinate, TerrainType terrainType) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinate, terrainType);
        buildValidator.processParameters(coordinate, terrainType);
        return buildValidator.settlementCanBeExpanded();
    }

    public boolean totoroCanBePlaced(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        buildValidator.processParameters(coordinate);
        return buildValidator.totoroCanBePlaced();
    }

    public boolean tigerCanBePlaced(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        buildValidator.processParameters(coordinate);
        return buildValidator.tigerCanBePlaced();
    }

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger(Coordinate coordinate) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinate);
        buildValidator.processParameters(coordinate);
        return buildValidator.atLeastOneAdjacentSettlementDoesNotContainATiger();
    }

    public HashSet<Coordinate> getCoordinatesOfPossibleSettlementExpansion(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.updtateComponents(this.getComponents());
        buildValidator.updtateComponents(this.getComponents());
        builder.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        buildValidator.processParameters(coordinateOfAnyHexInSettlement, terrainType);
        builder.findCoordinatesOfPossibleSettlementExpansion();
        return builder.visitedCoordinates;
    }

    public void splitSettlements() {
        placer.updtateComponents(this.getComponents());
        placer.splitSettlements();
    }

    public boolean isSettlementSplit(Settlement s) {
        placer.updtateComponents(getComponents());
        tileValidator.updtateComponents(getComponents());
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
