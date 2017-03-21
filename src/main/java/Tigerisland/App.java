package Tigerisland;

import com.sun.org.apache.xpath.internal.operations.Or;

/**
 * Hello world!
 *
 */
public class App {
    Boolean isBoardEmpty = true;
    GameBoard map = new GameBoard();
    Tile newTile = new Tile(TerrainType.Grasslands, TerrainType.Jungle, 1);
    Coordinate newCord = new Coordinate(0,0);
    Orientation orientation = Orientation.FromBottom;

    public static void main( String[] args ) {
        System.out.println("random change");
    }

    public App() {
    }

    public void placeTile(String orientaiton, int ID, int xcoordinate, int ycoordinate) {
        isBoardEmpty = false;
        newTile.setTileID(ID);
        map.placeTile(
                new Tile(TerrainType.Lake, TerrainType.Rocky, 1),
                new Coordinate(xcoordinate,ycoordinate),
                checkOrientation(orientaiton)
        );
    }

    public boolean checkTile(int coordinate1, int coordinate2) {
        System.out.println(coordinate1);
        System.out.println(coordinate2);
        Coordinate cord = new Coordinate ( coordinate1, coordinate2);
        return map.gameBoard.containsKey(cord);
    }

    public Orientation checkOrientation(String orientation) {
        if(orientation.equals("FromBottom")){
            return Orientation.FromBottom;
        }
        else if(orientation.equals("FromTop")) {
            return Orientation.FromTop;
        }
        else if(orientation.equals("FromTopLeft")) {
            return Orientation.FromTopLeft;
        }
        else if(orientation.equals("FromTopRight")) {
            return Orientation.FromTopRight;
        }
        else if(orientation.equals("FromBottomLeft")) {
            return Orientation.FromBottomLeft;
        }
        else if(orientation.equals("FromBottomRight")) {
            return Orientation.FromBottomRight;
        }
        else {
            System.out.println("Invalid Orientation");
            return null;
        }
    }

    public boolean isEmptyBoard(){return isBoardEmpty;}
}
