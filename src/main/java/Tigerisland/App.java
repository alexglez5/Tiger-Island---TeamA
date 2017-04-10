package Tigerisland;

import Tigerisland.AIHelpers.Choice;

public class App {

    Boolean isBoardEmpty = true;
    Tile currentTile;
    String currentBuildOption;
    Game map = new Game();
    Player player1 = new Player();
    Player player2 = new Player();

    public App() {
    }

    public static void main(String[] args) {
        System.out.println("random change");
    }

    public void placeTile(String orientation, int xcoordinate, int ycoordinate) {
        isBoardEmpty = false;
        map.placeTile(
                currentTile,
                new Coordinate(xcoordinate, ycoordinate),
                checkOrientation(orientation)
        );
    }

    public void doBuildOption(String playerID) {
        if (currentBuildOption.equals("Found Settlement")) {
        }
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
        return map.getBoard().containsKey(cord);
    }

    public Choice enumPlayerChoice(String choice) {
        if (choice.equals("Found Settlement")) {
            return Choice.FOUNDED;
        } else if (choice.equals("Expand Settlement")) {
            return Choice.EXPANDED;
        } else if (choice.equals("Place Totoro")) {
            return Choice.TOTORO;
        } else if (choice.equals("Place Tiger")) {
            return Choice.TIGER;
        } else {
            System.out.println("Invalid Player Choice");
            return null;
        }

    }

    public TerrainType checkTerrain(String terrain) {

        if (terrain.equals("JUNGLE") || terrain.equals("jungle")) {
            return TerrainType.JUNGLE;
        } else if (terrain.equals("ROCK") || terrain.equals("rocky")) {
            return TerrainType.ROCK;
        } else if (terrain.equals("LAKE") || terrain.equals("lake")
                || terrain.equals("Lakes") || terrain.equals("lakes")) {
            return TerrainType.LAKE;
        } else if (terrain.equals("Grassland") || terrain.equals("grassland")
                || terrain.equals("GRASS") || terrain.equals("grasslands")) {
            return TerrainType.GRASS;
        } else if (terrain.equals("VOLCANO") || terrain.equals("volcano")) {
            return TerrainType.VOLCANO;
        } else {
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
        Coordinate toCheck = new Coordinate(x, y);
        Hex hexToCheck = map.getBoard().get(toCheck);
        return correctTerrainType.equals(hexToCheck.getTerrainType());

    }

    public int returnLevel(int coordinatex, int coordinatey) {
        Coordinate coordinate = new Coordinate(coordinatex, coordinatey);
        return map.getBoard().get(coordinate).getLevel();
    }

    public boolean checkVillagers(int coordinateX, int coordinateY) {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        return map.getBoard().get(coordinate).hasVillager();
    }

    public void expandSettlementAt(int coordinateX, int coordinateY, String terrain) {
        Coordinate coordinate = new Coordinate(coordinateX, coordinateY);
        map.expandSettlement(coordinate, checkTerrain(terrain));
    }

    public void updateCurrentTile(String terrain1, String terrain2) {
        currentTile = new Tile(checkTerrain(terrain1), checkTerrain(terrain2));
    }

    public void createPlayer1(int playerID) {
//        player1.setPlayerID(playerID);
//    }

//    public void createPlayer2(int playerID){
//        player2.setPlayerID(playerID);
// }

//    public void chooseBuildOption(String choice, String playerID) {
//
//    }
    }
}
