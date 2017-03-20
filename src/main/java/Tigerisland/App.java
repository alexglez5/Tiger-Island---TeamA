package Tigerisland;

/**
 * Hello world!
 *
 */
public class App {
    GameBoard map = new GameBoard();
    Tile newTile = new Tile(TerrainType.Grasslands, TerrainType.Jungle, 1);
    Coordinate newCord = new Coordinate(0,0);
    Orientation orientation = Orientation.FromBottom;

    public static void main( String[] args ) {
        System.out.println("random change");
    }

    public App() {
    }

    public void placeTile(int ID) {
        newTile.setTileID(ID);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky, 1), new Coordinate(0,0), Orientation.FromTop);
    }

    public boolean checkTile(int coordinate1, int coordinate2) {
        Coordinate cord = new Coordinate ( coordinate1, coordinate2);
        return map.gameBoard.containsKey(cord);
    }
}
