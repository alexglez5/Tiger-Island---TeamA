package Tigerisland;

import com.sun.org.apache.xpath.internal.operations.Or;

/**
 * Hello world!
 *
 */
public class App {
    Boolean isBoardEmpty = true;
    GameBoard map = new GameBoard();
    Player player1 = new Player();
    int currentTurnNumber = 1;

    public static void main( String[] args ) {
        System.out.println("random change");
    }

    public App() {
    }

    public void placeTile(String orientaiton, int ID, int xcoordinate, int ycoordinate) {
        isBoardEmpty = false;
        player1.getCurrentTile().setTileID(ID);
        map.placeTile(
                player1.getCurrentTile(),
                new Coordinate(xcoordinate,ycoordinate),
                checkOrientation(orientaiton)
        );
        currentTurnNumber++;
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
        if(terrain.equals("Jungle")){
            return TerrainType.Jungle;
        }
        else if(terrain.equals("Rocky")) {
            return TerrainType.Rocky;
        }
        else if(terrain.equals("Lake")) {
            return TerrainType.Lake;
        }
        else if(terrain.equals("Grassland")) {
            return TerrainType.Grasslands;
        }
        else if(terrain.equals("Volcano")) {
            return TerrainType.Volcano;
        }
        else {
            System.out.println("Invalid Terrain");
            return null;
        }
    }

    public void givePlayerTile(String terrain1, String terrain2, int ID) {
        Tile currentTile = new Tile(checkTerrain(terrain1), checkTerrain(terrain2), ID);
        player1.grantTile(currentTile);
    }

    public boolean isEmptyBoard(){return isBoardEmpty;}

    public void buildVillager(int x, int y) {map.foundNewSettlement(new Coordinate(x,y));}

    public boolean isCorrectTerrain(int x, int y, String correctTerrain) {
        TerrainType correctTerrainType = checkTerrain(correctTerrain);
        return correctTerrainType.equals(map.gameBoard.get(new Coordinate(x,y)).getTerrainType());
    }

    public int returnLevel(int coordinatex, int coordinatey) {
        Coordinate coordinate = new Coordinate ( coordinatex, coordinatey);
        return map.gameBoard.get(coordinate).getLevel();
    }

    public boolean checkVillagers(int coordinateX, int coordinateY) {
        Coordinate coordinate = new Coordinate ( coordinateX, coordinateY);
        return map.gameBoard.get(coordinate).hasVillager();
    }
}
