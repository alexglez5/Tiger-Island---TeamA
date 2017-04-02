package Tigerisland;

/**
 * Hello world!
 */
public class App {
    Boolean isBoardEmpty = true;
    GameBoard map = new GameBoard();
    Player player1 = new Player();
    int currentTurnNumber = 1;

    public App() {
    }

    public static void main(String[] args) {
        System.out.println("random change");
    }

    public void placeTile(String orientation, int ID, int xcoordinate, int ycoordinate) {
        isBoardEmpty = false;
        player1.getCurrentTile().setTileID(ID);
        map.placeTile(
                player1.getCurrentTile(),
                new Coordinate(xcoordinate,ycoordinate),
                checkOrientation(orientation)
        );
        currentTurnNumber++;
        player1.removeCurrentTile();
    }

    public Orientation checkOrientation(String orientation) {
        if (orientation.equals("FromBottom")) {
            return Orientation.FromBottom;
        } else if (orientation.equals("FromTop")) {
            return Orientation.FromTop;
        } else if (orientation.equals("FromTopLeft")) {
            return Orientation.FromTopLeft;
        } else if (orientation.equals("FromTopRight")) {
            return Orientation.FromTopRight;
        } else if (orientation.equals("FromBottomLeft")) {
            return Orientation.FromBottomLeft;
        } else if (orientation.equals("FromBottomRight")) {
            return Orientation.FromBottomRight;
        } else {
            System.out.println("Invalid Orientation");
            return null;
        }
    }

    public boolean doesTileExist(int coordinate1, int coordinate2) {
        Coordinate cord = new Coordinate(coordinate1, coordinate2);
        return map.gameBoard.containsKey(cord);
    }

    public PlayerChoice enumPlayerChoice(String choice) {
        if (choice.equals("Found Settlement")) {
            return PlayerChoice.foundSettlement;
        } else if (choice.equals("Expand Settlement")) {
            return PlayerChoice.expandSettlement;
        } else if (choice.equals("Place Totoro")) {
            return PlayerChoice.placeTotoro;
        } else if (choice.equals("Place Tiger")) {
            return PlayerChoice.placeTiger;
        } else {
            System.out.println("Invalid Player Choice");
            return null;
        }

    }

    public void givePlayerTile(String terrain1, String terrain2) {
        Tile currentTile = new Tile(checkTerrain(terrain1), checkTerrain(terrain2));
        player1.grantTile(currentTile);
    }

    public TerrainType checkTerrain(String terrain) {

        if(terrain.equals("JUNGLE") || terrain.equals("jungle") ){
            return TerrainType.JUNGLE;
        }
        else if(terrain.equals("ROCK") || terrain.equals("rocky") ) {
            return TerrainType.ROCK;
        }
        else if(terrain.equals("LAKE") || terrain.equals("lake")
                || terrain.equals("Lakes") || terrain.equals("lakes")) {
            return TerrainType.LAKE;
        }
        else if(terrain.equals("Grassland") || terrain.equals("grassland")
                || terrain.equals("grasslands") || terrain.equals("GRASS")) {
            return TerrainType.GRASS;
        }
        else if(terrain.equals("VOLCANO") || terrain.equals("volcano") ) {
            return TerrainType.VOLCANO;
        }
        else {
            System.out.println("Invalid Terrain");
            return null;
        }
    }

    public boolean isEmptyBoard() {
        return isBoardEmpty;
    }

    public void buildVillager(int x, int y) {
        map.foundNewSettlement(new Coordinate(x, y));
    }

    public boolean isCorrectTerrain(int x, int y, String correctTerrain) {
        TerrainType correctTerrainType = checkTerrain(correctTerrain);
        Coordinate toCheck = new Coordinate(x,y);
        Hex hexToCheck = map.gameBoard.get(toCheck);
        return correctTerrainType.equals(hexToCheck.getTerrainType());

    }

    public int returnLevel(int coordinatex, int coordinatey) {
        Coordinate coordinate = new Coordinate(coordinatex, coordinatey);
        return map.gameBoard.get(coordinate).getLevel();
    }

    public boolean checkVillagers(int coordinateX, int coordinateY) {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        return map.gameBoard.get(coordinate).hasVillager();
    }

    public void givePlayerChoice(String choice) {
        player1.giveChoice(choice);
    }

    public boolean checkPlayerChoice(String choice) {
        return player1.checkPlayerChoice().equals(choice);
    }

    public void expandSettlementAt(int coordinateX, int coordinateY, String terrain) {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        map.expandSettlement(coordinate, checkTerrain(terrain));
    }

    public boolean existsMultipleHexesOfTerrainTypeThatAreAdjacent(int coordinateX, int coordinateY) {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        return true;
//                map.checkExpandSettlementHexes(coordinate);
    }
}
