package Tigerisland.PlayerActions;

import Tigerisland.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Builder {
    protected ActionHelper locator = new ActionHelper();
    private final int pointsForTigerPlacement = 75;
    public HashSet<Coordinate> visitedCoordinates;
    protected Coordinate coordinate;
    protected TerrainType terrainType;
    protected int settlementID;
    public Set<Integer> differentSettlementIDsAroundCoordinate;
    protected int possiblePointsAdded;
    protected int possibleVillagersPlaced;
    protected Player player = new Player();
    protected HashMap<Coordinate, Hex> gameBoard = new HashMap<>();
    protected HashMap<Integer, Settlement> settlements = new HashMap<>();

    public void updtateComponents(ComponentsDTO dto) {
        this.gameBoard = dto.getGameBoard();
        this.settlements = dto.getSettlements();
        this.player = dto.getPlayer();
        this.locator = dto.getLocator();
    }

    public ComponentsDTO getComponents() {
        return new ComponentsDTO(this.gameBoard, this.settlements, this.player, this.locator);
    }

    public void foundNewSettlement() {
        foundNewSettlement(coordinate);
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    public void foundNewSettlement(Coordinate coordinate) {
        if(gameBoard.containsKey(coordinate))
            gameBoard.get(coordinate).placeVillagers();
        else
            gameBoard.put(coordinate, new Hex());
        gameBoard.get(coordinate).setSettlementID(settlementID);
        settlements.put(settlementID, new Settlement(coordinate));
        settlements.get(settlementID).setPlayerID(player.getPlayerID());
        player.addPoints(1);
        player.useVillagers(1);
    }

    private void mergeSettlementsThatCanBeMerged(Coordinate coordinate) {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        for (int id : differentSettlementIDsAroundCoordinate) {
            if (settlements.containsKey(settlementID) && settlements.get(id).hasTotoro())
                settlements.get(settlementID).placeTotoro();
            if (settlements.containsKey(settlementID) && settlements.get(id).hasTiger())
                settlements.get(settlementID).placeTiger();

            mergeSettlementsIntoASingleSettlement(id);
        }
    }

    public void getDifferentSettlementIDsAroundCoordinate(Coordinate coordinate) {
        locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinate);
        differentSettlementIDsAroundCoordinate = new HashSet<>();

        for (Coordinate neighborCoordinate : locator.surroundingCoordinates) {
            if (terrainContainsAPiece(neighborCoordinate)
                    && settlements.containsKey(gameBoard.get(neighborCoordinate).getSettlementID())
                    && settlements.get(gameBoard.get(neighborCoordinate).getSettlementID()).getPlayerID() == player.getPlayerID()) {
                differentSettlementIDsAroundCoordinate.add(gameBoard.get(neighborCoordinate).getSettlementID());
            }
        }
    }

    private void mergeSettlementsIntoASingleSettlement(int id) {
        if (id != settlementID && settlements.containsKey(id)) {
            for (Coordinate coordinatesToBeMoved : settlements.get(id).bfs()) {
                if(gameBoard.containsKey(coordinatesToBeMoved))
                    gameBoard.get(coordinatesToBeMoved).setSettlementID(settlementID);
                if(settlements.containsKey(settlementID))
                    settlements.get(settlementID).addToSettlement(coordinatesToBeMoved);
            }
            removeSettlementThatWasMerged(id);
        }
    }

    public boolean terrainContainsAPiece(Coordinate terrainCoordinate) {
        return gameBoard.containsKey(terrainCoordinate)
                && (gameBoard.get(terrainCoordinate).hasVillager()
                || gameBoard.get(terrainCoordinate).hasTotoro()
                || gameBoard.get(terrainCoordinate).hasTiger());
    }

    private void removeSettlementThatWasMerged(int id) {
        settlements.remove(id);
    }

    public void expandSettlement() {
        findCoordinatesOfPossibleSettlementExpansion();
        completeSettlementExpansion();
        mergeSettlementsThatCanBeMerged();
    }

    public void placeTotoro() {
        placeTotoroAtGivenCoordinate();
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    protected void findIdOfSettlementTotoroCouldBeAdjacentTo() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        for (int id : differentSettlementIDsAroundCoordinate) {
            if (settlements.containsKey(id) && settlements.get(id).getSize() >= 5
                    && !settlements.get(id).hasTotoro()) {
                setSettlementID(id);
                return;
            }
        }
        setSettlementID(-1);
    }

    public void findIdOfSettlementTigerCouldBeAdjacentTo() {
        getDifferentSettlementIDsAroundCoordinate(coordinate);
        for (int id : differentSettlementIDsAroundCoordinate) {
            if (settlements.containsKey(id) && !settlements.get(id).hasTiger()) {
                setSettlementID(id);
                return;
            }
        }
        setSettlementID(-1);
    }

    public void placeTiger() {
        placeTigerAtGivenCoordinate();
        mergeSettlementsThatCanBeMerged(coordinate);
    }

    private void placeTigerAtGivenCoordinate() {
        findIdOfSettlementTigerCouldBeAdjacentTo();
        if(gameBoard.containsKey(coordinate))
            gameBoard.get(coordinate).placeTiger();
        else
            gameBoard.put(coordinate, new Hex());
        gameBoard.get(coordinate).setSettlementID(settlementID);
        if(settlements.containsKey(settlementID))
            settlements.get(settlementID).addToSettlement(coordinate);
        else
            settlements.put(settlementID, new Settlement(coordinate));

        settlements.get(settlementID).placeTiger();
        settlements.get(settlementID).setPlayerID(player.getPlayerID());
        player.addPoints(pointsForTigerPlacement);
        player.useTiger();
    }

    private void placeTotoroAtGivenCoordinate() {
        findIdOfSettlementTotoroCouldBeAdjacentTo();
        if(gameBoard.containsKey(coordinate))
            gameBoard.get(coordinate).placeTotoro();
        else
            gameBoard.put(coordinate, new Hex());
        gameBoard.get(coordinate).setSettlementID(settlementID);

        if(settlements.containsKey(settlementID))
            settlements.get(settlementID).addToSettlement(coordinate);
        else
            settlements.put(settlementID, new Settlement(coordinate));
        settlements.get(settlementID).placeTotoro();
        settlements.get(settlementID).setPlayerID(player.getPlayerID());
        player.useTotoro();
        player.addPoints(200);
    }

    public void processParameters(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.settlementID = coordinate.hashCode();
    }

    public void processParameters(Coordinate coordinate, TerrainType terrainType) {
        if(gameBoard.containsKey(coordinate))
            this.settlementID = gameBoard.get(coordinate).getSettlementID();
        this.terrainType = terrainType;
        possibleVillagersPlaced = 0;
        possiblePointsAdded = 0;
    }

    public void findCoordinatesOfPossibleSettlementExpansion() {
        visitedCoordinates = new HashSet<>();
        expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType();
    }

    private void completeSettlementExpansion() {
        for (Coordinate coordinateToExpand : visitedCoordinates) {
            if(gameBoard.containsKey(coordinateToExpand)){
                gameBoard.get(coordinateToExpand).placeVillagers();
                gameBoard.get(coordinateToExpand).setSettlementID(settlementID);
            }
            if(settlements.containsKey(settlementID))
                settlements.get(settlementID).addToSettlement(coordinateToExpand);
        }
        if(settlements.containsKey(settlementID))
            settlements.get(settlementID).setPlayerID(player.getPlayerID());
        player.useVillagers(possibleVillagersPlaced);
        player.addPoints(possiblePointsAdded);
    }

    private void mergeSettlementsThatCanBeMerged() {
        for (Coordinate visitedCoordinate : visitedCoordinates)
            mergeSettlementsThatCanBeMerged(visitedCoordinate);
    }

    private void expandToAllEmptyAdjacentToSettlementSpacesOfTheSpecifiedType() {
        if(settlements.containsKey(settlementID)){
            for (Coordinate coordinateInSettlement : settlements.get(settlementID).bfs()) {
                locator.findCounterClockwiseCoordinatesAroundCoordinate(coordinateInSettlement);
                expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
            }
        }
    }

    private void expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited() {
        for (Coordinate neighborCoordinate : locator.surroundingCoordinates)
            if (adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(neighborCoordinate))
                expandAsLongAsThereIsAnAdjacentTerrainOfTheSameTipe(neighborCoordinate);
    }

    private boolean adjacentTerrainIsAsTheSameTypeAndHasNotBeenVisited(Coordinate neighborCoordinate) {
        return gameBoard.containsKey(neighborCoordinate)
                && gameBoard.get(neighborCoordinate).getTerrainType() == terrainType
                && !visitedCoordinates.contains(neighborCoordinate)
                && !gameBoard.get(neighborCoordinate).hasPiece();
    }

    private void expandAsLongAsThereIsAnAdjacentTerrainOfTheSameTipe(Coordinate neighborCoordinate) {
        markCoordinateAsVisited(neighborCoordinate);
        locator.findCounterClockwiseCoordinatesAroundCoordinate(neighborCoordinate);
        expandToAnyOfTheCoordinatesThatHaveTheSameTypeAndHasNotBeenVisited();
    }

    private void markCoordinateAsVisited(Coordinate neighborCoordinate) {
        visitedCoordinates.add(neighborCoordinate);
        updateScoreAndPlayersInventory(neighborCoordinate);
    }

    private void updateScoreAndPlayersInventory(Coordinate neighborCoordinate) {
        int level = gameBoard.get(neighborCoordinate).getLevel();
        possiblePointsAdded += level * level;
        possibleVillagersPlaced += level;
    }

    public void setSettlementID(int settlementID){
        this.settlementID = settlementID;
    }
}