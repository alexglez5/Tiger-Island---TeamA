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
    Player player1 = new Player();

    public static void main( String[] args ) {
        System.out.println("random change");
    }

    public App() {
    }

    public void placeTile(String orientaiton, int ID, int xcoordinate, int ycoordinate) {
        isBoardEmpty = false;
        newTile.setTileID(ID);
        map.placeTile(
                player1.currentTile,
                new Coordinate(xcoordinate,ycoordinate),
                checkOrientation(orientaiton)
        );
        player1.removeCurrentTile();
    }

    public boolean doesTileExist(int coordinate1, int coordinate2) {
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

    public TerrainType checkTerrain(String terrain) {
        if(orientation.equals("Jungle")){
            return TerrainType.Jungle;
        }
        else if(orientation.equals("Rocky")) {
            return TerrainType.Rocky;
        }
        else if(orientation.equals("Lake")) {
            return TerrainType.Lake;
        }
        else if(orientation.equals("Grassland")) {
            return TerrainType.Grasslands;
        }
        else if(orientation.equals("Volcano")) {
            return TerrainType.Volcano;
        }
        else {
            System.out.println("Invalid Terrain");
            return null;
        }
    }

    public void givePlayerTile(String terrain1, String terrain2) {
        Tile currentTile = new Tile(checkTerrain(terrain1), checkTerrain(terrain2),1);
        player1.grantTile(currentTile);
    }

    public boolean isEmptyBoard(){return isBoardEmpty;}
}
