package Tigerisland;

/**
 * Created by nathanbarnavon on 4/5/17.
 */
public class BuildValidator extends GameBoard {

    CoordinatesLocator locator = new CoordinatesLocator();








    private boolean thereIsAVillagerLeft(Player p) {
        return p.getNumberOfVillagersLeft() > 0;
    }

    public boolean thereIsATigerLeft(Player p) {
        return p.getNumberOfTigersLeft() > 0;
    }

    public boolean thereIsATotoroLeft(Player p) {
        return p.getNumberOfTotoroLeft() > 0;
    }

    public boolean onMap(Coordinate c) {
        return gameBoard.containsKey(c);
    }

    public boolean isOccupied(Coordinate c) {
        Hex check = gameBoard.get(c);
        return (check.hasTiger() || check.hasVillager() || check.hasTotoro());
    }

    public boolean isVolcano(Coordinate c) {
        return (gameBoard.get(c).getTerrainType() == TerrainType.VOLCANO);
    }

    public boolean levelIsAtLeastThree(Coordinate c) {
        return gameBoard.get(c).getLevel() > 2;
    }

}
