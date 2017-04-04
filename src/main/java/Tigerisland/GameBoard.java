package Tigerisland;

import Tigerisland.PlayerActions.ActionHelper;
import Tigerisland.PlayerActions.Builder;
import Tigerisland.PlayerActions.TilePlacer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander Gonzalez on 3/17/2017.
 */

//TODO make settlements its own class and add funcitons to check whether it a totoro/tiger
public class GameBoard {
    protected static Map<Coordinate, Hex> gameBoard = new HashMap<>();
    protected static HashMap<Integer, Settlement> settlements = new HashMap<>();
    public static ActionHelper helper = new ActionHelper();
    public static TilePlacer placer = new TilePlacer();
    public static Builder builder = new Builder();
    protected static Player player;

    public GameBoard(){
//        settlements = new HashMap<>();
        player = new Player();
    }

    public Map<Coordinate, Hex> getBoard(){
        return this.gameBoard;
    }

    public HashMap<Integer, Settlement> getSettlements(){
        return this.settlements;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void updatePlayer(Player player){
        this.player = player;
    }

    public void placeStartingTile(){
        placer.placeOneStartingTile();
    }

    public void placeTile(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation) {
        placer.placeTile(tile, mainTerrainCoordinate, terrainsOrientation);
    }

    public void foundNewSettlement(Coordinate coordinate) {
        builder.foundNewSettlement(coordinate);
    }

    public void expandSettlement(Coordinate coordinateOfAnyHexInSettlement, TerrainType terrainType) {
        builder.expandSettlement(coordinateOfAnyHexInSettlement, terrainType);
    }

    public void placeTotoro(Coordinate coordinate) {
        builder.placeTotoro(coordinate);
    }

    public void placeTiger(Coordinate coordinate) {
        builder.placeTiger(coordinate);
    }

    public boolean tileCanBePlacedOnLevelOne(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return placer.tileCanBePlacedOnLevelOne();
    }

    public boolean tileCanNukeOtherTiles(Tile tile, Coordinate mainTerrainCoordinate, Orientation terrainsOrientation){
        placer.processParameters(tile, mainTerrainCoordinate, terrainsOrientation);
        return placer.tileCanNukeOtherTiles();
    }

    public boolean settlementCanBeFound(Coordinate coordinate){
        builder.processParameters(coordinate);
        return builder.settlementCanBeFound();
    }

    public boolean settlementCanBeExpanded(Coordinate coordinate, TerrainType terrainType) {
        builder.processParameters(coordinate, terrainType);
        return builder.settlementCanBeExpanded();
    }

    public boolean totoroCanBePlaced(Coordinate coordinate){
        builder.processParameters(coordinate);
        return builder.totoroCanBePlaced();
    }

    public boolean tigerCanBePlaced(Coordinate coordinate){
        builder.processParameters(coordinate);
        return builder.tigerCanBePlaced();
    }

    public boolean atLeastOneAdjacentSettlementDoesNotContainATiger(Coordinate coordinate){
        builder.processParameters(coordinate);
        return builder.atLeastOneAdjacentSettlementDoesNotContainATiger();
    }
    //TODO add acceptance test for placeTile on different levels
}
