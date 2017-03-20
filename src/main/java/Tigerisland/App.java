package Tigerisland;

/**
 * Hello world!
 *
 */
public class App {
    GameBoard map = new GameBoard();
    Tile newTile = new Tile(TerrainType.Grasslands, TerrainType.Jungle);
    Coordinate newCord = new Coordinate(0,0);
    Orientation orientation = Orientation.FromBottom;

    public static void main( String[] args ) {
        System.out.println("random change");
    }

    public App() {
    }

    public void placeTile(int ID) {
        newTile.setTileID(ID);
        map.placeTile(new Tile(TerrainType.Lake, TerrainType.Rocky), new Coordinate(0,0), Orientation.FromTop);
    }
}
