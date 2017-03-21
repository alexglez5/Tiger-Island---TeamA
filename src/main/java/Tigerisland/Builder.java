package Tigerisland;

/**
 * Created by Alexander Gonzalez on 3/21/2017.
 */
public class Builder extends GameBoard {
    public void foundNewSettlement(Coordinate coordinate){
        if(terrainIsNotTaken(coordinate) && terrainIsNotAVolcano(coordinate))
            gameBoard.get(coordinate).placeVillager();
    }

    private boolean terrainIsNotAVolcano(Coordinate coordinate){
        return gameBoard.get(coordinate).getTerrainType() != TerrainType.Volcano;
    }

    private boolean terrainIsNotTaken(Coordinate coordinate){
        return gameBoard.containsKey(coordinate);
    }
}
